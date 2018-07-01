package de.kreth.clubinvoice.business;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.Invoice;
import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceBusiness extends AbstractBusiness<Invoice> {

	public InvoiceBusiness(Session sessionObj, PropertyStore propStore) {
		super(sessionObj, propStore, Invoice.class);
	}

	@Override
	public boolean save(Invoice obj) {

		sessionObj.beginTransaction();
		sessionObj.save(obj);
		for (InvoiceItem i : obj.getItems()) {
			i.setInvoice(obj);
			sessionObj.save(i);
		}

		sessionObj.getTransaction().commit();
		return true;
	}

}
