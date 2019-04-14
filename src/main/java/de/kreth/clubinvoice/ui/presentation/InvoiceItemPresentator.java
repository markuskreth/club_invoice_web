package de.kreth.clubinvoice.ui.presentation;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_DATE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_END;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_NAME;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_START;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.data.InvoiceItem;
import de.kreth.clubinvoice.utils.ResourceBundleProvider;

class InvoiceItemPresentator implements DataPresentator<InvoiceItem> {

	DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

	DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

	@Override
	public String presentationString(InvoiceItem obj) {
		if (obj == null) {
			return "null";
		}
		StringBuilder text = new StringBuilder();
		text.append(getString(CAPTION_INVOICEITEM_NAME));
		text.append(" [").append(getString(CAPTION_INVOICEITEM_DATE)).append("=");
		text.append(dateFormat.format(obj.getStart()));
		text.append(", ").append(getString(CAPTION_INVOICEITEM_START)).append("=");
		text.append(timeFormat.format(obj.getStart()));
		text.append(", ").append(getString(CAPTION_INVOICEITEM_END)).append("=");
		text.append(timeFormat.format(obj.getEnd()));
		text.append(", ").append(getString(CAPTION_ARTICLE)).append("=");
		text.append(obj.getArticle().getTitle());
		text.append("]");
		return text.toString();
	}

	private String getString(Application_Properties properties) {
		return properties.getString(ResourceBundleProvider.getBundle()::getString);
	}

	@Override
	public Class<InvoiceItem> forClass() {
		return InvoiceItem.class;
	}

}
