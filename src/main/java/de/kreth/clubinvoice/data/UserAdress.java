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
public class UserAdress extends Adress {

	private static final long serialVersionUID = -8104370538500175340L;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private User user;

	public UserAdress() {
	}

	public UserAdress(UserAdress adress) {
		super(adress);
		setUser(adress.user);
	}

	public void setUser(User user) {
		this.user = user;
		if (user != null) {
			this.setId(user.getId());
		}
	}
}
