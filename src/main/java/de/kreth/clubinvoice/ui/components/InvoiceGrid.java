package de.kreth.clubinvoice.ui.components;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Invoice;

public class InvoiceGrid extends Grid<Invoice> {

	private static final long serialVersionUID = 662980245990122807L;

	public InvoiceGrid() {
		setCaption("Invoices");
		setStyleName("bordered");

		addColumn(Invoice::getInvoiceId).setCaption("Invoice No.");
		addColumn(Invoice::getInvoiceDate).setCaption("Invoice Date")
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		addColumn(Invoice::getSum).setCaption("Sum").setRenderer(
				new NumberRenderer(NumberFormat.getCurrencyInstance()));

	}
}
