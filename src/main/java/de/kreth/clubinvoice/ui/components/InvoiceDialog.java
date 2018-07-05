package de.kreth.clubinvoice.ui.components;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ResourceBundle;

import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceDialog extends Window {

	private static final long serialVersionUID = -8997281625128779760L;
	private TextField invoiceNo;
	private TextField invoiceDate;
	private InvoiceItemGrid<InvoiceItem> itemGrid;
	private Button okButton;

	public InvoiceDialog(ResourceBundle resBundle) {
		invoiceNo = new TextField();
		invoiceNo.setCaption(resBundle.getString("caption.invoice.invoiceno"));
		invoiceNo.setReadOnly(true);
		invoiceDate = new TextField();
		invoiceDate
				.setCaption(resBundle.getString("caption.invoice.invoicedate"));
		invoiceDate.setReadOnly(true);

		itemGrid = new InvoiceItemGrid<>(resBundle);

		okButton = new Button(resBundle.getString("label.ok"), ev -> close());
		Button cancel = new Button(resBundle.getString("label.cancel"),
				ev -> close());
		HorizontalLayout btnLayout = new HorizontalLayout();
		btnLayout.addComponents(okButton, cancel);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.addComponents(invoiceNo, invoiceDate, itemGrid, btnLayout);
		setContent(vLayout);
		Invoice invoice = new Invoice();
		invoice.setInvoiceId("");
		invoice.setInvoiceDate(LocalDateTime.now());
		invoice.setItems(Collections.emptyList());
		setInvoice(invoice);
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public void setInvoice(Invoice invoice) {
		invoiceNo.setValue(invoice.getInvoiceId());
		invoiceDate.setValue(invoice.getInvoiceDate().toString());
		itemGrid.setItems(invoice.getItems());
	}

	public void setOkVisible(boolean visible) {
		okButton.setVisible(visible);
	}

}
