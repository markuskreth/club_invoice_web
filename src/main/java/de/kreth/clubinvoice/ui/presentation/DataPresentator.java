package de.kreth.clubinvoice.ui.presentation;

public interface DataPresentator<T> {

	String presentationString(T obj);

	Class<T> forClass();
}
