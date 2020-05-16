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
@Table(name = "adress")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "adress_type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode
@Data
public class Adress implements Serializable {

	private static final long serialVersionUID = 8331249424121577387L;

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
		this.id = toClone.id;
		this.createdDate = toClone.createdDate;
		this.changeDate = toClone.changeDate;
		this.adress1 = toClone.adress1;
		this.adress2 = toClone.adress2;
		this.zip = toClone.zip;
		this.city = toClone.city;
	}

}
