package de.kreth.clubinvoice;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.annotation.processing.Generated;

/**
 * Property keys from version.properties
 */
@Generated(date = "26.05.2019, 10:23:56", value = "de.kreth.property2java.Generator")
public enum Version_Properties {

	/**
	 * project.version = "${project.version}"
	 */
	PROJECT_VERSION ("project.version"),
	/**
	 * package_name = "${project.artifactId}-${project.version}.${project.packaging}"
	 */
	PACKAGE_NAME ("package_name"),
	/**
	 * build.dateTime = "${timestamp}"
	 */
	BUILD_DATETIME ("build.dateTime"),
	/**
	 * project.artifactId = "${project.artifactId}"
	 */
	PROJECT_ARTIFACTID ("project.artifactId");

	private final String value;

	private Version_Properties (String value) {
		this.value = value;
	}

	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Resolves the value for this key from the parameter function.
	 * <p>
	 * e.g. <code>Version_Properties.getString(resBundle::getString)</code>
	 * @param resourceFunction {@link Properties#getProperty(String)} or {@link ResourceBundle#getString(String)}
	 * @return
	 */
	public String getString(UnaryOperator<String> resourceFunction) {
		return resourceFunction.apply(value);
	}
}
