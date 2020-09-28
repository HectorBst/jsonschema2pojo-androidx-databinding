package org.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.ObjectRule;

/**
 * Override of the default {@link ObjectRule} to apply data binding related elements.
 *
 * @author Hector Basset
 */
public class AndroidDataBindingObjectRule extends ObjectRule {

	protected static final String BASE_OBSERVABLE_CLASS = "androidx.databinding.BaseObservable";

	private final AndroidDataBindingRuleFactory ruleFactory;

	protected AndroidDataBindingObjectRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory, ruleFactory.getParcelableHelper(), ruleFactory.getReflectionHelper());
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JType apply(String nodeName, JsonNode node, JsonNode parent, JPackage _package, Schema schema) {
		JType type = super.apply(nodeName, node, parent, _package, schema);

		handleDataBinding(type);

		return type;
	}

	protected void handleDataBinding(JType type) {

		if (type instanceof JDefinedClass) {
			JDefinedClass clazz = (JDefinedClass) type;

			clazz._extends(clazz.owner().directClass(BASE_OBSERVABLE_CLASS));
		}
	}
}
