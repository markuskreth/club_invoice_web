CREATE TABLE login_user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(45) NOT NULL,
  prename VARCHAR(45) NOT NULL,
  surname VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE INDEX id_UNIQUE (id ASC),
  UNIQUE INDEX login_UNIQUE (login ASC));
CREATE TABLE article (
  id INT NOT NULL AUTO_INCREMENT,
  price DOUBLE NOT NULL,
  title VARCHAR(50) NOT NULL,
  description VARCHAR(255) NULL DEFAULT NULL,
  user_id INT NOT NULL,
  UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id));
  CREATE TABLE ADRESS (
  id INT NOT NULL AUTO_INCREMENT,
  ADRESS_TYPE VARCHAR(31) NOT NULL,
  ADRESS1 VARCHAR(255) NULL,
  ADRESS2 VARCHAR(255) NULL,
  ZIP VARCHAR(45) NULL,
  CITY VARCHAR(155) NULL,
  UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id));
  CREATE TABLE BANKING_CONNECTION(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    OWNER_TYPE VARCHAR(31) NOT NULL,
    BANKNAME VARCHAR(255),
    BIC VARCHAR(255),
    IBAN VARCHAR(255),
  UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	UNIQUE INDEX banking_connection_UNIQUE (ID, OWNER_TYPE, IBAN ASC)
);
CREATE TABLE invoice_item (
  id INT NOT NULL AUTO_INCREMENT,
  start DATETIME NOT NULL,
  end VARCHAR(45) NOT NULL,
  article_id INT NOT NULL,
  participants VARCHAR(15) NULL DEFAULT NULL,
  sum_price DECIMAL(7,2) NOT NULL,
  rechnung_id INT NULL,
  invoice_id INT NULL DEFAULT NULL,
  UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX fk_invoice_item_1_idx (article_id ASC),
  CONSTRAINT fk_invoice_item_1
    FOREIGN KEY (article_id)
    REFERENCES ARTICLE (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
CREATE TABLE invoice (
  id INT NOT NULL AUTO_INCREMENT,
  invoicedate DATETIME NOT NULL,
  invoiceid VARCHAR(150) NOT NULL,
  user_id INT NOT NULL,
  UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX fk_invoice_1_idx (user_id ASC),
  CONSTRAINT fk_invoice_1
    FOREIGN KEY (user_id)
    REFERENCES LOGIN_USER (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
    