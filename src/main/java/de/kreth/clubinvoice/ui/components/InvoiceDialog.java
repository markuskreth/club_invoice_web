package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_INVOICEDATE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_INVOICENO;
import static de.kreth.clubinvoice.Application_Properties.LABEL_CANCEL;
import static de.kreth.clubinvoice.Application_Properties.LABEL_OPEN;
import static de.kreth.clubinvoice.Application_Properties.LABEL_PREVIEW;
import static de.kreth.clubinvoice.Application_Properties.LABEL_STORE;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.report.InvoiceReportSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class InvoiceDialog extends Window {

	private static final long serialVersionUID = -8997281625128779760L;

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDialog.class);

	public enum InvoiceMode {
		CREATE,
		VIEW_ONLY
	}

	private TextField invoiceNo;

	private DateTimeField invoiceDate;

	private InvoiceItemGrid<InvoiceItem> itemGrid;

	private Button okButton;

	private Invoice invoice;

	private ResourceBundle resBundle;

	public InvoiceDialog(ResourceBundle resBundle, InvoiceMode pdfOpenLabel) {
		this.resBundle = resBundle;
		setWidth(200, Unit.EM);

		invoiceNo = new TextField();
		invoiceNo.setCaption(getString(CAPTION_INVOICE_INVOICENO));
		if (InvoiceMode.VIEW_ONLY == pdfOpenLabel) {
			invoiceNo.setReadOnly(true);
		}
		else {
			invoiceNo.addValueChangeListener(this::updateInvoiceNo);
		}

		invoiceDate = new DateTimeField();
		invoiceDate.setCaption(getString(CAPTION_INVOICE_INVOICEDATE));
		invoiceDate.setReadOnly(true);

		itemGrid = new InvoiceItemGrid<>(resBundle);
		itemGrid.setSizeFull();

		okButton = new Button(getString(LABEL_STORE), ev -> close());
		Button cancel = new Button(getString(LABEL_CANCEL), ev -> close());

		String caption;
		if (pdfOpenLabel == InvoiceMode.VIEW_ONLY) {
			caption = getString(LABEL_OPEN);
		}
		else {
			caption = getString(LABEL_PREVIEW);
		}
		Button previewButton = new Button(caption, this::showPdf);
		HorizontalLayout btnLayout = new HorizontalLayout();
		btnLayout.addComponents(okButton, cancel, previewButton);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.addComponents(invoiceNo, invoiceDate, itemGrid, btnLayout);
		vLayout.setSizeFull();

		setContent(vLayout);
		Invoice invoice = new Invoice();
		invoice.setInvoiceId("");
		invoice.setInvoiceDate(LocalDateTime.now());
		invoice.setItems(Collections.emptyList());
		setInvoice(invoice);
	}

	private String getString(Application_Properties property) {
		return property.getString(resBundle::getString);
	}

	private void updateInvoiceNo(ValueChangeEvent<String> ev) {
		if (invoice != null) {
			invoice.setInvoiceId(ev.getValue());
		}
	}

	private void showPdf(ClickEvent ev) {
		try {
			JasperPrint print = createJasperPrint();
			LOGGER.debug("Created JasperPrint");
			showInWebWindow(print, ev);
		}
		catch (JRException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void showInWebWindow(JasperPrint print, ClickEvent ev) throws IOException, JRException {

		Window window = new Window();
		window.setCaption("View PDF");
		AbstractComponent e = createEmbedded(print);
		window.setContent(e);
		window.setModal(true);
		window.setWidth("50%");
		window.setHeight("90%");

		ev.getButton().getUI().addWindow(window);
	}

	private AbstractComponent createEmbedded(JasperPrint print) throws IOException, JRException {

		PipedInputStream inFrame = new PipedInputStream();
		final StreamResource resourceFrame = new StreamResource(() -> inFrame, "invoice.pdf");
		resourceFrame.setMIMEType("application/pdf");

		BrowserFrame c = new BrowserFrame("PDF invoice", resourceFrame);
		c.setResponsive(true);
		c.setAlternateText("Unable to show PDF. Please click Download link.");
		c.setSizeFull();
		ExecutorService exec = Executors.newSingleThreadExecutor();
		File outFile = Files.createTempFile("invoice", ".pdf").toFile();
		exec.execute(() -> {
			try (PipedOutputStream out1 = new PipedOutputStream(inFrame)) {
				JasperExportManager.exportReportToPdfStream(print, out1);
			}
			catch (JRException | IOException e) {
				LOGGER.error("Error exporting Report to Browser Window", e);
			}
		});
		exec.shutdown();

		JasperExportManager.exportReportToPdfFile(print, outFile.getAbsolutePath());
		LOGGER.info("PDF File written: {}", outFile.getAbsolutePath());

		Link link = new Link("Download PDF", new FileResource(outFile));

		link.addContextClickListener(ev -> LOGGER.debug("Download link clicked."));
		link.addAttachListener(ev -> LOGGER.debug("Download link attached."));
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(link);
		layout.addComponent(c);
		layout.setSizeFull();
		return layout;
	}

	public JasperPrint createJasperPrint() throws JRException {
		InvoiceReportSource source = new InvoiceReportSource();
		source.setInvoice(invoice);
		JasperReport report = JasperCompileManager
				.compileReport(getClass().getResourceAsStream(invoice.getItems().get(0).getArticle().getReport()));
		return JasperFillManager.fillReport(report, new HashMap<>(), source);
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
		invoiceNo.setValue(invoice.getInvoiceId());
		invoiceDate.setValue(invoice.getInvoiceDate());
		itemGrid.setItems(invoice.getItems());
	}

	public void setOkVisible(boolean visible) {
		okButton.setVisible(visible);
	}

}
