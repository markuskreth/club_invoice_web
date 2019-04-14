package de.kreth.clubinvoice.business;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

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
		for (InvoiceItem i : obj.getItems()) {
			i.setInvoice(obj);
			// sessionObj.saveOrUpdate(i);
		}
		sessionObj.save(obj);

		sessionObj.getTransaction().commit();
		return true;
	}

	public String createNextInvoiceId(List<Invoice> invoices, String pattern) {

		Optional<Invoice> latest = invoices.stream().max((o1, o2) -> {
			return o1.getInvoiceId().compareTo(o2.getInvoiceId());
		});

		int lastInvoiceId = 0;

		if (latest.isPresent()) {
			String old = latest.get().getInvoiceId();
			int start = pattern.indexOf("{0}");
			String substring = old.substring(start);
			if (substring.matches("[0-9]+")) {
				lastInvoiceId = Integer.parseInt(substring);
			}
			else {
				lastInvoiceId++;
			}
		}

		lastInvoiceId++;

		String invoiceNo = MessageFormat.format(pattern, lastInvoiceId);
		return invoiceNo;
	}

}
