CREATE TABLE `clubinvoice`.`invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `invoicedate` DATETIME NOT NULL,
  `invoiceid` VARCHAR(150) NOT NULL,
  `user_id` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_invoice_1_idx` (`user_id` ASC),
  CONSTRAINT `fk_invoice_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `clubinvoice`.`login_user` (`login`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
