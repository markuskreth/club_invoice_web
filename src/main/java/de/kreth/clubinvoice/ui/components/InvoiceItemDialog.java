package de.kreth.clubinvoice.ui.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.Binder.BindingBuilder;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
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
	private TextField dateField;

	private Binder<InvoiceItem> dateBinder;

	private Button okButton;
	private List<ClickListener> okListeners;

	private ResourceBundle resBundle;

	public InvoiceItemDialog(ResourceBundle resBundle) {
		this.resBundle = resBundle;
		item = new InvoiceItem();
		item.setStart(LocalDate.now().atStartOfDay());
		item.setEnd(LocalDate.now().atStartOfDay());

		selectableArticles = new ArrayList<>();

		dateField = new TextField();
		dateField.setCaption(resBundle.getString("caption.invoiceitem.date"));
		startTimeField = new TextField();
		startTimeField
				.setCaption(resBundle.getString("caption.invoiceitem.start"));
		endTimeField = new TextField();
		endTimeField.setCaption(resBundle.getString("caption.invoiceitem.end"));
		articleBox = new ComboBox<>();
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
				endTimeField, buttons);
		setContent(content);
		center();
	}

	private boolean isValid() {
		dateBinder.writeBeanIfValid(item);
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
		if (sumPrice == null
				|| sumPrice.doubleValue() <= 0) {
			Notification.show(
					resBundle.getString(
							"message.invoiceitem.allfieldsmustbeset"),
					Notification.Type.ERROR_MESSAGE);
		}
		return true;
	}

	public void bindItemFields() {

		dateBinder = new Binder<>();

		createBinding(dateField, item::getStart, item::setStart,
				DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

		createBinding(dateField, item::getStart, item::setEnd,
				DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

		createBinding(startTimeField, item::getStart, item::setStart,
				DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

		createBinding(endTimeField, item::getEnd, item::setEnd,
				DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

		dateBinder.forField(articleBox).bind(InvoiceItem::getArticle,
				InvoiceItem::setArticle);
		dateBinder.readBean(item);
	}

	private Binding<InvoiceItem, String> createBinding(TextField field,
			Supplier<LocalDateTime> getter, Consumer<LocalDateTime> setter,
			DateTimeFormatter formatter) {

		DateTimeBinder<InvoiceItem> dateBinding = new DateTimeBinder<>(getter,
				setter, formatter);

		BindingBuilder<InvoiceItem, String> bindBuilder = dateBinder
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
		dateBinder.setBean(item);
		dateBinder.readBean(item);

	}

}
