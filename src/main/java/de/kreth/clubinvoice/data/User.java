package de.kreth.clubinvoice.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "login_user")
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

	private static final long serialVersionUID = 8756244776503934540L;

	@Column(name = "login", nullable = true, length = 45)
	private String loginName;

	@Column(nullable = true, length = 45)
	private String prename;

	@Column(nullable = true, length = 45)
	private String surname;

	@Column(nullable = true, length = 45)
	@ToString.Exclude
	private String password;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private UserBank bank;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private UserAdress adress;

	public void setBank(UserBank bank) {
		this.bank = bank;
		if (bank == null) {
			return;
		}
		else if (bank.getUser() == null) {
			bank.setUser(this);
		}
		else {
			if (!bank.getUser().equals(this)) {
				throw new IllegalArgumentException("Bank already set, but other than this.");
			}
		}
	}

	public User() {
	}

	public User(User toClone) {
		super(toClone);
		this.adress = toClone.adress;
		this.bank = toClone.bank;
		this.loginName = toClone.loginName;
		this.prename = toClone.prename;
		this.surname = toClone.surname;
		this.password = toClone.password;
	}
}
