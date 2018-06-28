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
		Instant instant = locDate.atStartOfDay().toInstant(offset);
		return (locDate == null
				? null
				: Date.from(instant));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		Instant instant = sqlDate.toInstant();
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZONE_ID);
		return (sqlDate == null ? null : dateTime.toLocalDate());
	}
}
