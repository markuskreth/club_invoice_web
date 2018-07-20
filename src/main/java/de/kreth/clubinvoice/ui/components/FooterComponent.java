package de.kreth.clubinvoice.ui.components;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

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
		String path = "/../version.properties";
		try {
			recursivelyLoadPropFromPath(FooterComponent.class, path, 0);
		} catch (Exception e) {
			LOGGER.error("Error loading version properties file = " + path
					+ ", cause: " + e.getMessage());
		}
	}

	private static void recursivelyLoadPropFromPath(
			Class<FooterComponent> thisClass, String path, int level)
			throws IOException {

		URL resource = thisClass.getResource(path);
		if (resource != null) {
			version.load(resource.openStream());
			LOGGER.info("Successfully loaded version info from " + resource);
		} else if (level < 4) {
			recursivelyLoadPropFromPath(thisClass, "/.." + path, level + 1);
		} else {
			throw new IOException("File not Found in any subdir of " + path);
		}

	}
	public FooterComponent() {

		Label copyright = new Label("&copy; Markus Kreth", ContentMode.HTML);
		addComponent(copyright);

		if (propertiesLoaded()) {

			String dateTimeProperty = version.getProperty("build.dateTime");
			SimpleDateFormat sourceFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				Date date = sourceFormat.parse(dateTimeProperty);
				dateTimeProperty = DateFormat.getDateTimeInstance(
						DateFormat.MEDIUM, DateFormat.SHORT).format(date);
			} catch (ParseException e) {
				LOGGER.warn(
						"Unable to parse dateTimeProperty=" + dateTimeProperty,
						e);
			}
			Label vers = new Label(
					"Version: " + version.getProperty("project.version"));
			Label buildTime = new Label("Build: " + dateTimeProperty);
			addComponents(vers, buildTime);
		}
	}

	private boolean propertiesLoaded() {
		return version.getProperty("build.dateTime") != null && version
				.getProperty("build.dateTime").trim().isEmpty() == false;
	}
}
