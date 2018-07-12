package de.kreth.clubinvoice.ui.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.HashMap;
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
import de.kreth.clubinvoice.report.InvoiceReportSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class InvoiceDialog extends Window {

	private static final long serialVersionUID = -8997281625128779760L;
	private static final String MTV_JRXML = "/reports/mtv_gross_buchholz.jrxml";
	private TextField invoiceNo;
	private TextField invoiceDate;
	private InvoiceItemGrid<InvoiceItem> itemGrid;
	private Button okButton;
	private Invoice invoice;

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
		Button previewButton = new Button("Preview", ev -> showPdf());
		HorizontalLayout btnLayout = new HorizontalLayout();
		btnLayout.addComponents(okButton, cancel, previewButton);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.addComponents(invoiceNo, invoiceDate, itemGrid, btnLayout);
		setContent(vLayout);
		Invoice invoice = new Invoice();
		invoice.setInvoiceId("");
		invoice.setInvoiceDate(LocalDateTime.now());
		invoice.setItems(Collections.emptyList());
		setInvoice(invoice);
	}

	private void showPdf() {

		InvoiceReportSource source = new InvoiceReportSource();
		source.setInvoice(invoice);

		try {
			JasperReport report = JasperCompileManager
					.compileReport(getClass().getResourceAsStream(MTV_JRXML));
			JasperPrint print = JasperFillManager.fillReport(report,
					new HashMap<>(), source);
			JasperViewer.viewReport(print, false);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
		invoiceNo.setValue(invoice.getInvoiceId());
		invoiceDate.setValue(invoice.getInvoiceDate()
				.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		itemGrid.setItems(invoice.getItems());
	}

	public void setOkVisible(boolean visible) {
		okButton.setVisible(visible);
	}

}
