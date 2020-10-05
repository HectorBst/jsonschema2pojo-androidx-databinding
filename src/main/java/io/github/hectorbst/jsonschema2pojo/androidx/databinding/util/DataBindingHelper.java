package io.github.hectorbst.jsonschema2pojo.androidx.databinding.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsonschema2pojo.Schema;

import java.util.Optional;

/**
 * @author Hector Basset
 */
public class DataBindingHelper {

	protected static final String JSON_KEY_OBSERVABLE = "x-adb-observable";

	public void propagateObservable(Schema schema) {
		if (schema.getContent().get(JSON_KEY_OBSERVABLE) == null) {
			Optional.ofNullable(schema.getParent())
					.map(Schema::getContent)
					.map(n -> n.get(JSON_KEY_OBSERVABLE))
					.map(n -> n.asBoolean(true))
					.filter(observable -> !observable)
					.ifPresent(observable -> ((ObjectNode) schema.getContent()).put(JSON_KEY_OBSERVABLE, observable));
		}
	}

	public boolean mustHandleDataBinding(JsonNode node) {
		return Optional.ofNullable(node.get(JSON_KEY_OBSERVABLE))
				.map(n -> n.asBoolean(true))
				.orElse(Boolean.TRUE);
	}
}
