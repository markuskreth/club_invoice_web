package de.kreth.clubinvoice.utils;

import java.util.ResourceBundle;

public enum ResourceBundleProvider {

	INSTANCE;
	ResourceBundle implementation;

	public static ResourceBundle getBundle() {
		if (INSTANCE.implementation == null) {
			INSTANCE.implementation = ResourceBundle.getBundle("application");
		}
		return INSTANCE.implementation;
	}
}
