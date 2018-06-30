package de.kreth.clubinvoice.testutils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.kreth.clubinvoice.business.PropertyStore;

public class MockPropertyStore extends PropertyStore {

	private final Map<String, Object> values;

	public MockPropertyStore() {
		super(null);
		values = new HashMap<>();
	}

	@Override
	public Object getAttribute(String name) {
		return values.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		values.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		values.remove(name);
	}

	@Override
	public Collection<String> getAttributeNames() {
		return values.keySet();
	}

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(values);
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + "] " + values.toString();
	}
}
