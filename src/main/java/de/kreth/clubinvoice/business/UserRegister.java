package de.kreth.clubinvoice.business;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import de.kreth.clubinvoice.data.User;

public class UserRegister extends AbstractBusiness<User> {

	private CookieStore cookies;
	private Query<User> query_login;

	public UserRegister(Session sessionObj, PropertyStore propStore,
			CookieStore cookies) {
		super(sessionObj, propStore, User.class);
		this.cookies = cookies;

		query_login = sessionObj.createQuery(
				"from User where login = :login and password = :password",
				User.class);
	}

	public boolean login(String login, String password) {

		query_login.setParameter("login", login);
		query_login.setParameter("password", password);

		List<User> result = query_login.list();

		if (result.isEmpty()) {
			return false;
		}
		propStore.setAttribute(PropertyStore.LOGGED_IN_USER, result.get(0));
		cookies.store(CookieStore.USER_NAME, login);
		cookies.store(CookieStore.PASSWORD, password);
		return true;
	}

	@Override
	public boolean save(User obj) {
		boolean saved = super.save(obj);
		propStore.setAttribute(PropertyStore.LOGGED_IN_USER, obj);
		cookies.store(CookieStore.USER_NAME, obj.getLoginName());
		cookies.store(CookieStore.PASSWORD, obj.getPassword());
		return saved;
	}

	public void logout() {
		propStore.removeAttribute(PropertyStore.LOGGED_IN_USER);
	}

	public boolean isLoggedIn() {

		Object user = propStore.getAttribute(PropertyStore.LOGGED_IN_USER);
		if (user == null) {
			String login = cookies.getValue(CookieStore.USER_NAME);
			String password = cookies.getValue(CookieStore.PASSWORD);
			if (login != null && password != null) {
				return login(login, password);
			}
		}

		return user != null;
	}

	public CookieStore getCookieStore() {
		return cookies;
	}
}
