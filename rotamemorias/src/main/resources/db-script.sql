SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `rotamemory` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `rotamemory`;

DROP TABLE IF EXISTS `rotamemory`.`cemiterio`;
CREATE TABLE IF NOT EXISTS `rotamemory`.`cemiterio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `foto` VARCHAR(150) NULL,
  `nome` VARCHAR(150) NOT NULL,
  `rua` VARCHAR(150) NOT NULL,
  `numero` VARCHAR(20) NOT NULL,
  `bairro` VARCHAR(150) NOT NULL,
  `cidade` VARCHAR(150) NOT NULL,
  `estado` VARCHAR(150) NOT NULL,
  `horarios_funcionamento` VARCHAR(250) NOT NULL,
  `telefone` VARCHAR(10) NOT NULL,
  `pagina_oficial` VARCHAR(150) NOT NULL,
  `localizacao_cemiterio` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `rotamemory`.`falecido`;
CREATE TABLE IF NOT EXISTS `rotamemory`.`falecido` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `foto` VARCHAR(255) NULL,
  `nome` VARCHAR(150) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `data_falecimento` DATE NOT NULL,
  `nome_mae` VARCHAR(150) NOT NULL,
  `nome_pai` VARCHAR(150) NOT NULL,
  `profissao` VARCHAR(150) NOT NULL,
  `localizacao_sepultura` VARCHAR(150) NOT NULL,
  `cemiterio_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cemiterio_idx` (`cemiterio_id` ASC) VISIBLE,
  CONSTRAINT `fk_cemiterio`
    FOREIGN KEY (`cemiterio_id`)
    REFERENCES `rotamemory`.`cemiterio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `rotamemory`.`mensagens`;
CREATE TABLE IF NOT EXISTS `rotamemory`.`mensagens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome_remetente` VARCHAR(150) NOT NULL,
  `mensagem` TEXT NOT NULL,
  `data_envio` DATE NOT NULL,
  `falecido_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_falecido_idx` (`falecido_id` ASC) VISIBLE,
  CONSTRAINT `fk_falecido`
    FOREIGN KEY (`falecido_id`)
    REFERENCES `rotamemory`.`falecido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
