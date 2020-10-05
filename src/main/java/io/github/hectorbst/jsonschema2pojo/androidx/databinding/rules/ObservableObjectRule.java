package io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.ObjectRule;

import static io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.Constants.BASE_OBSERVABLE_CLASS;

/**
 * Override of the default {@link ObjectRule} to apply data binding related elements.
 *
 * @author Hector Basset
 */
public class ObservableObjectRule extends ObjectRule implements DataBindingRule {

	public ObservableObjectRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory, ruleFactory.getParcelableHelper(), ruleFactory.getReflectionHelper());
	}

	@Override
	public JType apply(String nodeName, JsonNode node, JsonNode parent, JPackage jPackage, Schema schema) {
		propagateObservable(schema);

		JType type = super.apply(nodeName, node, parent, jPackage, schema);

		if (mustHandleDataBinding(node)) {
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
