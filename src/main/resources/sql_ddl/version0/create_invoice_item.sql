CREATE TABLE `clubinvoice`.`invoice_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start` DATETIME NOT NULL,
  `end` VARCHAR(45) NOT NULL,
  `article_id` INT NOT NULL,
  `sum_price` DECIMAL(7,2) NOT NULL,
  `rechnung_id` INT NULL,
  `invoice_id` INT NULL DEFAULT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_invoice_item_1_idx` (`article_id` ASC),
  CONSTRAINT `fk_invoice_item_1`
    FOREIGN KEY (`article_id`)
    REFERENCES `clubinvoice`.`article` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

