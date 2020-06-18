package de.kreth.clubinvoice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathConverterTest {

	private PathConverter converter;

	@BeforeEach
	void initConverter() {
		converter = new PathConverter();
	}

	@Test
	public void testConvewrtToDBLinux() {
		String dbValue = new File("/etc/init.d/.depend.start").getAbsolutePath();
		Path attribute = Paths.get(dbValue);
		assertEquals(dbValue, converter.convertToDatabaseColumn(attribute));
	}

	@Test
	public void testConvewrtFromDBLinux() {
		String dbValue = new File("/etc/init.d/.depend.start").getAbsolutePath();
		Path attribute = Paths.get(dbValue);
		assertEquals(attribute, converter.convertToEntityAttribute(dbValue));
	}

}
