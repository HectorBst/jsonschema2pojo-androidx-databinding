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
public class ObservableObjectRuleTest {

	final JCodeModel owner = new JCodeModel();
	final JDefinedClass clazz = owner._class("test.Test");
	final ObservableObjectRule observableObjectRule = new ObservableObjectRule();

	public ObservableObjectRuleTest() throws JClassAlreadyExistsException {
	}

	@Test
	void when_object_must_extends_base_observable() {

		// Given

		// When
		observableObjectRule.apply("test", null, null, clazz, null);

		// Then
		assertThat(clazz._extends().fullName()).isEqualTo(ObservableObjectRule.BASE_OBSERVABLE_CLASS);
	}
}
