package io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.PropertyRule;

/**
 * Override of the default {@link PropertyRule} to apply data binding related elements.
 *
 * @author Hector Basset
 */
public class AndroidDataBindingPropertyRule extends PropertyRule {

	protected static final String BINDABLE_CLASS = "androidx.databinding.Bindable";

	private final AndroidDataBindingRuleFactory ruleFactory;

	public AndroidDataBindingPropertyRule(AndroidDataBindingRuleFactory ruleFactory) {
		super(ruleFactory);
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass clazz, Schema schema) {
		clazz = super.apply(nodeName, node, parent, clazz, schema);

		handleDataBinding(nodeName, node, clazz);

		return clazz;
	}

	protected void handleDataBinding(String nodeName, JsonNode node, JDefinedClass clazz) {
		String propertyName = ruleFactory.getNameHelper().getPropertyName(nodeName, node);
		JFieldVar field = clazz.fields().get(propertyName);

		if (field != null) {

			String getterName = ruleFactory.getNameHelper().getGetterName(nodeName, field.type(), node);
			JMethod getter = clazz.getMethod(getterName, new JType[0]);

			if (getter != null) {
				getter.annotate(clazz.owner().directClass(BINDABLE_CLASS));
			}

			String setterName = ruleFactory.getNameHelper().getSetterName(nodeName, node);
			JMethod setter = clazz.getMethod(setterName, new JType[]{field.type()});

			if (setter != null) {
				setter.body().invoke("notifyPropertyChanged").arg(clazz.owner().directClass("BR").staticRef(field.name()));
			}
		}
	}
}
