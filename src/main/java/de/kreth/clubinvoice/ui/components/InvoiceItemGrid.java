package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.ui.Constants.CAPTION_ARTICLE;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICEITEMS;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICEITEM_DATE;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICEITEM_END;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICEITEM_PARTICIPANTS;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICEITEM_START;
import static de.kreth.clubinvoice.ui.Constants.CAPTION_INVOICEITEM_SUMPRICE;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceItemGrid<T extends InvoiceItem> extends Grid<T> {

	private static final long serialVersionUID = -8653320112619816426L;

	public InvoiceItemGrid(ResourceBundle resBundle) {
		setCaption(resBundle.getString(CAPTION_INVOICEITEMS));

		addColumn(InvoiceItem::getArticle, Article::getTitle)
				.setCaption(resBundle.getString(CAPTION_ARTICLE));

		Column<T, LocalDateTime> dateColumn = addColumn(InvoiceItem::getStart)
				.setCaption(resBundle.getString(CAPTION_INVOICEITEM_DATE))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		Column<T, LocalDateTime> startColumn = addColumn(InvoiceItem::getStart)
				.setCaption(resBundle.getString(CAPTION_INVOICEITEM_START))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
		addColumn(InvoiceItem::getEnd)
				.setCaption(resBundle.getString(CAPTION_INVOICEITEM_END))
				.setRenderer(new LocalDateTimeRenderer(
						DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
		addColumn(InvoiceItem::getParticipants).setCaption(
				resBundle.getString(CAPTION_INVOICEITEM_PARTICIPANTS));

		addColumn(InvoiceItem::getSumPrice)
				.setCaption(resBundle.getString(CAPTION_INVOICEITEM_SUMPRICE))
				.setRenderer(
						new NumberRenderer(NumberFormat.getCurrencyInstance()));

		setSortOrder(GridSortOrder.asc(dateColumn).thenAsc(startColumn));
	}
}
