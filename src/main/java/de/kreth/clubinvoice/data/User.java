package de.kreth.clubinvoice.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LOGIN_USER")
public class User extends BaseEntity {

	private static final long serialVersionUID = 8756244776503934540L;

	@Column(name = "login")
	private String loginName;

	private String prename;

	private String surname;

	private String password;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private UserBank bank;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private UserAdress adress;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBank getBank() {
		return bank;
	}

	public void setBank(UserBank bank) {
		this.bank = bank;
		if (bank.getUser() == null) {
			bank.setUser(this);
		} else {
			if (bank.getUser().equals(this)) {
				throw new IllegalArgumentException(
						"Bank already set, but other than this.");
			}
		}
	}

	public UserAdress getAdress() {
		return adress;
	}

	public void setAdress(UserAdress adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", loginName=" + loginName + ", prename="
				+ prename + ", surname=" + surname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prename == null) ? 0 : prename.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (prename == null) {
			if (other.prename != null)
				return false;
		} else if (!prename.equals(other.prename))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

}
