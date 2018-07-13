package de.kreth.clubinvoice.ui.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.Binder.BindingBuilder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.utils.DateTimeBinder;

public class InvoiceItemDialog extends Window {

	private static final long serialVersionUID = -8997281625128779760L;

	private InvoiceItem item;
	private final List<Article> selectableArticles;

	private ComboBox<Article> articleBox;
	private TextField endTimeField;
	private TextField startTimeField;
	private DateField dateField;
	private TextField participants;

	private Binder<InvoiceItem> binder;

	private Button okButton;
	private List<ClickListener> okListeners;

	private ResourceBundle resBundle;

	public InvoiceItemDialog(ResourceBundle resBundle) {
		this.resBundle = resBundle;
		item = new InvoiceItem();
		item.setStart(LocalDate.now().atStartOfDay());
		item.setEnd(LocalDate.now().atStartOfDay());

		selectableArticles = new ArrayList<>();

		dateField = new DateField();
		dateField.setLocale(Locale.getDefault());
		dateField.setCaption(resBundle.getString("caption.invoiceitem.date"));
		startTimeField = new TextField();
		startTimeField.setRequiredIndicatorVisible(true);
		startTimeField
				.setCaption(resBundle.getString("caption.invoiceitem.start"));
		endTimeField = new TextField();
		endTimeField.setRequiredIndicatorVisible(true);
		endTimeField.setCaption(resBundle.getString("caption.invoiceitem.end"));
		participants = new TextField();
		participants.setCaption(
				resBundle.getString("caption.invoiceitem.participants"));

		articleBox = new ComboBox<>();
		articleBox.setCaption(resBundle.getString("caption.articles"));
		articleBox.setItemCaptionGenerator(Article::getTitle);
		articleBox.addSelectionListener(ev -> {
			item.setArticle(ev.getSelectedItem().orElse(item.getArticle()));
		});

		bindItemFields();

		okButton = new Button(resBundle.getString("label.ok"));
		okListeners = new ArrayList<>();
		okListeners.add(ev -> {
			close();
		});

		okButton.addClickListener(ev -> {
			if (isValid()) {
				for (ClickListener clLi : okListeners) {
					clLi.buttonClick(ev);
				}
			}
		});

		Button cancelButton = new Button(resBundle.getString("label.cancel"),
				ev -> close());

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(okButton, cancelButton);

		VerticalLayout content = new VerticalLayout();
		content.addComponents(articleBox, dateField, startTimeField,
				endTimeField, participants, buttons);
		setContent(content);
		center();
	}

	private boolean isValid() {
		binder.writeBeanIfValid(item);
		if (item.getArticle() == null || item.getStart() == null
				|| item.getEnd() == null) {
			Notification.show(
					resBundle.getString(
							"message.invoiceitem.allfieldsmustbeset"),
					Notification.Type.ERROR_MESSAGE);
			return false;
		}
		if (item.getStart().toLocalDate()
				.equals(item.getEnd().toLocalDate()) == false) {
			// should never happen!
			Notification.show("Error: update start and end to fix.",
					Notification.Type.ERROR_MESSAGE);
			return false;
		}
		if (item.getStart().isAfter(item.getEnd())) {
			UserError componentError = new UserError(
					resBundle.getString("message.invoiceitem.startbeforeend"));
			startTimeField.setComponentError(componentError);
			endTimeField.setComponentError(componentError);
			return false;
		}
		BigDecimal sumPrice = item.getSumPrice();
		if (sumPrice == null || sumPrice.doubleValue() <= 0) {
			Notification.show(
					resBundle.getString(
							"message.invoiceitem.allfieldsmustbeset"),
					Notification.Type.ERROR_MESSAGE);
		}
		return true;
	}

	public void bindItemFields() {

		binder = new Binder<>();

		ValueProvider<InvoiceItem, LocalDate> getter = new ValueProvider<InvoiceItem, LocalDate>() {

			private static final long serialVersionUID = -7354502111873472077L;

			@Override
			public LocalDate apply(InvoiceItem source) {
				return source.getStart().toLocalDate();
			}
		};
		Setter<InvoiceItem, LocalDate> setter = new Setter<InvoiceItem, LocalDate>() {

			private static final long serialVersionUID = 4253885526871538754L;

			@Override
			public void accept(InvoiceItem bean, LocalDate fieldvalue) {

				LocalDateTime value = updateValue(fieldvalue, bean.getStart());
				bean.setStart(value);
				value = updateValue(fieldvalue, bean.getEnd());
				bean.setEnd(value);
			}

			private LocalDateTime updateValue(LocalDate fieldvalue,
					LocalDateTime value) {
				if (value == null) {
					value = fieldvalue.atStartOfDay();
				} else {
					value = fieldvalue.atTime(value.toLocalTime());
				}
				return value;
			}
		};

		binder.forField(dateField).asRequired().bind(getter, setter);

		createBinding(startTimeField, item::getStart, item::setStart,
				DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

		createBinding(endTimeField, item::getEnd, item::setEnd,
				DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

		binder.forField(articleBox).bind(InvoiceItem::getArticle,
				InvoiceItem::setArticle);

		binder.forField(participants).bind(InvoiceItem::getParticipants,
				InvoiceItem::setParticipants);

		binder.readBean(item);
	}

	private Binding<InvoiceItem, String> createBinding(TextField field,
			Supplier<LocalDateTime> getter, Consumer<LocalDateTime> setter,
			DateTimeFormatter formatter) {

		DateTimeBinder<InvoiceItem> dateBinding = new DateTimeBinder<>(getter,
				setter, formatter);

		BindingBuilder<InvoiceItem, String> bindBuilder = binder
				.forField(field);
		return bindBuilder.bind(dateBinding.getValueProvider(),
				dateBinding.getSetter());

	}
	public void addOkClickListener(ClickListener listener) {
		okListeners.add(listener);
	}

	public void setSelectableArticles(List<Article> selectableArticles) {
		this.selectableArticles.clear();
		this.selectableArticles.addAll(selectableArticles);
		articleBox.setItems(selectableArticles);
		articleBox.setSelectedItem(selectableArticles.get(0));
	}

	public InvoiceItem getItem() {
		return item;
	}

	public void setItem(InvoiceItem item) {
		this.item = item;
		binder.setBean(item);
		binder.readBean(item);

	}

}
