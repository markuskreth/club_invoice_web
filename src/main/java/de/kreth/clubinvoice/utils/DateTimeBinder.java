package de.kreth.clubinvoice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;

public class DateTimeBinder<T> {

	private final DateTimeFormatter dfFormat;

	private final Supplier<LocalDateTime> getter;
	private final Consumer<LocalDateTime> setter;

	public DateTimeBinder(Supplier<LocalDateTime> getter,
			Consumer<LocalDateTime> setter, DateTimeFormatter dfFormat) {
		super();
		this.getter = getter;
		this.setter = setter;
		this.dfFormat = dfFormat;
	}

	public ValueProvider<T, String> getValueProvider() {
		return new InnerValueProvider();
	}

	public Setter<T, String> getSetter() {
		return new InnerSetter();
	}

	protected class InnerSetter implements Setter<T, String> {

		private static final long serialVersionUID = 6366152099110154067L;

		@Override
		public void accept(T bean, String fieldvalue) {
			TemporalAccessor tmp = dfFormat.parse(fieldvalue);
			setter.accept(setDateValues(getter.get(), tmp));
		}

		private LocalDateTime setDateValues(LocalDateTime start,
				TemporalAccessor tmp) {
			LocalDateTime result = start;
			if (tmp.isSupported(ChronoField.YEAR)) {
				result = start.withYear(tmp.get(ChronoField.YEAR))
						.withMonth(tmp.get(ChronoField.MONTH_OF_YEAR))
						.withDayOfMonth(tmp.get(ChronoField.DAY_OF_MONTH));
			} else {
				result = start.withHour(tmp.get(ChronoField.HOUR_OF_DAY))
						.withMinute(tmp.get(ChronoField.MINUTE_OF_HOUR))
						.withSecond(tmp.get(ChronoField.SECOND_OF_MINUTE));
			}
			return result;
		}
	}

	protected class InnerValueProvider implements ValueProvider<T, String> {

		private static final long serialVersionUID = -4689472830911574888L;

		@Override
		public String apply(T source) {
			return dfFormat.format(getter.get());
		}

	}
}
