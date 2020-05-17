package de.kreth.clubinvoice.data;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "banking_connection")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "owner_type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode
@Data
public class BankingConnection implements Serializable, InvoiceEntity {

	private static final long serialVersionUID = -6168631092559375156L;

	@Id
	private int id;

	@Column(name = "created")
	private LocalDateTime createdDate;

	@Column(name = "updated", nullable = false)
	private LocalDateTime changeDate;

	@PrePersist
	protected void onCreate() {
		changeDate = createdDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		changeDate = LocalDateTime.now();
	}

	@Column(nullable = false, length = 150)
	private String bankName;

	@Column(nullable = false, length = 150)
	private String iban;

	@Column(nullable = true, length = 150)
	private String bic;

	public BankingConnection() {
	}

	protected BankingConnection(BankingConnection toClone) {
		this.id = toClone.id;
		this.createdDate = toClone.createdDate;
		this.changeDate = toClone.changeDate;
		this.bankName = toClone.bankName;
		this.iban = toClone.iban;
		this.bic = toClone.bic;

	}

}
