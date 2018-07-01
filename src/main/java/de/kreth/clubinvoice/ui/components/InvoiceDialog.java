package de.kreth.clubinvoice.ui.components;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceDialog extends Window {

	private static final long serialVersionUID = -8997281625128779760L;
	private TextField invoiceNo;
	private TextField invoiceDate;
	private InvoiceItemGrid<SelectableInvoiceItem> itemGrid;
	private Button okButton;
	private Invoice invoice;
	private List<SelectableInvoiceItem> items;

	public InvoiceDialog(ResourceBundle resBundle) {
		invoiceNo = new TextField();
		invoiceNo.setCaption(resBundle.getString("caption.invoice.invoiceno"));
		invoiceDate = new TextField();
		invoiceDate
				.setCaption(resBundle.getString("caption.invoice.invoicedate"));
		itemGrid = new InvoiceItemGrid<>(resBundle);
		itemGrid.addColumn(SelectableInvoiceItem::isSelected).setCaption("");

		okButton = new Button(resBundle.getString("label.ok"), ev -> close());
		Button cancel = new Button(resBundle.getString("label.cancel"),
				ev -> close());
		HorizontalLayout btnLayout = new HorizontalLayout();
		btnLayout.addComponents(okButton, cancel);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.addComponents(invoiceNo, invoiceDate, itemGrid, btnLayout);
		setContent(vLayout);
		invoice = new Invoice();
		items = new ArrayList<>();
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items.stream().map(i -> new SelectableInvoiceItem(i))
				.collect(Collectors.toList());
	}

	class SelectableInvoiceItem extends InvoiceItem {
		private boolean selected;
		private final InvoiceItem original;

		public SelectableInvoiceItem(InvoiceItem original) {
			this.original = original;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public int getId() {
			return original.getId();
		}

		public void setId(int id) {
			original.setId(id);
		}

		public LocalDateTime getStart() {
			return original.getStart();
		}

		public void setStart(LocalDateTime start) {
			original.setStart(start);
		}

		public LocalDateTime getEnd() {
			return original.getEnd();
		}

		public void setEnd(LocalDateTime end) {
			original.setEnd(end);
		}

		public Article getArticle() {
			return original.getArticle();
		}

		public void setArticle(Article article) {
			original.setArticle(article);
		}

		public BigDecimal getSumPrice() {
			return original.getSumPrice();
		}

		public Invoice getInvoice() {
			return original.getInvoice();
		}

		public void setInvoice(Invoice invoice) {
			original.setInvoice(invoice);
		}

		public LocalDateTime getCreatedDate() {
			return original.getCreatedDate();
		}

		public void setCreatedDate(LocalDateTime createdDate) {
			original.setCreatedDate(createdDate);
		}

		public LocalDateTime getChangeDate() {
			return original.getChangeDate();
		}

		public void setChangeDate(LocalDateTime changeDate) {
			original.setChangeDate(changeDate);
		}

		public String toString() {
			return original.toString();
		}

		public long getDurationInMinutes() {
			return original.getDurationInMinutes();
		}

	}
}
