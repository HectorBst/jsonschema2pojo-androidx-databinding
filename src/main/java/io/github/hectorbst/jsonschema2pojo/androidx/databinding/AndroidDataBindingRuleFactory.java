package io.github.hectorbst.jsonschema2pojo.androidx.databinding;

import com.sun.codemodel.JClassContainer;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.AndroidDataBindingObjectRule;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.AndroidDataBindingPropertyRule;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.AndroidDataBindingTypeRule;
import org.jsonschema2pojo.rules.Rule;
import org.jsonschema2pojo.rules.RuleFactory;
import org.jsonschema2pojo.util.ParcelableHelper;

/**
 * The entry point of the Android Data Binding jsonschema2pojo extension.
 *
 * Allows the use of additional {@link Rule}s applied to handle Data Binding related elements.
 *
 * @author Hector Basset
 */
public class AndroidDataBindingRuleFactory extends RuleFactory {

	private final ParcelableHelper parcelableHelper = new ParcelableHelper();

	public ParcelableHelper getParcelableHelper() {
		return parcelableHelper;
	}

	@Override
	public Rule<JPackage, JType> getObjectRule() {
		return new AndroidDataBindingObjectRule(this);
	}

	@Override
	public Rule<JDefinedClass, JDefinedClass> getPropertyRule() {
		return new AndroidDataBindingPropertyRule(this);
	}

	@Override
	public Rule<JClassContainer, JType> getTypeRule() {
		return new AndroidDataBindingTypeRule(this);
	}
}
