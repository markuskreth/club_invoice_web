package de.kreth.clubinvoice.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private LocalDateTime start;

	@Column
	private LocalDateTime end;

	@ManyToOne(optional = false)
	@JoinColumn(name = "article_id", nullable = false, updatable = false)
	private Article article;

	@Column(name = "created")
	private LocalDateTime createdDate;

	@Column(name = "updated")
	private LocalDateTime changeDate;

	@ManyToOne(optional = true)
	@JoinColumn(name = "invoice_id", nullable = true, updatable = true)
	private Invoice invoice;

	@Column(name = "sum_price")
	private BigDecimal sumPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
		getSumPrice();
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
		getSumPrice();
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
		getSumPrice();
	}

	public BigDecimal getSumPrice() {
		if (article == null || start == null || end == null) {
			sumPrice = null;
			return null;
		}

		sumPrice = BigDecimal.valueOf(getDurationInMinutes())
				.setScale(2, RoundingMode.HALF_UP)
				.divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP)
				.multiply(article.getPricePerHour())
				.setScale(2, RoundingMode.HALF_UP);
		return sumPrice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(LocalDateTime changeDate) {
		this.changeDate = changeDate;
	}

	public long getDurationInMinutes() {
		if (start == null || end == null) {
			return -1L;
		}
		return start.until(end, ChronoUnit.MINUTES);
	}

	@Override
	public String toString() {
		return "InvoiceItem [id=" + id + ", start=" + start + ", end=" + end
				+ ", article=" + article + ", createdDate=" + createdDate
				+ ", changeDate=" + changeDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + id;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceItem other = (InvoiceItem) obj;
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (id != other.id)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}
