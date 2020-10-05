package io.github.hectorbst.jsonschema2pojo.androidx.databinding;

import com.sun.codemodel.JClassContainer;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.ObservableObjectRule;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.ObservablePropertyRule;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.rules.ObservableTypeRule;
import io.github.hectorbst.jsonschema2pojo.androidx.databinding.util.DataBindingHelper;
import org.jsonschema2pojo.rules.Rule;
import org.jsonschema2pojo.rules.RuleFactory;
import org.jsonschema2pojo.util.ParcelableHelper;

/**
 * The entry point of the Android Data Binding jsonschema2pojo extension.
 * <p>
 * Allows the use of additional {@link Rule}s applied to handle Data Binding related elements.
 *
 * @author Hector Basset
 */
public class AndroidDataBindingRuleFactory extends RuleFactory {

	private final ParcelableHelper parcelableHelper = new ParcelableHelper();
	private final DataBindingHelper dataBindingHelper = new DataBindingHelper();

	public ParcelableHelper getParcelableHelper() {
		return parcelableHelper;
	}

	public DataBindingHelper getDataBindingHelper() {
		return dataBindingHelper;
	}

	@Override
	public Rule<JPackage, JType> getObjectRule() {
		return new ObservableObjectRule(this);
	}

	@Override
	public Rule<JDefinedClass, JDefinedClass> getPropertyRule() {
		return new ObservablePropertyRule(this);
	}

	@Override
	public Rule<JClassContainer, JType> getTypeRule() {
		return new ObservableTypeRule(this);
	}
}
