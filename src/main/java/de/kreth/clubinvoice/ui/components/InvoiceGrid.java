package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICES;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICE_INVOICEDATE;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICE_INVOICENO;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICE_SUM;

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
		setCaption(resBundle.getString(CAPTION_INVOICES));
		setStyleName("bordered");

		addColumn(Invoice::getInvoiceId)
				.setCaption(resBundle.getString(CAPTION_INVOICE_INVOICENO));
		addColumn(Invoice::getInvoiceDate)
				.setCaption(resBundle.getString(CAPTION_INVOICE_INVOICEDATE))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		addColumn(Invoice::getSum)
				.setCaption(resBundle.getString(CAPTION_INVOICE_SUM))
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()));

	}
}
