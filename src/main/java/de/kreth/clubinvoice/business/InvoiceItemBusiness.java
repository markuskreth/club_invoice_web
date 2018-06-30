package de.kreth.clubinvoice.business;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceItemBusiness extends AbstractBusiness<InvoiceItem> {

	public InvoiceItemBusiness(Session sessionObj, PropertyStore propStore) {
		super(sessionObj, propStore, InvoiceItem.class);
	}

}
