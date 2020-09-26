package org.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.PropertyRule;

/**
 * Override of the default {@link PropertyRule} to apply data binding related elements.
 *
 * @author Hector Basset
 */
public class AndroidDataBindingPropertyRule extends PropertyRule {

	private final AndroidDataBindingRuleFactory ruleFactory;

	public AndroidDataBindingPropertyRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory);
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass clazz, Schema schema) {
		clazz = super.apply(nodeName, node, parent, clazz, schema);

		ruleFactory.getObservablePropertyRule().apply(nodeName, node, parent, clazz, schema);

		return clazz;
	}
}
