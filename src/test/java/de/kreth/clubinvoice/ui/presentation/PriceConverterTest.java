package de.kreth.clubinvoice.ui.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vaadin.data.Result;

import junit.framework.AssertionFailedError;

class PriceConverterTest {

	private PriceConverter converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new PriceConverter(BigDecimal.ZERO, "Error Message", Locale.GERMANY);
	}

	@Test
	void testConvertToModelNull() {
		Result<BigDecimal> value = converter.convertToModel(null, null);
		assertFalse(value.isError());

		assertEquals(BigDecimal.ZERO, value.getOrThrow(this::exceptionProvider));

	}

	@Test
	void testConvertToModelEuro() {
		Result<BigDecimal> value = converter.convertToModel("13,70\u00a0€", null);
		assertFalse(value.isError());

		assertEquals(BigDecimal.valueOf(13.7), value.getOrThrow(this::exceptionProvider));

	}

	@Test
	void testConvertToModelNumberOnly() {
		Result<BigDecimal> value = converter.convertToModel("13,7", null);
		assertFalse(value.isError());

		assertEquals(BigDecimal.valueOf(13.7), value.getOrThrow(this::exceptionProvider));

		value = converter.convertToModel(" 13,7 ", null);
		assertFalse(value.isError());

	}

	@Test
	void test() {
		String presentation = converter.convertToPresentation(BigDecimal.valueOf(13.7), null);
		assertEquals("13,70\u00a0€", presentation);
	}

	public AssertionFailedError exceptionProvider(String text) {
		return new AssertionFailedError(text);
	}
}
