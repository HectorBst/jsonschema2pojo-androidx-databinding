package org.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.Rule;

/**
 * @author Hector Basset
 */
public class ObservablePropertyRule implements Rule<JDefinedClass, JDefinedClass> {

	protected static final String BINDABLE_CLASS = "androidx.databinding.Bindable";

	private final AndroidDataBindingRuleFactory ruleFactory;

	public ObservablePropertyRule(AndroidDataBindingRuleFactory ruleFactory) {
		this.ruleFactory = ruleFactory;
	}

	@Override
	public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass clazz, Schema currentSchema) {
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

		return clazz;
	}
}
