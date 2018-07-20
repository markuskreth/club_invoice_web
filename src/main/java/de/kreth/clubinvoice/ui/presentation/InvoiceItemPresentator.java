package de.kreth.clubinvoice.ui.presentation;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import de.kreth.clubinvoice.data.InvoiceItem;

class InvoiceItemPresentator implements DataPresentator<InvoiceItem> {

	DateTimeFormatter dateFormat = DateTimeFormatter
			.ofLocalizedDate(FormatStyle.MEDIUM);
	DateTimeFormatter timeFormat = DateTimeFormatter
			.ofLocalizedTime(FormatStyle.SHORT);

	@Override
	public String presentationString(InvoiceItem obj) {
		if (obj == null) {
			return "null";
		}
		ResourceBundle resBundle = Resouce.INSTANCE.resBundle;
		StringBuilder text = new StringBuilder();
		text.append(resBundle.getString(CAPTION_INVOICEITEM_NAME));
		text.append(" [").append(resBundle.getString(CAPTION_INVOICEITEM_DATE))
				.append("=");
		text.append(dateFormat.format(obj.getStart()));
		text.append(", ").append(resBundle.getString(CAPTION_INVOICEITEM_START))
				.append("=");
		text.append(timeFormat.format(obj.getStart()));
		text.append(", ").append(resBundle.getString(CAPTION_INVOICEITEM_END))
				.append("=");
		text.append(timeFormat.format(obj.getEnd()));
		text.append(", ").append(resBundle.getString(CAPTION_ARTICLE))
				.append("=");
		text.append(obj.getArticle().getTitle());
		text.append("]");
		return text.toString();
	}

	@Override
	public Class<InvoiceItem> forClass() {
		return InvoiceItem.class;
	}

}
