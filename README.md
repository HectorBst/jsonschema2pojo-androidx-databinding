[![Build](https://img.shields.io/github/workflow/status/hectorbst/jsonschema2pojo-androidx-databinding/Build?label=Build)](https://github.com/HectorBst/jsonschema2pojo-androidx-databinding/actions?query=workflow%3ABuild)
[![Coverage](https://img.shields.io/sonar/coverage/HectorBst_jsonschema2pojo-androidx-databinding?server=https%3A%2F%2Fsonarcloud.io&label=Coverage)](https://sonarcloud.io/dashboard?id=HectorBst_jsonschema2pojo-androidx-databinding)
[![Violations](https://img.shields.io/sonar/violations/HectorBst_jsonschema2pojo-androidx-databinding?server=https%3A%2F%2Fsonarcloud.io&label=Violations)](https://sonarcloud.io/dashboard?id=HectorBst_jsonschema2pojo-androidx-databinding)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.hectorbst/jsonschema2pojo-androidx-databinding?label=Maven%20Central)](https://search.maven.org/artifact/io.github.hectorbst/jsonschema2pojo-androidx-databinding)
[![License](https://img.shields.io/github/license/hectorbst/jsonschema2pojo-androidx-databinding?label=License)](LICENSE)

# jsonschema2pojo-androidx-databinding

This project is a [*jsonschema2pojo*](https://github.com/joelittlejohn/jsonschema2pojo) extension dedicated to
[observable](https://developer.android.com/topic/libraries/data-binding/observability) entities generation for
[*Android Data Binding*](https://developer.android.com/topic/libraries/data-binding).

## Features

This extension generate observable data objects as explained [here](https://developer.android.com/topic/libraries/data-binding/observability#observable_objects).

E.g., this schema:
```json
{
	"title": "Sample entity",
	"type": "object",
	"properties": {
		"field": {
			"title": "A field",
			"type": "string"
		}
	}
}
```
Will produce:
```java
public class Entity extends BaseObservable {

	private String field;

	@Bindable
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
		notifyPropertyChanged(BR.field);
	}
}
```

`Collection` type fields become [`ObservableList`](https://developer.android.com/reference/android/databinding/ObservableList)
and `Map` type fields become [`ObservableMap`](https://developer.android.com/reference/android/databinding/ObservableMap).

E.g., this schema:
```json
{
	"title": "Sample entity",
	"type": "object",
	"properties": {
		"field": {
			"title": "A list",
			"type": "array",
			"items": {
				"type": "string"
			}
		}
	}
}
```
Will produce:
```java
public class Entity extends BaseObservable {

	private ObservableList<String> field;

	@Bindable
	public ObservableList<String> getField() {
		return field;
	}

	public void setField(ObservableList<String> field) {
		this.field = field;
		notifyPropertyChanged(BR.field);
	}
}
```

## Gradle configuration

Here is an example of how the extension can be added to the jsonschema2pojo Gradle plugin in a Android project.

```groovy
...

buildscript {
	...
	dependencies {
		...
		//jsonschema2pojo dependency
		classpath "org.jsonschema2pojo:jsonschema2pojo-gradle-plugin:${jsonschema2pojoVersion}"
		//Extension dependency
		classpath "io.github.hectorbst:jsonschema2pojo-androidx-databinding:${jsonschema2pojoDataBindingVersion}"
	}
}

apply plugin: 'com.android.library'
apply plugin: 'jsonschema2pojo'

android {
	...

	buildFeatures {
		//Enable data binding
		dataBinding = true
	}
}

...

jsonSchema2Pojo {
	...
	//Extension RuleFactory
	customRuleFactory = 'org.jsonschema2pojo.androidx.databinding.rules.AndroidDataBindingRuleFactory'
}
```

A more complete example is available [here](example).

## License

This project is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
