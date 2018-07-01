package de.kreth.clubinvoice.ui.components;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceItemGrid<T extends InvoiceItem> extends Grid<T> {

	private static final long serialVersionUID = -8653320112619816426L;

	public InvoiceItemGrid(ResourceBundle resBundle) {
		setCaption(resBundle.getString("caption.invoiceitems"));
		setStyleName("bordered");

		addColumn(InvoiceItem::getStart)
				.setCaption(resBundle.getString("caption.invoiceitem.date"))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		addColumn(InvoiceItem::getStart)
				.setCaption(resBundle.getString("caption.invoiceitem.start"))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
		addColumn(InvoiceItem::getEnd)
				.setCaption(resBundle.getString("caption.invoiceitem.end"))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));

		addColumn(InvoiceItem::getSumPrice)
				.setCaption(resBundle.getString("caption.invoiceitem.sumprice"))
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()));

	}
}
