package de.kreth.clubinvoice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateAttributeConverterTest {

	private LocalDateAttributeConverter converter;

	@BeforeEach
	void initConverter() {
		converter = new LocalDateAttributeConverter();
	}
	
	@Test
	void testNullArguments() {
		assertNull(converter.convertToDatabaseColumn(null));
		assertNull(converter.convertToEntityAttribute(null));
	}
	
	@Test
	void testConvertToDatabaseColumn() {
		Date actual = converter.convertToDatabaseColumn(LocalDate.of(2018, 8, 21));
		assertNotNull(actual);
		Calendar cal = Calendar.getInstance();
		cal.setTime(actual);
		assertEquals(2018, cal.get(Calendar.YEAR));
		assertEquals(Calendar.AUGUST, cal.get(Calendar.MONTH));
		assertEquals(21, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(0, cal.get(Calendar.HOUR));
		assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, cal.get(Calendar.MINUTE));
		assertEquals(0, cal.get(Calendar.SECOND));
		assertEquals(0, cal.get(Calendar.MILLISECOND));
		
	}

	@Test
	void testConvertToEntityAttribute() {
		LocalDate actual = converter.convertToEntityAttribute(new GregorianCalendar(2018, Calendar.AUGUST, 21).getTime());
		assertNotNull(actual);
		assertEquals(2018, actual.getYear());
		assertEquals(8, actual.getMonthValue());
		assertEquals(21, actual.getDayOfMonth());
	}

}
