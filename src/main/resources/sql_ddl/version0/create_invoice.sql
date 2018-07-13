CREATE TABLE `clubinvoice`.`INVOICE` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `invoicedate` DATETIME NOT NULL,
  `invoiceid` VARCHAR(150) NOT NULL,
  `user_id` INT NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_invoice_1_idx` (`user_id` ASC),
  CONSTRAINT `fk_invoice_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `clubinvoice`.`LOGIN_USER` (`login`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
