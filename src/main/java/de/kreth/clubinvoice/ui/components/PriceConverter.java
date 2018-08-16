package de.kreth.clubinvoice.ui.components;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.vaadin.data.ErrorMessageProvider;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.AbstractStringToNumberConverter;

public class PriceConverter
		extends
			AbstractStringToNumberConverter<BigDecimal> {

	private static final long serialVersionUID = -2627857228509459543L;
	private final NumberFormat formatter = NumberFormat.getCurrencyInstance();

	public PriceConverter(BigDecimal emptyValue,
			ErrorMessageProvider errorMessageProvider) {
		super(emptyValue, errorMessageProvider);
	}

	public PriceConverter(BigDecimal emptyValue, String errorMessage) {
		super(emptyValue, errorMessage);
	}

	@Override
	public Result<BigDecimal> convertToModel(String value,
			ValueContext context) {
		if (value == null) {
			return Result.ok(BigDecimal.ZERO);
		}
		try {
			Number numValue = formatter.parse(value);
			return Result.ok(BigDecimal.valueOf(numValue.doubleValue()));
		} catch (ParseException e) {
			return Result.error(e.getMessage());
		}
	}

	@Override
	protected NumberFormat getFormat(Locale locale) {
		return formatter;
	}

	@Override
	public String convertToPresentation(BigDecimal value,
			ValueContext context) {
		if (value == null) {
			return null;
		}
		return formatter.format(value.doubleValue());
	}
}
