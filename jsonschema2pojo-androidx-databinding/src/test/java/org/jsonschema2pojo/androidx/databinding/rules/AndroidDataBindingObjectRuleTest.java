package org.jsonschema2pojo.androidx.databinding.rules;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Hector Basset
 */
@RunWith(JUnitPlatform.class)
public class AndroidDataBindingObjectRuleTest {

	final JCodeModel owner = new JCodeModel();
	final JDefinedClass clazz = owner._class("test.Test");
	final AndroidDataBindingObjectRule androidDataBindingObjectRule = new AndroidDataBindingObjectRule(new AndroidDataBindingRuleFactory());

	public AndroidDataBindingObjectRuleTest() throws JClassAlreadyExistsException {
	}

	@Test
	void when_object_must_extends_base_observable() {

		// Given

		// When
		androidDataBindingObjectRule.handleDataBinding(clazz);

		// Then
		assertThat(clazz._extends().fullName()).isEqualTo(AndroidDataBindingObjectRule.BASE_OBSERVABLE_CLASS);
	}
}
