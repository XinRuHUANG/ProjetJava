-- 1) Le dictionnaire des types de déchets
CREATE TABLE `TypeDechet` (
  `typeDechet` VARCHAR(20) NOT NULL,
  `poids` DOUBLE,
  PRIMARY KEY (`typeDechet`)
) ENGINE=InnoDB;

-- 2) Les entités principales
CREATE TABLE `CategorieDeProduits` (
  `identifiantCategorieDeProduits` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`identifiantCategorieDeProduits`)
) ENGINE=InnoDB;

CREATE TABLE `CentreDeTri` (
  `identifiantCentreDeTri` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL,
  `adresse` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`identifiantCentreDeTri`)
) ENGINE=InnoDB;

CREATE TABLE `Commerce` (
  `identifiantCommerce` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`identifiantCommerce`)
) ENGINE=InnoDB;

CREATE TABLE `Promotion` (
  `identifiantPromotion` INT NOT NULL AUTO_INCREMENT,
  `pourcentageRemise` FLOAT,
  `pointsRequis` FLOAT,
  PRIMARY KEY (`identifiantPromotion`)
) ENGINE=InnoDB;

CREATE TABLE `Utilisateur` (
  `identifiantUtilisateur` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  `pointsFidelite` FLOAT,
  PRIMARY KEY (`identifiantUtilisateur`)
) ENGINE=InnoDB;

CREATE TABLE `Contrat` (
  `identifiantContrat` INT NOT NULL AUTO_INCREMENT,
  `dateDebut` DATE,
  `dateFin` DATE,
  `clauses` VARCHAR(255),
  PRIMARY KEY (`identifiantContrat`)
) ENGINE=InnoDB;

CREATE TABLE `Dechet` (
  `identifiantDechet` INT NOT NULL AUTO_INCREMENT,
  `typeDechet` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`identifiantDechet`),
  FOREIGN KEY (`typeDechet`)
    REFERENCES `TypeDechet`(`typeDechet`)
      ON UPDATE CASCADE
      ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE `Depot` (
  `identifiantDepot` INT NOT NULL AUTO_INCREMENT,
  `date` DATE,
  `heure` TIME,
  `points` FLOAT,
  PRIMARY KEY (`identifiantDepot`)
) ENGINE=InnoDB;

CREATE TABLE `PoubelleIntelligente` (
  `identifiantPoubelleIntelligente` INT NOT NULL AUTO_INCREMENT,
  `emplacement` VARCHAR(255),
  `capaciteMaximale` FLOAT,
  `typeDechet` VARCHAR(20) NOT NULL,
  `poids` FLOAT,
  PRIMARY KEY (`identifiantPoubelleIntelligente`),
  FOREIGN KEY (`typeDechet`)
    REFERENCES `TypeDechet`(`typeDechet`)
      ON UPDATE CASCADE
      ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 3) Les tables d’association
CREATE TABLE `gerer` (
  `identifiantCentreDeTri` INT NOT NULL,
  `identifiantPoubelleIntelligente` INT NOT NULL,
  PRIMARY KEY (`identifiantCentreDeTri`,`identifiantPoubelleIntelligente`),
  FOREIGN KEY (`identifiantCentreDeTri`)
    REFERENCES `CentreDeTri`(`identifiantCentreDeTri`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantPoubelleIntelligente`)
    REFERENCES `PoubelleIntelligente`(`identifiantPoubelleIntelligente`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `commercer` (
  `identifiantCentreDeTri` INT NOT NULL,
  `identifiantCommerce` INT NOT NULL,
  `identifiantContrat` INT NOT NULL,
  PRIMARY KEY (`identifiantCentreDeTri`,`identifiantCommerce`,`identifiantContrat`),
  FOREIGN KEY (`identifiantCentreDeTri`)
    REFERENCES `CentreDeTri`(`identifiantCentreDeTri`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantCommerce`)
    REFERENCES `Commerce`(`identifiantCommerce`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantContrat`)
    REFERENCES `Contrat`(`identifiantContrat`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `proposer` (
  `identifiantCommerce` INT NOT NULL,
  `identifiantCategorieDeProduits` INT NOT NULL,
  PRIMARY KEY (`identifiantCommerce`,`identifiantCategorieDeProduits`),
  FOREIGN KEY (`identifiantCommerce`)
    REFERENCES `Commerce`(`identifiantCommerce`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantCategorieDeProduits`)
    REFERENCES `CategorieDeProduits`(`identifiantCategorieDeProduits`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `concerner` (
  `identifiantPromotion` INT NOT NULL,
  `identifiantCategorieDeProduits` INT NOT NULL,
  PRIMARY KEY (`identifiantPromotion`,`identifiantCategorieDeProduits`),
  FOREIGN KEY (`identifiantPromotion`)
    REFERENCES `Promotion`(`identifiantPromotion`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantCategorieDeProduits`)
    REFERENCES `CategorieDeProduits`(`identifiantCategorieDeProduits`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `definir` (
  `identifiantContrat` INT NOT NULL,
  `identifiantPromotion` INT NOT NULL,
  PRIMARY KEY (`identifiantContrat`,`identifiantPromotion`),
  FOREIGN KEY (`identifiantContrat`)
    REFERENCES `Contrat`(`identifiantContrat`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantPromotion`)
    REFERENCES `Promotion`(`identifiantPromotion`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `utiliser` (
  `identifiantUtilisateur` INT NOT NULL,
  `identifiantPromotion` INT NOT NULL,
  PRIMARY KEY (`identifiantUtilisateur`,`identifiantPromotion`),
  FOREIGN KEY (`identifiantUtilisateur`)
    REFERENCES `Utilisateur`(`identifiantUtilisateur`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantPromotion`)
    REFERENCES `Promotion`(`identifiantPromotion`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `posseder` (
  `identifiantUtilisateur` INT NOT NULL,
  `identifiantDepot` INT NOT NULL,
  PRIMARY KEY (`identifiantUtilisateur`,`identifiantDepot`),
  FOREIGN KEY (`identifiantUtilisateur`)
    REFERENCES `Utilisateur`(`identifiantUtilisateur`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantDepot`)
    REFERENCES `Depot`(`identifiantDepot`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `jeter` (
  `identifiantPoubelleIntelligente` INT NOT NULL,
  `identifiantDechet` INT NOT NULL,
  PRIMARY KEY (`identifiantPoubelleIntelligente`,`identifiantDechet`),
  FOREIGN KEY (`identifiantPoubelleIntelligente`)
    REFERENCES `PoubelleIntelligente`(`identifiantPoubelleIntelligente`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantDechet`)
    REFERENCES `Dechet`(`identifiantDechet`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `stocker` (
  `identifiantPoubelleIntelligente` INT NOT NULL,
  `identifiantDepot` INT NOT NULL,
  PRIMARY KEY (`identifiantPoubelleIntelligente`,`identifiantDepot`),
  FOREIGN KEY (`identifiantPoubelleIntelligente`)
    REFERENCES `PoubelleIntelligente`(`identifiantPoubelleIntelligente`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantDepot`)
    REFERENCES `Depot`(`identifiantDepot`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `contenir` (
  `identifiantDepot` INT NOT NULL,
  `identifiantDechet` INT NOT NULL,
  PRIMARY KEY (`identifiantDepot`,`identifiantDechet`),
  FOREIGN KEY (`identifiantDepot`)
    REFERENCES `Depot`(`identifiantDepot`)
      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`identifiantDechet`)
    REFERENCES `Dechet`(`identifiantDechet`)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- Insertions des données pour la table TypeDechet
INSERT INTO TypeDechet (typeDechet, poids) VALUES
('plastique', 0.9), -- plastique léger
('verre', 2.5),     -- verre lourd
('metal', 3.0),     -- métal assez dense
('carton', 1.2);    -- carton intermédiaire

