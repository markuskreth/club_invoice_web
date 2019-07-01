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
@Table(name = "banking_connection")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "owner_type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode(callSuper = true)
@Data
public class BankingConnection extends BaseEntity {

	private static final long serialVersionUID = -6168631092559375156L;

	@Column(nullable = false, length = 150)
	private String bankName;

	@Column(nullable = false, length = 150)
	private String iban;

	@Column(nullable = true, length = 150)
	private String bic;

	public BankingConnection() {
	}

	protected BankingConnection(BankingConnection toClone) {
		super(toClone);
		this.bankName = toClone.bankName;
		this.iban = toClone.iban;
		this.bic = toClone.bic;

	}

}
