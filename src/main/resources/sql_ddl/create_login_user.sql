CREATE TABLE `clubinvoice`.`login_user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `prename` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC));
