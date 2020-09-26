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

	private final AndroidDataBindingRuleFactory ruleFactory;

	protected AndroidDataBindingObjectRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory, ruleFactory.getParcelableHelper(), ruleFactory.getReflectionHelper());
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JType apply(String nodeName, JsonNode node, JsonNode parent, JPackage _package, Schema schema) {
		JType type = super.apply(nodeName, node, parent, _package, schema);

		if (type instanceof JDefinedClass) {
			JDefinedClass clazz = (JDefinedClass) type;

			ruleFactory.getObservableObjectRule().apply(nodeName, node, parent, clazz, schema);
		}

		return type;
	}
}
