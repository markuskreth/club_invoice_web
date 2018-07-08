package de.kreth.clubinvoice.ui.components;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Invoice;

public class InvoiceGrid extends Grid<Invoice> {

	private static final long serialVersionUID = 662980245990122807L;

	public InvoiceGrid(ResourceBundle resBundle) {
		setCaption(resBundle.getString("caption.invoices"));
		setStyleName("bordered");

		addColumn(Invoice::getInvoiceId)
				.setCaption(resBundle.getString("caption.invoice.invoiceno"));
		addColumn(Invoice::getInvoiceDate)
				.setCaption(resBundle.getString("caption.invoice.invoicedate"))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		addColumn(Invoice::getSum)
				.setCaption(resBundle.getString("caption.invoice.sum"))
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()));

	}
}
