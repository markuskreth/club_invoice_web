package de.kreth.clubinvoice.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.kreth.clubinvoice.business.PropertyStore;
import de.kreth.clubinvoice.data.User;

public class OverviewUi extends VerticalLayout implements InvoiceUi {

	private static final long serialVersionUID = 318645298331660865L;
	private User user;
	private PropertyStore store;

	public OverviewUi(PropertyStore store) {
		super();
		this.store = store;
	}

	@Override
	public void setContent(UI ui, VaadinRequest vaadinRequest) {

		this.user = (User) store.getAttribute(PropertyStore.LOGGED_IN_USER);
		Label l1 = new Label("Logged in:");
		Label l2 = new Label(user.toString());
		VerticalLayout head = new VerticalLayout();
		head.addComponents(l1, l2);
		HorizontalLayout main = new HorizontalLayout();
		GridLayout left = new GridLayout(3, 2);
		GridLayout right = new GridLayout(3, 2);
		main.addComponents(left, right);
		addComponents(head, main);
		ui.setContent(this);
	}

}
