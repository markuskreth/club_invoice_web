package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICES;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_INVOICEDATE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_INVOICENO;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_SUM;

import java.text.NumberFormat;
import java.util.ResourceBundle;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Invoice;

public class InvoiceGrid extends Grid<Invoice> {

	private static final long serialVersionUID = 662980245990122807L;

	public InvoiceGrid(ResourceBundle resBundle) {
		setCaption(resBundle.getString(CAPTION_INVOICES.getValue()));
		setStyleName("bordered");

		addColumn(Invoice::getInvoiceId).setCaption(resBundle.getString(CAPTION_INVOICE_INVOICENO.getValue()));
		addColumn(Invoice::getInvoiceDate).setCaption(resBundle.getString(CAPTION_INVOICE_INVOICEDATE.getValue()))
				.setRenderer(new LocalDateTimeRenderer("dd.MM.yyyy"));
		addColumn(Invoice::getSum).setCaption(CAPTION_INVOICE_SUM.getString(resBundle::getString))
				.setRenderer(new NumberRenderer(NumberFormat.getCurrencyInstance()));

	}
}
