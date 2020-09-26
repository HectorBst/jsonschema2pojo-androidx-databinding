package org.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.Rule;

/**
 * @author Hector Basset
 */
public class ObservableObjectRule implements Rule<JDefinedClass, JDefinedClass> {

	protected final String BASE_OBSERVABLE = "androidx.databinding.BaseObservable";

	@Override
	public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass clazz, Schema schema) {
		clazz._extends(clazz.owner().directClass(BASE_OBSERVABLE));

		return clazz;
	}
}
