package de.kreth.clubinvoice.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vaadin.server.Setter;

class DateTimeBinderTest {

	private final LocalDateTime dateTime = LocalDateTime.of(2018, 8, 21, 17, 11, 7);
	private DateTimeObj obj;
	
	@BeforeEach
	void initBinder() {
		obj = new DateTimeObj();
	}
	
	@Test
	void testGetValueProvider() {

		DateTimeFormatter dfFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		obj.value = dateTime;
		String value = new DateTimeBinder<>(obj::getValue, obj::setValue, dfFormat).getValueProvider().apply(obj);
		assertEquals("2018-08-21 17:11:07", value);
	}

	@Test
	void testTimeOnly() {
		DateTimeFormatter dfFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

		obj.value = LocalDateTime.MIN;
		Setter<Object, String> setter = new DateTimeBinder<>(obj::getValue, obj::setValue, dfFormat).getSetter();
		setter.accept(obj, "17:11");

		assertEquals(dateTime.getHour(), obj.value.getHour());
		assertEquals(dateTime.getMinute(), obj.value.getMinute());
	}
	
	@Test
	void testGetSetter() {
		DateTimeFormatter dfFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		obj.value = LocalDateTime.MIN;
		new DateTimeBinder<>(obj::getValue, obj::setValue, dfFormat).getSetter().accept(obj, "2018-08-21 17:11:07");
		
		assertEquals(dateTime.withHour(0).withMinute(0).withSecond(0), obj.value);
	}

	class DateTimeObj {
		LocalDateTime value;

		public LocalDateTime getValue() {
			return value;
		}

		public void setValue(LocalDateTime value) {
			this.value = value;
		}
		
	}
}
