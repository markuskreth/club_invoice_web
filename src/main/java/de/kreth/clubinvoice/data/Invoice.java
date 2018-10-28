package de.kreth.clubinvoice.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "INVOICE")
public class Invoice extends BaseEntity {

	private static final long serialVersionUID = 736651954892271409L;

	@Column(name = "invoiceid", nullable = false, length = 150)
	private String invoiceId;
	private LocalDateTime invoiceDate;

	@OneToMany(mappedBy = "invoice")
	private List<InvoiceItem> items;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public LocalDateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(Collection<InvoiceItem> items) {
		this.items = new ArrayList<>(items);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getSum() {
		if (items == null || items.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return items.stream().map(i -> i.getSumPrice()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}

	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", itemscount="
				+ items.size() + ", sum=" + getSum() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((invoiceId == null) ? 0 : invoiceId.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceId == null) {
			if (other.invoiceId != null)
				return false;
		} else if (!invoiceId.equals(other.invoiceId))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
