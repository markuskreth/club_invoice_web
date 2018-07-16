package de.kreth.clubinvoice.business;

import java.util.List;

import org.hibernate.Session;

public interface Business<T> {

	boolean save(T obj);
	boolean delete(T obj);
	List<T> loadAll();
	PropertyStore getStore();
	Session getSessionObj();

}