package de.kreth.clubinvoice.business;

import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinService;

public class CookieStore {

	public static final String USER_NAME = "LoggedInUser";
	public static final String PASSWORD = "LoggedInUserPassword";

	private final Encryptor enc = new Encryptor();

	public synchronized String getValue(String key) {

		Cookie c = findCookie(key);
		if (c != null) {
			if (key.equals(PASSWORD)) {
				try {
					return decrypt(c.getValue());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				return c.getValue();
			}
		}
		return null;
	}

	private Cookie findCookie(String key) {

		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		for (Cookie c : cookies) {
			if (key.equals(c.getName())) {
				return c;
			}
		}
		return null;

	}

	public synchronized void store(String key, String value) {
		if (key.equals(PASSWORD)) {
			try {
				value = encrypt(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		Cookie cookie = new Cookie(key, value);
		cookie.setComment(key);
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
		VaadinService.getCurrentResponse().addCookie(cookie);
	}

	public synchronized void remove(String key) {
		Cookie c = findCookie(key);
		if (c != null) {
			c.setValue("");
			c.setMaxAge(0);
			VaadinService.getCurrentResponse().addCookie(c);
		}
	}

	String encrypt(String strClearText) throws Exception {
		return enc.encrypt(strClearText);
	}

	String decrypt(String strEncrypted) throws Exception {
		return enc.decrypt(strEncrypted);
	}
}
