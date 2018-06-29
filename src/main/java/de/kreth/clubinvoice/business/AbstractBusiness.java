package de.kreth.clubinvoice.business;

import org.hibernate.Session;

public abstract class AbstractBusiness<T> implements Business<T> {

	protected final Session sessionObj;
	protected final PropertyStore propStore;

	public AbstractBusiness(Session sessionObj, PropertyStore propStore) {
		super();
		this.sessionObj = sessionObj;
		this.propStore = propStore;
	}

	@Override
	public boolean save(T obj) {
		sessionObj.beginTransaction();
		sessionObj.save(obj);
		sessionObj.getTransaction().commit();
		return true;
	}

	@Override
	public PropertyStore getStore() {
		return propStore;
	}
}