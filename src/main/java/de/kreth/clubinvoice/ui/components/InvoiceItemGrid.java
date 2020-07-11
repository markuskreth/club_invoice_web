package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEMS;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_DATE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_END;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_PARTICIPANTS;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_START;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_SUMPRICE;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.ResourceBundle;

import com.vaadin.data.provider.DataChangeEvent;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.GridSelectionModel;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.data.InvoiceItem;

public class InvoiceItemGrid<T extends InvoiceItem> extends Grid<T> {

	private static final long serialVersionUID = -8653320112619816426L;

	private final DateTimeFormatter ofLocalizedDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

	private final ResourceBundle resBundle;

	private FooterCell priceSumCell;

	private FooterCell countCell;

	private FooterCell dateSpan;

	public InvoiceItemGrid(ResourceBundle resBundle) {
		this.resBundle = resBundle;
		setCaption(getString(CAPTION_INVOICEITEMS));

		addColumn(InvoiceItem::getTitle)
				.setId("Article")
				.setCaption(getString(CAPTION_ARTICLE));

		Column<T, LocalDateTime> dateColumn = addColumn(InvoiceItem::getStart)
				.setId("Date")
				.setCaption(getString(CAPTION_INVOICEITEM_DATE))
				.setRenderer(new LocalDateTimeRenderer("EEE, dd.MM.yyyy"));
		Column<T, LocalDateTime> startColumn = addColumn(InvoiceItem::getStart)
				.setCaption(getString(CAPTION_INVOICEITEM_START))
				.setRenderer(new LocalDateTimeRenderer("HH:mm"));
		Column<T, LocalDateTime> endColumn = addColumn(InvoiceItem::getEnd)
				.setCaption(getString(CAPTION_INVOICEITEM_END))
				.setRenderer(new LocalDateTimeRenderer("HH:mm"));
		addColumn(InvoiceItem::getParticipants)
				.setCaption(getString(CAPTION_INVOICEITEM_PARTICIPANTS));

		addColumn(InvoiceItem::getSumPrice)
				.setId("price")
				.setCaption(getString(CAPTION_INVOICEITEM_SUMPRICE))
				.setRenderer(new NumberRenderer(NumberFormat.getCurrencyInstance()));

		setSortOrder(GridSortOrder.asc(dateColumn).thenAsc(startColumn));
		FooterRow footer = appendFooterRow();

		priceSumCell = footer.getCell("price");
		dateSpan = footer.join(dateColumn, startColumn, endColumn);
		countCell = footer.getCell("Article");

		addSelectionListener(this::selectionChanged);

		getDataProvider().addDataProviderListener(new InnerDataProviderListener());
	}

	@Override
	public GridSelectionModel<T> setSelectionMode(SelectionMode selectionMode) {
		GridSelectionModel<T> setSelectionMode = super.setSelectionMode(selectionMode);
		setSelectionMode.addSelectionListener(this::selectionChanged);
		return setSelectionMode;
	}

	@SuppressWarnings("unchecked")
	private void selectionChanged(SelectionEvent<T> event) {
		if (event.getAllSelectedItems().isEmpty()) {
			updateFooterWith(((ListDataProvider<T>) getDataProvider()).getItems());
		}
		else {
			updateFooterWith(event.getAllSelectedItems());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void internalSetDataProvider(DataProvider<T, ?> dataProvider) {

		if (!(dataProvider instanceof ListDataProvider)) {
			throw new IllegalArgumentException("dataProvider must be an instance of ListDataProvider");
		}
		super.internalSetDataProvider(dataProvider);
		dataProvider.addDataProviderListener(new InnerDataProviderListener());
		updateFooterWith(((ListDataProvider<T>) getDataProvider()).getItems());
	}

	private void updateFooterWith(Collection<T> selected) {
		BigDecimal priceSum = BigDecimal.ZERO;
		LocalDate min = null;
		LocalDate max = null;

		for (T t : selected) {
			priceSum = priceSum.add(t.getSumPrice());
			min = getMin(min, t.getStart().toLocalDate());
			max = getMax(max, t.getEnd().toLocalDate());
		}

		priceSumCell.setText(NumberFormat.getCurrencyInstance().format(priceSum));
		if (min != null && max != null) {
			dateSpan.setText(min.format(ofLocalizedDateFormatter) + " - " + max.format(ofLocalizedDateFormatter));
		}
		else {
			dateSpan.setText("");
		}
		countCell.setText("Anzahl: " + selected.size());
	}

	private LocalDate getMax(LocalDate max, LocalDate localDate) {
		if (max == null) {
			max = localDate;
		}
		else {
			if (max.isBefore(localDate)) {
				max = localDate;
			}
		}
		return max;
	}

	private LocalDate getMin(LocalDate min, LocalDate localDate) {
		if (min == null) {
			min = localDate;
		}
		else {
			if (min.isAfter(localDate)) {
				min = localDate;
			}
		}
		return min;
	}

	private String getString(Application_Properties property) {
		return property.getString(resBundle::getString);
	}

	private class InnerDataProviderListener implements DataProviderListener<T> {

		private static final long serialVersionUID = -6094992880488082586L;

		@Override
		public void onDataChange(DataChangeEvent<T> event) {
			if (event.getSource() == getDataProvider()) {
				@SuppressWarnings("unchecked")
				ListDataProvider<T> provider = (ListDataProvider<T>) getDataProvider();
				updateFooterWith(provider.getItems());
			}
		}

	}
}
