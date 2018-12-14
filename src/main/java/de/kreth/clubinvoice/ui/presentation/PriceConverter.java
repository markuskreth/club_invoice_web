package de.kreth.clubinvoice.ui.presentation;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.vaadin.data.ErrorMessageProvider;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.AbstractStringToNumberConverter;

public class PriceConverter extends AbstractStringToNumberConverter<BigDecimal> {

	private static final long serialVersionUID = -2627857228509459543L;
	private final NumberFormat formatter;
	private final NumberFormat backupFormat;

	public PriceConverter(BigDecimal emptyValue, ErrorMessageProvider errorMessageProvider) {
		this(emptyValue, errorMessageProvider, Locale.getDefault());
	}

	public PriceConverter(BigDecimal emptyValue, String errorMessage) {
		this(emptyValue, errorMessage, Locale.getDefault());
	}

	public PriceConverter(BigDecimal emptyValue, ErrorMessageProvider errorMessageProvider, Locale inLocale) {
		super(emptyValue, errorMessageProvider);
		formatter = NumberFormat.getCurrencyInstance(inLocale);
		backupFormat = NumberFormat.getInstance(inLocale);
	}

	public PriceConverter(BigDecimal emptyValue, String errorMessage, Locale inLocale) {
		super(emptyValue, errorMessage);
		formatter = NumberFormat.getCurrencyInstance(inLocale);
		backupFormat = NumberFormat.getInstance(inLocale);
	}

	@Override
	public Result<BigDecimal> convertToModel(String value, ValueContext context) {
		if (value == null) {
			return Result.ok(BigDecimal.ZERO);
		}
		try {
			Number numValue = formatter.parse(value);
			return Result.ok(BigDecimal.valueOf(numValue.doubleValue()));
		} catch (ParseException e) {
			try {
				Number numValue = backupFormat.parse(value.trim());
				return Result.ok(BigDecimal.valueOf(numValue.doubleValue()));
			} catch (ParseException e2) {
				return Result.error(e.getMessage());
			}
		}
	}

	@Override
	protected NumberFormat getFormat(Locale locale) {
		return formatter;
	}

	@Override
	public String convertToPresentation(BigDecimal value, ValueContext context) {
		if (value == null) {
			return null;
		}
		return formatter.format(value.doubleValue());
	}
}
