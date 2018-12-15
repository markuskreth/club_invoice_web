package de.kreth.clubinvoice.data;

import java.util.ResourceBundle;

import de.kreth.clubinvoice.ui.Constants;
import de.kreth.clubinvoice.utils.ResourceBundleProvider;

public enum ReportLicense {

	TRAINER("/reports/mtv_gross_buchholz_trainer.jrxml"), ASSISTANT("/reports/mtv_gross_buchholz.jrxml");

	private static final ResourceBundle resBundle = ResourceBundleProvider.getBundle();
	private String ressource;

	private ReportLicense(String ressource) {
		this.ressource = ressource;
	}

	public String getRessource() {
		return ressource;
	}

	public String getLabel() {
		if (TRAINER == this) {
			return resBundle.getString(Constants.CAPTION_ARTICLE_TYPE_TRAINER);
		} else {
			return resBundle.getString(Constants.CAPTION_ARTICLE_TYPE_ASSISTANT);
		}
	}
}
