package de.kreth.clubinvoice.business;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.Session;

public abstract class AbstractBusiness<T> implements Business<T> {

	protected final Session sessionObj;
	protected final PropertyStore propStore;
	private final Class<T> itemClass;

	public AbstractBusiness(Session sessionObj, PropertyStore propStore,
			Class<T> itemClass) {
		super();
		this.sessionObj = sessionObj;
		this.propStore = propStore;
		this.itemClass = itemClass;
	}

	@Override
	public boolean save(T obj) {
		sessionObj.beginTransaction();
		sessionObj.save(obj);
		sessionObj.getTransaction().commit();
		return true;
	}

	@Override
	public List<T> loadAll() {
		return sessionObj
				.createQuery("from " + itemClass.getSimpleName(), itemClass)
				.list();
	}

	public List<T> loadAll(Predicate<T> predicate) {
		return loadAll().stream().filter(predicate)
				.collect(Collectors.toList());
	}

	@Override
	public Session getSessionObj() {
		return sessionObj;
	}

	@Override
	public PropertyStore getStore() {
		return propStore;
	}
}