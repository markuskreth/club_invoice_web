package de.kreth.clubinvoice.ui.presentation;

import java.util.ResourceBundle;

import de.kreth.clubinvoice.ui.Constants;

public interface DataPresentator<T> extends Constants {

	public enum Resouce {
		INSTANCE;

		public ResourceBundle resBundle;
		private Resouce() {
			resBundle = ResourceBundle.getBundle("/application");
		}
	}

	String presentationString(T obj);

	Class<T> forClass();
}
