package de.kreth.clubinvoice.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "invoice_item")
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceItem extends BaseEntity {

	private static final long serialVersionUID = 3142997452876778041L;

	private LocalDateTime start;

	private LocalDateTime end;

	@Column(nullable = true, length = 15)
	private String participants;

	@ManyToOne(optional = false)
	@JoinColumn(name = "article_id", nullable = false, updatable = false)
	private Article article;

	@ManyToOne(optional = true)
	@JoinColumn(name = "invoice_id", nullable = true, updatable = true)
	@EqualsAndHashCode.Exclude
	private Invoice invoice;

	@Column(name = "sum_price")
	private BigDecimal sumPrice;

	public void setStart(LocalDateTime start) {
		this.start = start;
		getSumPrice();
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
		getSumPrice();
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

		sumPrice = BigDecimal.valueOf(getDurationInMinutes()).setScale(2, RoundingMode.HALF_UP)
				.divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP).multiply(article.getPricePerHour())
				.setScale(2, RoundingMode.HALF_UP);
		return sumPrice;
	}

	public long getDurationInMinutes() {
		if (start == null || end == null) {
			return -1L;
		}
		return start.until(end, ChronoUnit.MINUTES);
	}

	public InvoiceItem() {
	}

	public InvoiceItem(InvoiceItem obj) {
		super(obj);
		this.article = obj.article;
		this.end = obj.end;
		this.invoice = obj.invoice;
		this.participants = obj.participants;
		this.start = obj.start;
		this.sumPrice = obj.sumPrice;
	}

}
