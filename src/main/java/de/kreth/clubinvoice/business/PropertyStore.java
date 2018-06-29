package de.kreth.clubinvoice.business;

import java.util.Collection;

import com.vaadin.server.WrappedSession;

public class PropertyStore {

	private final WrappedSession store;

	public static final String LOGGED_IN_USER = "LoggedInUser";

	public PropertyStore(WrappedSession wrappedSession) {
		this.store = wrappedSession;
	}

	public Object getAttribute(String name) {
		return store.getAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		store.setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		store.removeAttribute(name);
	}

	public Collection<String> getAttributeNames() {
		return store.getAttributeNames();
	}

}
