package de.kreth.clubinvoice.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ADRESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "adress_type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode(callSuper = true)
@Data
public class Adress extends BaseEntity {

	private static final long serialVersionUID = 8331249424121577387L;

	@Column(nullable = false, length = 255)
	private String adress1;

	@Column(nullable = true, length = 255)
	private String adress2;

	@Column(nullable = true, length = 45)
	private String zip;

	@Column(nullable = true, length = 155)
	private String city;

	public Adress() {
	}

	protected Adress(Adress toClone) {
		super(toClone);
		this.adress1 = toClone.adress1;
		this.adress2 = toClone.adress2;
		this.zip = toClone.zip;
		this.city = toClone.city;
	}

}
