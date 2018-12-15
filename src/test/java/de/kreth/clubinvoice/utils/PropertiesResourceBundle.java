package de.kreth.clubinvoice.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PropertiesResourceBundle extends ResourceBundle {

	public static void install() {
		ResourceBundleProvider.INSTANCE.implementation = new PropertiesResourceBundle();
	}

	final Properties prop = new Properties();
	private final List<String> collect;

	PropertiesResourceBundle() {
		try {
			prop.load(getClass().getResourceAsStream("/application_de_DE.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		collect = prop.keySet().stream().map(o -> o.toString()).collect(Collectors.toList());
	}

	@Override
	protected Object handleGetObject(String key) {
		return prop.getProperty(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(collect);
	}

}
