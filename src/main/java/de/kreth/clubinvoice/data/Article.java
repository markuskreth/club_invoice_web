package de.kreth.clubinvoice.data;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ARTICLE")
@EqualsAndHashCode(callSuper = true)
@Data
public class Article extends BaseEntity {

	public Article() {
	}

	public Article(Article toClone) {
		super(toClone);
		this.description = toClone.description;
		this.title = toClone.title;
		this.userId = toClone.userId;
		this.pricePerHour = toClone.pricePerHour;
		this.report = toClone.report;
	}

	private static final long serialVersionUID = 6777704420363536698L;

	@Column(name = "price")
	private BigDecimal pricePerHour;

	@Column(nullable = false, length = 50)
	private String title;

	@Column(nullable = true, length = 255)
	private String description;

	@Column(name = "user_id")
	private int userId;

	@Column
	private String report;

}
