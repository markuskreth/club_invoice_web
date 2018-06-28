package de.kreth.clubinvoice.business;

public interface Business<T> {
	void save(T obj);
}