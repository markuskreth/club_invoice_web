package de.kreth.clubinvoice.data;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 6953593432069408729L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public BaseEntity() {
	}

	protected BaseEntity(BaseEntity toClone) {
		this.id = toClone.id;
		this.createdDate = toClone.createdDate;
		this.changeDate = toClone.changeDate;
	}

}
