package de.kreth.clubinvoice.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter
		implements
			AttributeConverter<LocalDate, Date> {

	private static final ZoneId ZONE_ID = ZoneId.systemDefault();
	private static ZoneOffset offset = OffsetDateTime.now(ZONE_ID).getOffset();

	@Override
	public Date convertToDatabaseColumn(LocalDate locDate) {
		return (locDate == null
				? null
				: Date.from(locDate.atStartOfDay().toInstant(offset)));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		if (sqlDate == null) {
			return null;
		}
		Instant instant = sqlDate.toInstant();
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZONE_ID);
		return dateTime.toLocalDate();
	}
}
