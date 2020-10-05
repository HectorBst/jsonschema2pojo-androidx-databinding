package io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsonschema2pojo.Schema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.Constants.JSON_KEY_OBSERVABLE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Hector Basset
 */
@RunWith(JUnitPlatform.class)
public class DataBindingRuleTest {

	final DataBindingRule dataBindingRule = new DataBindingRule() {
	};
	final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void when_propagate_with_no_parent_must_do_nothing() {

		// Given
		Schema schema = new Schema(null, objectMapper.createObjectNode(), null);

		// When
		dataBindingRule.propagateObservable(schema);

		// Then
		assertThat(schema.getContent().path(JSON_KEY_OBSERVABLE)).isEqualTo(MissingNode.getInstance());
	}

	static private Stream<JsonNode> trueObservableValues() {
		return Stream.of(
				MissingNode.getInstance(),
				NullNode.getInstance(),
				BooleanNode.TRUE
		);
	}

	@ParameterizedTest
	@MethodSource("trueObservableValues")
	void when_propagate_true_observable_property_must_do_nothing(JsonNode observable) {

		// Given
		ObjectNode parentContent = objectMapper.createObjectNode();
		parentContent.set(JSON_KEY_OBSERVABLE, observable);
		Schema parent = new Schema(null, parentContent, null);
		Schema schema = new Schema(null, objectMapper.createObjectNode(), parent);

		// When
		dataBindingRule.propagateObservable(schema);

		// Then
		assertThat(schema.getContent().path(JSON_KEY_OBSERVABLE)).isEqualTo(MissingNode.getInstance());
	}

	@Test
	void when_propagate_false_observable_value_must_do_it() {

		// Given
		ObjectNode parentContent = objectMapper.createObjectNode();
		parentContent.put(JSON_KEY_OBSERVABLE, false);
		Schema parent = new Schema(null, parentContent, null);
		Schema schema = new Schema(null, objectMapper.createObjectNode(), parent);

		// When
		dataBindingRule.propagateObservable(schema);

		// Then
		assertThat(schema.getContent().path(JSON_KEY_OBSERVABLE)).isEqualTo(BooleanNode.FALSE);
	}

	static private Stream<Arguments> whenPropagateAnyObservableValueAlreadyDefinedMustDoNothingArguments() {
		return Stream.of(
				MissingNode.getInstance(),
				NullNode.getInstance(),
				BooleanNode.FALSE,
				BooleanNode.TRUE
		).flatMap(parentValue -> Stream.of(
				Arguments.arguments(parentValue, BooleanNode.FALSE),
				Arguments.arguments(parentValue, BooleanNode.TRUE)
		));
	}

	@ParameterizedTest
	@MethodSource("whenPropagateAnyObservableValueAlreadyDefinedMustDoNothingArguments")
	void when_propagate_any_observable_value_already_defined_must_do_nothing(JsonNode parentValue, JsonNode currentValue) {

		// Given
		ObjectNode parentContent = objectMapper.createObjectNode();
		parentContent.set(JSON_KEY_OBSERVABLE, parentValue);
		Schema parent = new Schema(null, parentContent, null);
		ObjectNode content = objectMapper.createObjectNode();
		content.set(JSON_KEY_OBSERVABLE, currentValue);
		Schema schema = new Schema(null, content, parent);

		// When
		dataBindingRule.propagateObservable(schema);

		// Then
		assertThat(schema.getContent().path(JSON_KEY_OBSERVABLE)).isEqualTo(currentValue);
	}

	@ParameterizedTest
	@MethodSource("trueObservableValues")
	void when_test_data_binding_handling_must_return_true_if_not_false(JsonNode observable) {

		// Given
		ObjectNode node = objectMapper.createObjectNode();
		node.set(JSON_KEY_OBSERVABLE, observable);

		// When
		boolean handle = dataBindingRule.mustHandleDataBinding(node);

		// Then
		assertThat(handle).isTrue();
	}

	@Test
	void when_test_data_binding_handling_must_return_false_if_false() {

		// Given
		ObjectNode node = objectMapper.createObjectNode();
		node.put(JSON_KEY_OBSERVABLE, false);

		// When
		boolean handle = dataBindingRule.mustHandleDataBinding(node);

		// Then
		assertThat(handle).isFalse();
	}
}
