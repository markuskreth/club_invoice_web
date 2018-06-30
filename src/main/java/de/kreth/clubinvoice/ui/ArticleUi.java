package de.kreth.clubinvoice.ui;

import java.util.ResourceBundle;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ArticleUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 1681902867644983698L;
	private final Label caption = new Label("Artikel");
	private ResourceBundle resBundle;

	public ArticleUi() {
		this.resBundle = ResourceBundle.getBundle("/application");
	}
	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {
		ui.getPage().setTitle("Artikel");

		addComponents(caption);
		ui.setContent(this);
	}

}
