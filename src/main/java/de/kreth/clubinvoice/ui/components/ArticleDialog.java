package de.kreth.clubinvoice.ui.components;

import java.math.BigDecimal;
import java.util.ResourceBundle;

import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ArticleDialog extends Window {

	private static final long serialVersionUID = 2516636187686436452L;

	private TextField title;
	private TextField pricePerHour;
	private TextField description;

	private Button okButton;
	private Button cancelButton;

	public ArticleDialog(ResourceBundle resBundle) {
		title = new TextField();
		title.setCaption(resBundle.getString("caption.article"));
		pricePerHour = new TextField();
		pricePerHour.setCaption(resBundle.getString("caption.article.price"));
		description = new TextField();
		description
				.setCaption(resBundle.getString("caption.article.description"));
		okButton = new Button(resBundle.getString("label.ok"));
		okButton.addClickListener(ev -> {
			close();
		});

		cancelButton = new Button(resBundle.getString("label.cancel"),
				ev -> close());

		HorizontalLayout contentValues = new HorizontalLayout();
		contentValues.addComponents(title, pricePerHour, description);
		HorizontalLayout contentButtons = new HorizontalLayout();
		contentButtons.addComponents(okButton, cancelButton);

		VerticalLayout content = new VerticalLayout();
		content.addComponents(contentValues, contentButtons);
		setContent(content);
		center();
		setModal(true);
	}

	public Registration addOkClickListener(ClickListener listener) {
		return okButton.addClickListener(listener);
	}

	public String getTitle() {
		return title.getValue();
	}

	public void setTitle(String title) {
		this.title.setValue(title);
	}

	public BigDecimal getPricePerHour() {
		return new BigDecimal(pricePerHour.getValue());
	}

	public void setPricePerHour(BigDecimal pricePerHour) {
		this.pricePerHour.setValue(pricePerHour.toPlainString());
	}

	public String getDescription() {
		return description.getValue();
	}

	public void setDescription(String description) {
		this.description.setValue(description);
	}

}
