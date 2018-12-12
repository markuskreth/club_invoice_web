package de.kreth.clubinvoice.ui.presentation;

import java.util.ResourceBundle;

public interface DataPresentator<T> {

	public enum Resouce {
		INSTANCE;

		public final transient ResourceBundle resBundle;

		private Resouce() {
			resBundle = ResourceBundle.getBundle("application");
		}
	}

	String presentationString(T obj);

	Class<T> forClass();
}
