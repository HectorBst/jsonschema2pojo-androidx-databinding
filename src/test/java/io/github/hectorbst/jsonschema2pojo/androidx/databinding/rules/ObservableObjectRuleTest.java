package io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.ObservableObjectRule.BASE_OBSERVABLE_CLASS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Hector Basset
 */
@RunWith(JUnitPlatform.class)
class ObservableObjectRuleTest {

	final JCodeModel owner = new JCodeModel();
	final JDefinedClass clazz = owner._class("test.Test");
	final ObservableObjectRule observableObjectRule = new ObservableObjectRule(new AndroidDataBindingRuleFactory());

	public ObservableObjectRuleTest() throws JClassAlreadyExistsException {
	}

	@Test
	void when_object_must_extends_base_observable() {

		// Given

		// When
		observableObjectRule.handleDataBinding(clazz);

		// Then
		assertThat(clazz._extends().fullName()).isEqualTo(BASE_OBSERVABLE_CLASS);
	}
}
