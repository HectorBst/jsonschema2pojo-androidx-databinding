package dev.hctbst.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassContainer;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import dev.hctbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.TypeRule;

import java.util.Collection;
import java.util.Map;

/**
 * @author Hector Basset
 */
public class ObservableTypeRule extends TypeRule {

	protected static final String OBSERVABLE_LIST_CLASS = "androidx.databinding.ObservableList";
	protected static final String OBSERVABLE_MAP_CLASS = "androidx.databinding.ObservableMap";

	private final AndroidDataBindingRuleFactory ruleFactory;

	public ObservableTypeRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory);
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JType apply(String nodeName, JsonNode node, JsonNode parent, JClassContainer classContainer, Schema schema) {
		ruleFactory.getDataBindingHelper().propagateObservable(schema);

		JType type = super.apply(nodeName, node, parent, classContainer, schema);

		if (ruleFactory.getDataBindingHelper().mustHandleDataBinding(node)) {
			type = handleDataBinding(type);
		}

		return type;
	}

	protected JType handleDataBinding(JType type) {
		if (type instanceof JClass) {
			JClass clazz = (JClass) type;
			JCodeModel owner = clazz.owner();

			if (owner.ref(Collection.class).isAssignableFrom(clazz.erasure())) {
				return owner.directClass(OBSERVABLE_LIST_CLASS).narrow(handleDataBinding(clazz.getTypeParameters().get(0)));
			} else if (owner.ref(Map.class).isAssignableFrom(clazz.erasure())) {
				return owner.directClass(OBSERVABLE_MAP_CLASS).narrow(
						(JClass) handleDataBinding(clazz.getTypeParameters().get(0)),
						(JClass) handleDataBinding(clazz.getTypeParameters().get(1))
				);
			}
		}

		return type;
	}
}
