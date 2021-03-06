package dev.hctbst.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import dev.hctbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.ObjectRule;

/**
 * Override of the default {@link ObjectRule} to apply data binding related elements.
 *
 * @author Hector Basset
 */
public class ObservableObjectRule extends ObjectRule {

	protected static final String BASE_OBSERVABLE_CLASS = "androidx.databinding.BaseObservable";

	private final AndroidDataBindingRuleFactory ruleFactory;

	public ObservableObjectRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory, ruleFactory.getParcelableHelper(), ruleFactory.getReflectionHelper());
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JType apply(String nodeName, JsonNode node, JsonNode parent, JPackage jPackage, Schema schema) {
		ruleFactory.getDataBindingHelper().propagateObservable(schema);

		JType type = super.apply(nodeName, node, parent, jPackage, schema);

		if (ruleFactory.getDataBindingHelper().mustHandleDataBinding(node)) {
			handleDataBinding(type);
		}

		return type;
	}

	protected void handleDataBinding(JType type) {
		if (type instanceof JDefinedClass) {
			JDefinedClass clazz = (JDefinedClass) type;

			clazz._extends(clazz.owner().directClass(BASE_OBSERVABLE_CLASS));
		}
	}
}
