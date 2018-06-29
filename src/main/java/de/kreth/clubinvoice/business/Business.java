package de.kreth.clubinvoice.business;

public interface Business<T> {

	boolean save(T obj);
	PropertyStore getStore();

}