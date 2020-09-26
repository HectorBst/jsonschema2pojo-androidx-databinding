package org.jsonschema2pojo.androidx.databinding.rules;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import org.jsonschema2pojo.rules.Rule;
import org.jsonschema2pojo.rules.RuleFactory;
import org.jsonschema2pojo.util.ParcelableHelper;

/**
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

	public Rule<JDefinedClass, JDefinedClass> getObservableObjectRule() {
		return new ObservableObjectRule();
	}

	public Rule<JDefinedClass, JDefinedClass> getObservablePropertyRule() {
		return new ObservablePropertyRule(this);
	}
}
