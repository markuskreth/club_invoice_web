package de.kreth.clubinvoice.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public interface InvoiceUi extends Constants {

	void setContent(UI ui, VaadinRequest vaadinRequest);

}