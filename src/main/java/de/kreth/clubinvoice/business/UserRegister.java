package de.kreth.clubinvoice.business;

import org.hibernate.Session;

import de.kreth.clubinvoice.data.User;

public class UserRegister implements Business<User> {

	private final Session sessionObj;

	public UserRegister(Session sessionObj) {
		this.sessionObj = sessionObj;
	}

	public void save(User user) {

		sessionObj.beginTransaction();
		sessionObj.save(user);
		sessionObj.getTransaction().commit();
	}
}
