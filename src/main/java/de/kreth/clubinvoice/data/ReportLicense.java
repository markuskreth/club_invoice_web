package de.kreth.clubinvoice.data;

import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLE_TYPE_ASSISTANT;
import static de.kreth.clubinvoice.Localization_Properties.CAPTION_ARTICLE_TYPE_TRAINER;

import java.util.ResourceBundle;

import de.kreth.clubinvoice.utils.ResourceBundleProvider;

public enum ReportLicense {

	TRAINER("/reports/mtv_gross_buchholz_trainer.jrxml"),
	ASSISTANT("/reports/mtv_gross_buchholz.jrxml");

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
			return CAPTION_ARTICLE_TYPE_TRAINER.getString(resBundle::getString);
		}
		else {
			return CAPTION_ARTICLE_TYPE_ASSISTANT.getString(resBundle::getString);
		}
	}
}
