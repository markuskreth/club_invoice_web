package de.kreth.clubinvoice.data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBank extends BankingConnection {

	private static final long serialVersionUID = -7356424394007978241L;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private User user;

	public void setUser(User user) {
		this.user = user;
		if (user.getBank() == null) {
			user.setBank(this);
		}
		else {
			if (user.getBank().equals(this) == false) {
				throw new IllegalArgumentException("User already set, but other than this.");
			}
		}
	}

	public UserBank() {
	}

	public UserBank(UserBank bank) {
		super(bank);
		this.user = bank.user;
	}

}
