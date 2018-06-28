package de.kreth.clubinvoice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateTimeAttributeConverterTest {

	private LocalDateTimeAttributeConverter converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new LocalDateTimeAttributeConverter();
	}

	@Test
	void testConvertToDatabaseColumn() {
		LocalDateTime locDateTime = LocalDateTime.of(2017, Month.JUNE, 27, 21,
				20, 30);
		Calendar expected = new GregorianCalendar(2017, Calendar.JUNE, 27, 21,
				20, 30);
		Timestamp dateTime = converter.convertToDatabaseColumn(locDateTime);
		assertEquals(expected.getTimeInMillis(), dateTime.getTime(), "expected="
				+ expected.getTime().toString() + ", actual=" + dateTime);
	}

	@Test
	void testConvertToEntityAttribute() {

		LocalDateTime locDateTime = LocalDateTime.of(2017, Month.JUNE, 27, 21,
				20, 30);
		Calendar expected = new GregorianCalendar(2017, Calendar.JUNE, 27, 21,
				20, 30);
		LocalDateTime actual = converter.convertToEntityAttribute(
				new Timestamp(expected.getTimeInMillis()));
		assertEquals(locDateTime, actual);
	}

}
