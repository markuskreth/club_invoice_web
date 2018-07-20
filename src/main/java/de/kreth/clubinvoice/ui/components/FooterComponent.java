package de.kreth.clubinvoice.ui.components;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class FooterComponent extends HorizontalLayout {

	private static final long serialVersionUID = 4845822203421115202L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FooterComponent.class);

	private final static Properties version = new Properties();
	static {
		try {
			version.load(FooterComponent.class
					.getResourceAsStream("/version.properties"));
		} catch (IOException | NullPointerException e) {
			LOGGER.error(
					"Error loading version properties file: " + e.getMessage());
		}
	}

	public FooterComponent() {

		Label copyright = new Label("&copy; Markus Kreth", ContentMode.HTML);
		addComponent(copyright);

		if (propertiesLoaded()) {

			Label vers = new Label(
					"Version: " + version.getProperty("project.version"));
			Label buildTime = new Label(
					"Build: " + version.getProperty("build.dateTime"));
			addComponents(vers, buildTime);
		}
	}

	private boolean propertiesLoaded() {
		return version.getProperty("build.dateTime") != null && version
				.getProperty("build.dateTime").trim().isEmpty() == false;
	}
}
