package de.kreth.clubinvoice.business;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kreth.clubinvoice.data.BaseEntity;

public abstract class AbstractBusiness<T extends BaseEntity> implements Business<T> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Class<T> itemClass;

	protected final Session sessionObj;
	protected final PropertyStore propStore;

	public AbstractBusiness(Session sessionObj, PropertyStore propStore,
			Class<T> itemClass) {
		super();
		this.sessionObj = sessionObj;
		this.propStore = propStore;
		this.itemClass = itemClass;
	}

	@Override
	public boolean save(T obj) {
		Transaction tx = sessionObj.beginTransaction();
		Serializable id = sessionObj.save(obj);
		obj.setId(Integer.parseInt(id.toString()));
		tx.commit();
		logger.debug("Stored {}", obj);
		return true;
	}

	@Override
	public boolean delete(T obj) {
		sessionObj.beginTransaction();
		sessionObj.delete(obj);
		sessionObj.getTransaction().commit();
		logger.info("Stored {}", obj);
		return true;
	}

	@Override
	public List<T> loadAll() {
		List<T> list = sessionObj
				.createQuery("from " + itemClass.getSimpleName(), itemClass)
				.list();
		logger.trace("Loaded {} of {}", list.size(), itemClass.getSimpleName());
		return list;
	}

	public List<T> loadAll(Predicate<T> predicate) {
		List<T> loadAll = loadAll();
		List<T> result = loadAll.stream().filter(predicate)
				.collect(Collectors.toList());
		logger.trace("Filtered {} of {} total {}", result.size(),
				loadAll.size(), itemClass.getSimpleName());
		return result;
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