package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEMS;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_DATE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_END;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_PARTICIPANTS;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_START;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_SUMPRICE;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceItemGrid<T extends InvoiceItem> extends Grid<T> {

	private static final long serialVersionUID = -8653320112619816426L;

	private final ResourceBundle resBundle;

	public InvoiceItemGrid(ResourceBundle resBundle) {
		this.resBundle = resBundle;
		setCaption(getString(CAPTION_INVOICEITEMS));

		addColumn(InvoiceItem::getArticle, Article::getTitle)
				.setCaption(getString(CAPTION_ARTICLE));

		Column<T, LocalDateTime> dateColumn = addColumn(InvoiceItem::getStart)
				.setCaption(getString(CAPTION_INVOICEITEM_DATE))
				.setRenderer(new LocalDateTimeRenderer("EEE, dd.MM.yyyy"));
		Column<T, LocalDateTime> startColumn = addColumn(InvoiceItem::getStart)
				.setCaption(getString(CAPTION_INVOICEITEM_START))
				.setRenderer(new LocalDateTimeRenderer("HH:mm"));
		addColumn(InvoiceItem::getEnd).setCaption(getString(CAPTION_INVOICEITEM_END))
				.setRenderer(new LocalDateTimeRenderer("HH:mm"));
		addColumn(InvoiceItem::getParticipants)
				.setCaption(getString(CAPTION_INVOICEITEM_PARTICIPANTS));

		addColumn(InvoiceItem::getSumPrice).setCaption(getString(CAPTION_INVOICEITEM_SUMPRICE))
				.setRenderer(new NumberRenderer(NumberFormat.getCurrencyInstance()));

		setSortOrder(GridSortOrder.asc(dateColumn).thenAsc(startColumn));
	}

	private String getString(Application_Properties property) {
		return property.getString(resBundle::getString);
	}
}
