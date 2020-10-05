package io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.AndroidDataBindingRuleFactory;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.Constants.BINDABLE_CLASS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Hector Basset
 */
@RunWith(JUnitPlatform.class)
class ObservablePropertyRuleTest {

	final JCodeModel owner = new JCodeModel();
	final JDefinedClass clazz = owner._class("test.Test");
	final JFieldVar field = clazz.field(JMod.PRIVATE, owner.ref(String.class), "test");
	final JMethod getter = clazz.method(JMod.PUBLIC, field.type(), "getTest");
	final JMethod setter = clazz.method(JMod.PUBLIC, owner.VOID, "setTest");

	{
		setter.param(field.type(), field.name());
	}

	final ObservablePropertyRule observablePropertyRule = new ObservablePropertyRule(new AndroidDataBindingRuleFactory());

	public ObservablePropertyRuleTest() throws JClassAlreadyExistsException {
	}

	@Test
	void when_property_must_annotate_getter() {

		// Given

		// When
		observablePropertyRule.handleDataBinding("test", null, clazz);

		// Then
		assertThat(getter.annotations()).anyMatch(ann -> ann.getAnnotationClass().fullName().equals(BINDABLE_CLASS));
	}

	@Test
	void when_property_must_amend_setter() {

		// Given

		// When
		observablePropertyRule.handleDataBinding("test", null, clazz);

		// Then
		assertThat(setter.body().getContents()).last()
				.isInstanceOfSatisfying(JInvocation.class, invocation -> {
					Field nameField;
					try {
						//Dirty hack to check what is invoked
						nameField = JInvocation.class.getDeclaredField("name");
						nameField.setAccessible(true);
						assertThat(nameField.get(invocation)).isEqualTo("notifyPropertyChanged");
					} catch (NoSuchFieldException | IllegalAccessException e) {
						throw new RuntimeException(e.getMessage(), e);
					}
				});
	}
}
