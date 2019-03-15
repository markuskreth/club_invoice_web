package de.kreth.clubinvoice.ui.components;

import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLES;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE_DESCRIPTION;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE_PRICE;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE_REPORT;
import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLE_TITLE;
import static de.kreth.clubinvoice.Application_Properties.MESSAGE_ARTICLE_PRICEERROR;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.kreth.clubinvoice.Application_Properties;
import de.kreth.clubinvoice.business.ArticleBusiness;
import de.kreth.clubinvoice.data.Article;
import de.kreth.clubinvoice.data.ReportLicense;
import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.ui.presentation.PriceConverter;

public class ArticleDialog extends Window {

	private static final long serialVersionUID = 2516636187686436452L;

	private TextField title;

	private TextField pricePerHour;

	private TextField description;

	private Grid<Article> articleGrid;

	private ArticleBusiness business;

	private User user;

	private Article current = null;

	private Binder<Article> binder;

	private Button discartButton;

	private Button storeButton;

	private CheckBox isTrainer;

	public ArticleDialog(ResourceBundle resBundle) {
		title = new TextField();
		title.setCaption(CAPTION_ARTICLE.getString(resBundle::getString));
		pricePerHour = new TextField();
		pricePerHour.setCaption(CAPTION_ARTICLE_PRICE.getString(resBundle::getString));

		description = new TextField();
		description.setCaption(CAPTION_ARTICLE_DESCRIPTION.getString(resBundle::getString));

		isTrainer = new CheckBox(CAPTION_ARTICLE_REPORT.getString(resBundle::getString), false);

		HorizontalLayout contentValues = new HorizontalLayout();
		contentValues.addComponents(title, pricePerHour, description, isTrainer);

		Button addArticle = new Button(Application_Properties.LABEL_ADDARTICLE.getString(resBundle::getString));

		addArticle.addClickListener(ev -> {
			current = new Article();
			current.setTitle("");
			current.setDescription("");
			current.setUserId(user.getId());
			current.setPricePerHour(BigDecimal.ZERO);
			current.setReport(ReportLicense.ASSISTANT.getRessource());
			binder.setBean(current);
		});

		discartButton = new Button(resBundle.getString(Application_Properties.LABEL_DISCART.getValue()), e -> {
			binder.readBean(current);
			discartButton.setVisible(false);
			storeButton.setVisible(false);
		});
		discartButton.setVisible(false);

		storeButton = new Button(resBundle.getString(Application_Properties.LABEL_STORE.getValue()), e -> {
			if (binder.validate().isOk()) {
				business.save(current);
				reloadItems();
				discartButton.setVisible(false);
				storeButton.setVisible(false);
			}
		});
		storeButton.setVisible(false);

		Button closeButton = new Button(resBundle.getString(Application_Properties.LABEL_CLOSE.getValue()),
				ev -> close());

		Button deleteButton = new Button(resBundle.getString(Application_Properties.LABEL_DELETE.getValue()), ev -> {
			business.delete(current);
			reloadItems();
		});

		HorizontalLayout contentButtons = new HorizontalLayout();
		contentButtons.addComponents(storeButton, discartButton, addArticle, deleteButton, closeButton);

		setupArticleGrid(resBundle);

		setupBinder(resBundle);

		VerticalLayout content = new VerticalLayout();
		content.addComponents(contentValues, articleGrid, contentButtons);
		setContent(content);
		center();
		setWidth(75.0f, Unit.PERCENTAGE);
		setModal(true);
	}

	private List<Article> reloadItems() {
		List<Article> loadAll = business.loadAll(a -> a.getUserId() == user.getId());
		articleGrid.setItems(loadAll);
		return loadAll;
	}

	private void setupBinder(ResourceBundle resBundle) {
		binder = new Binder<>(Article.class);
		binder.forField(title).asRequired().withNullRepresentation("").bind(Article::getTitle, Article::setTitle);
		PriceConverter converter = new PriceConverter(BigDecimal.ZERO,
				MESSAGE_ARTICLE_PRICEERROR.getString(resBundle::getString));

		binder.forField(pricePerHour).asRequired().withNullRepresentation("").withConverter(converter)
				.bind(Article::getPricePerHour, Article::setPricePerHour);

		binder.forField(description).withNullRepresentation("").bind(Article::getDescription, Article::setDescription);

		binder.forField(isTrainer).bind(this::reportToCheckbox, this::checkboxToReportLicense);

		binder.addValueChangeListener(changeEv -> {

			if (changeEv.isUserOriginated()) {
				discartButton.setVisible(true);
				if (binder.validate().isOk()) {
					storeButton.setVisible(true);
				}
			}
			else {
				storeButton.setVisible(false);
				discartButton.setVisible(false);
			}
		});
	}

	private boolean reportToCheckbox(Article source) {
		if (source == null || source.getReport() == null) {
			return false;
		}
		return source.getReport().contentEquals(ReportLicense.TRAINER.getRessource());
	}

	private void checkboxToReportLicense(Article bean, Boolean fieldvalue) {
		if (bean != null && fieldvalue != null) {
			if (fieldvalue) {
				bean.setReport(ReportLicense.TRAINER.getRessource());
			}
			else {
				bean.setReport(ReportLicense.ASSISTANT.getRessource());
			}
		}
	}

	private void setupArticleGrid(ResourceBundle resBundle) {
		articleGrid = new Grid<>();
		articleGrid.setCaption(CAPTION_ARTICLES.getString(resBundle::getString));
		articleGrid.setSizeFull();

		articleGrid.addColumn(Article::getTitle).setCaption(CAPTION_ARTICLE_TITLE.getString(resBundle::getString));

		ValueProvider<BigDecimal, String> currencyProvider = new ValueProvider<BigDecimal, String>() {

			private static final long serialVersionUID = -6305095230785149948L;

			private final NumberFormat formatter = NumberFormat.getCurrencyInstance();

			@Override
			public String apply(BigDecimal source) {
				return formatter.format(source.doubleValue());
			}
		};
		articleGrid.addColumn(Article::getPricePerHour, currencyProvider)
				.setCaption(CAPTION_ARTICLE_PRICE.getString(resBundle::getString));
		articleGrid.addColumn(Article::getDescription)
				.setCaption(CAPTION_ARTICLE_DESCRIPTION.getString(resBundle::getString));
		articleGrid.addColumn(this::reportToCheckbox)
				.setCaption(CAPTION_ARTICLE_REPORT.getString(resBundle::getString));

		articleGrid.addSelectionListener(sel -> {
			if (!binder.hasChanges()) {

				Optional<Article> selected = sel.getFirstSelectedItem();
				if (selected.isPresent()) {
					current = selected.get();
					binder.setBean(current);
				}
			}
		});
	}

	public void setBusiness(ArticleBusiness articleBusiness) {
		this.business = articleBusiness;

		List<Article> loadAll = reloadItems();

		if (loadAll.isEmpty()) {
			current = new Article();
			if (user != null) {
				current.setUserId(user.getId());
			}
		}
		else {
			current = loadAll.get(0);
			articleGrid.select(current);
		}

		binder.setBean(current);
	}

	public void setUser(User user) {
		this.user = user;
	}
}
