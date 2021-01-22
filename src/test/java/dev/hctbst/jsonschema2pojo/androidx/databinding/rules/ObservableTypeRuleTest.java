package dev.hctbst.jsonschema2pojo.androidx.databinding.rules;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import dev.hctbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static dev.hctbst.jsonschema2pojo.androidx.databinding.rules.ObservableTypeRule.OBSERVABLE_LIST_CLASS;
import static dev.hctbst.jsonschema2pojo.androidx.databinding.rules.ObservableTypeRule.OBSERVABLE_MAP_CLASS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Hector Basset
 */
@RunWith(JUnitPlatform.class)
class ObservableTypeRuleTest {

	final JCodeModel owner = new JCodeModel();
	final ObservableTypeRule observableTypeRule = new ObservableTypeRule(new AndroidDataBindingRuleFactory());

	@Test
	void when_primitive_must_not_convert() {

		// Given
		JType type = owner.BOOLEAN;

		// When
		JType result = observableTypeRule.handleDataBinding(type);

		// Then
		assertThat(result).isEqualTo(type);
	}

	@Test
	void when_existing_class_must_not_convert() {

		// Given
		JType type = owner.ref(String.class);

		// When
		JType result = observableTypeRule.handleDataBinding(type);

		// Then
		assertThat(result).isEqualTo(type);
	}

	@Test
	void when_generated_class_must_not_convert() throws JClassAlreadyExistsException {

		// Given
		JType type = owner._class("test.Test");

		// When
		JType result = observableTypeRule.handleDataBinding(type);

		// Then
		assertThat(result).isEqualTo(type);
	}

	static private Stream<Class<?>> whenCollectionMustConvertArguments() {
		return Stream.of(
				List.class,
				ArrayList.class,
				Set.class,
				HashSet.class
		);
	}

	@ParameterizedTest
	@MethodSource("whenCollectionMustConvertArguments")
	void when_collection_must_convert(Class<?> clazz) {

		// Given
		JType type = owner.ref(clazz).narrow(owner.ref(String.class));

		// When
		JType result = observableTypeRule.handleDataBinding(type);

		// Then
		assertThat(result).isInstanceOfSatisfying(JClass.class, c -> {
			assertThat(c.erasure().fullName()).isEqualTo(OBSERVABLE_LIST_CLASS);
			assertThat(c.getTypeParameters().get(0)).isEqualTo(owner.ref(String.class));
		});
	}

	static private Stream<Class<?>> whenMapMustConvertArguments() {
		return Stream.of(
				Map.class,
				HashMap.class
		);
	}

	@ParameterizedTest
	@MethodSource("whenMapMustConvertArguments")
	void when_map_must_convert(Class<?> clazz) {

		// Given
		JType type = owner.ref(clazz).narrow(owner.ref(String.class), owner.ref(Double.class));

		// When
		JType result = observableTypeRule.handleDataBinding(type);

		// Then
		assertThat(result).isInstanceOfSatisfying(JClass.class, c -> {
			assertThat(c.erasure().fullName()).isEqualTo(OBSERVABLE_MAP_CLASS);
			assertThat(c.getTypeParameters().get(0)).isEqualTo(owner.ref(String.class));
			assertThat(c.getTypeParameters().get(1)).isEqualTo(owner.ref(Double.class));
		});
	}

	@Test
	void when_nested_list_must_convert() {

		// Given
		JType type = owner.ref(List.class).narrow(owner.ref(List.class).narrow(owner.ref(String.class)));

		// When
		JType result = observableTypeRule.handleDataBinding(type);

		// Then
		assertThat(result).isInstanceOfSatisfying(JClass.class, c -> {
			assertThat(c.erasure().fullName()).isEqualTo(OBSERVABLE_LIST_CLASS);
			assertThat(c.getTypeParameters().get(0).erasure().fullName()).isEqualTo(OBSERVABLE_LIST_CLASS);
			assertThat(c.getTypeParameters().get(0).getTypeParameters().get(0)).isEqualTo(owner.ref(String.class));
		});
	}
}
