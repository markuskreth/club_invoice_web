package de.kreth.clubinvoice.data;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.kreth.clubinvoice.utils.PathConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "invoice")
@Data
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity {

	private static final long serialVersionUID = 736651954892271409L;

	@Column(name = "invoiceid", nullable = false, length = 150)
	private String invoiceId;

	private LocalDateTime invoiceDate;

	@Convert(converter = PathConverter.class)
	private Path signImagePath;

	@OneToMany(mappedBy = "invoice")
	private List<InvoiceItem> items;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public BigDecimal getSum() {
		if (items == null || items.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return items.stream().map(i -> i.getSumPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setItems(Collection<InvoiceItem> selectedItems) {
		if (items == null) {
			items = new ArrayList<InvoiceItem>(selectedItems);
		}
		else {
			items.clear();
			items.addAll(selectedItems);
		}
	}

	public Invoice() {
	}

	public Invoice(Invoice toClone) {
		super(toClone);
		this.invoiceId = toClone.invoiceId;
		this.invoiceDate = toClone.invoiceDate;
		this.user = toClone.user;
		this.items = new ArrayList<InvoiceItem>(toClone.items);
	}

}
