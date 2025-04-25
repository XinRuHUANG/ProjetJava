-- ------------------------------------------------------------------
-- Énumération TypeDechet (avec son attribut 'poids')
-- ------------------------------------------------------------------
CREATE TABLE TypeDechet (
    typeDechet    VARCHAR(20)       PRIMARY KEY,    -- 'verre','carton','plastique','metal'
    poids         DOUBLE PRECISION                -- poids unitaire associé au type
);

-- ------------------------------------------------------------------
-- Classes principales
-- ------------------------------------------------------------------

CREATE TABLE CentreDeTri (
    identifiantCentreDeTri   INT             PRIMARY KEY,
    nom                       VARCHAR(255),
    adresse                   VARCHAR(255)
);

CREATE TABLE PoubelleIntelligente (
    identifiantPoubelleIntelligente  INT             PRIMARY KEY,
    emplacement                       VARCHAR(255),
    capaciteMaximale                  REAL,
    typeDechet                        VARCHAR(20)     NOT NULL
        REFERENCES TypeDechet(typeDechet),
    poids                             REAL            -- poids courant dans la poubelle
);

CREATE TABLE Dechet (
    identifiantDechet    INT             PRIMARY KEY,
    typeDechet           VARCHAR(20)     NOT NULL
        REFERENCES TypeDechet(typeDechet)
);

CREATE TABLE Depot (
    identifiantDepot     INT             PRIMARY KEY,
    date                 DATE,
    heure                TIME,
    points               REAL
);

CREATE TABLE Contrat (
    identifiantContrat   INT             PRIMARY KEY,
    dateDebut            DATE,
    dateFin              DATE,
    clauses              TEXT
);

CREATE TABLE Commerce (
    identifiantCommerce  INT             PRIMARY KEY,
    nom                  VARCHAR(255)
);

CREATE TABLE CategorieDeProduits (
    identifiantCategorieDeProduits INT             PRIMARY KEY,
    nom                            VARCHAR(255)
);

CREATE TABLE Promotion (
    identifiantPromotion INT             PRIMARY KEY,
    pourcentageRemise    REAL,
    pointRequis          REAL
);

CREATE TABLE Utilisateur (
    identifiantUtilisateur INT           PRIMARY KEY,
    nom                    VARCHAR(255),
    prenom                 VARCHAR(255),
    pointsFidelite         REAL
);

-- ------------------------------------------------------------------
-- Tables d’association
-- ------------------------------------------------------------------

-- 1) CentreDeTri gère PoubelleIntelligente
CREATE TABLE gerer (
    identifiantPoubelleIntelligente  INT,
    identifiantCentreDeTri           INT,
    PRIMARY KEY (identifiantPoubelleIntelligente, identifiantCentreDeTri),
    FOREIGN KEY (identifiantPoubelleIntelligente)
        REFERENCES PoubelleIntelligente(identifiantPoubelleIntelligente),
    FOREIGN KEY (identifiantCentreDeTri)
        REFERENCES CentreDeTri(identifiantCentreDeTri)
);

-- 2) CentreDeTri <–> Commerce <–> Contrat : commercer
CREATE TABLE commercer (
    identifiantCentreDeTri   INT,
    identifiantCommerce      INT,
    identifiantContrat       INT,
    PRIMARY KEY (identifiantCentreDeTri, identifiantCommerce, identifiantContrat),
    FOREIGN KEY (identifiantCentreDeTri)
        REFERENCES CentreDeTri(identifiantCentreDeTri),
    FOREIGN KEY (identifiantCommerce)
        REFERENCES Commerce(identifiantCommerce),
    FOREIGN KEY (identifiantContrat)
        REFERENCES Contrat(identifiantContrat)
);

-- 3) Commerce propose CategorieDeProduits
CREATE TABLE proposer (
    identifiantCommerce          INT,
    identifiantCategorieDeProduits INT,
    PRIMARY KEY (identifiantCommerce, identifiantCategorieDeProduits),
    FOREIGN KEY (identifiantCommerce)
        REFERENCES Commerce(identifiantCommerce),
    FOREIGN KEY (identifiantCategorieDeProduits)
        REFERENCES CategorieDeProduits(identifiantCategorieDeProduits)
);

-- 4) Contrat définit Promotion
CREATE TABLE definir (
    identifiantContrat     INT,
    identifiantPromotion   INT,
    PRIMARY KEY (identifiantContrat, identifiantPromotion),
    FOREIGN KEY (identifiantContrat)
        REFERENCES Contrat(identifiantContrat),
    FOREIGN KEY (identifiantPromotion)
        REFERENCES Promotion(identifiantPromotion)
);

-- 5) Promotion utilise Utilisateur
CREATE TABLE utiliser (
    identifiantPromotion   INT,
    identifiantUtilisateur INT,
    PRIMARY KEY (identifiantPromotion, identifiantUtilisateur),
    FOREIGN KEY (identifiantPromotion)
        REFERENCES Promotion(identifiantPromotion),
    FOREIGN KEY (identifiantUtilisateur)
        REFERENCES Utilisateur(identifiantUtilisateur)
);

-- 6) Depot contient Dechet
CREATE TABLE contenir (
    identifiantDepot     INT,
    identifiantDechet    INT,
    PRIMARY KEY (identifiantDepot, identifiantDechet),
    FOREIGN KEY (identifiantDepot)
        REFERENCES Depot(identifiantDepot),
    FOREIGN KEY (identifiantDechet)
        REFERENCES Dechet(identifiantDechet)
);

-- 7) PoubelleIntelligente jette Dechet
CREATE TABLE jeter (
    identifiantPoubelleIntelligente  INT,
    identifiantDechet                INT,
    PRIMARY KEY (identifiantPoubelleIntelligente, identifiantDechet),
    FOREIGN KEY (identifiantPoubelleIntelligente)
        REFERENCES PoubelleIntelligente(identifiantPoubelleIntelligente),
    FOREIGN KEY (identifiantDechet)
        REFERENCES Dechet(identifiantDechet)
);

-- ------------------------------------------------------------------
-- Exemples de données cohérentes
-- ------------------------------------------------------------------

-- 1) TypeDechet
INSERT INTO TypeDechet(typeDechet, poids) VALUES
  ('verre',     0.25),
  ('carton',    0.30),
  ('plastique', 0.05),
  ('metal',     0.40);

-- 2) Centres de tri
INSERT INTO CentreDeTri(identifiantCentreDeTri, nom,            adresse) VALUES
  (1, 'Centre Paris Nord',   '10 Rue de la Science, Paris'),
  (2, 'Centre Lyon Est',      '5 Avenue des Lumières, Lyon'),
  (3, 'Centre Marseille Sud', '20 Boulevard du Port, Marseille');

-- 3) Poubelles intelligentes
INSERT INTO PoubelleIntelligente(
    identifiantPoubelleIntelligente,
    emplacement,
    capaciteMaximale,
    typeDechet,
    poids
) VALUES
  (100, 'Boulevard Voltaire',    50.0, 'plastique', 12.3),
  (101, 'Place de la République',75.5, 'verre',      30.0),
  (102, 'Cours Julien',          60.0, 'carton',     8.0),
  (103, 'Rue de la Paix',        40.0, 'metal',      5.5),
  (104, 'Avenue Victor Hugo',    55.0, 'plastique', 20.0);

-- 4) Déchets
INSERT INTO Dechet(identifiantDechet, typeDechet) VALUES
  (1000, 'plastique'),
  (1001, 'verre'),
  (1002, 'carton'),
  (1003, 'plastique'),
  (1004, 'metal'),
  (1005, 'verre'),
  (1006, 'carton'),
  (1007, 'metal'),
  (1008, 'plastique'),
  (1009, 'verre');

-- 5) Dépôts
INSERT INTO Depot(identifiantDepot, date,       heure,     points) VALUES
  (200, '2025-04-20', '09:30:00', 10.0),
  (201, '2025-04-21', '14:15:00',  5.0),
  (202, '2025-04-22', '17:45:00',  8.0);

-- 6) Contrats
INSERT INTO Contrat(identifiantContrat, dateDebut,   dateFin,     clauses) VALUES
  (300, '2025-01-01', '2025-12-31', 'Collecte hebdomadaire'),
  (301, '2025-02-15', '2026-02-14', 'Maintenance trimestrielle'),
  (302, '2025-03-01', '2025-09-30', 'Extension capacité & stats mensuelles');

-- 7) Commerces
INSERT INTO Commerce(identifiantCommerce, nom) VALUES
  (400, 'EcoMarket Paris'),
  (401, 'GreenShop Lyon'),
  (402, 'Recyclo Marseille');

-- 8) Catégories de produits
INSERT INTO CategorieDeProduits(identifiantCategorieDeProduits, nom) VALUES
  (500, 'Emballages'),
  (501, 'Bouteilles'),
  (502, 'Journaux'),
  (503, 'Canettes');

-- 9) Promotions
INSERT INTO Promotion(identifiantPromotion, pourcentageRemise, pointRequis) VALUES
  (600, 10.0,  5.0),
  (601, 20.0, 10.0),
  (602, 50.0, 25.0);

-- 10) Utilisateurs
INSERT INTO Utilisateur(identifiantUtilisateur, nom,   prenom, pointsFidelite) VALUES
  (700, 'Dupont', 'Jean',  15.0),
  (701, 'Martin', 'Claire',30.0),
  (702, 'Durand','Luc',     5.0),
  (703, 'Petit', 'Emma',   50.0);

-- ------------------------------------------------------------------
-- Tables d’association avec exemples
-- ------------------------------------------------------------------

-- a) gerer (CentreDeTri ↔ PoubelleIntelligente)
INSERT INTO gerer(identifiantPoubelleIntelligente, identifiantCentreDeTri) VALUES
  (100, 1),
  (101, 1),
  (102, 2),
  (103, 2),
  (104, 3);

-- b) commercer (CentreDeTri ↔ Commerce ↔ Contrat)
INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) VALUES
  (1, 400, 300),
  (1, 401, 301),
  (2, 401, 301),
  (3, 402, 302);

-- c) proposer (Commerce ↔ CategorieDeProduits)
INSERT INTO proposer(identifiantCommerce, identifiantCategorieDeProduits) VALUES
  (400, 500),
  (400, 501),
  (401, 500),
  (401, 502),
  (402, 501),
  (402, 503);

-- d) definir (Contrat ↔ Promotion)
INSERT INTO definir(identifiantContrat, identifiantPromotion) VALUES
  (300, 600),
  (301, 601),
  (301, 602),
  (302, 602);

-- e) utiliser (Promotion ↔ Utilisateur)
INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) VALUES
  (600, 700),
  (600, 701),
  (601, 701),
  (602, 702),
  (602, 703);

-- f) contenir (Depot ↔ Dechet)
INSERT INTO contenir(identifiantDepot, identifiantDechet) VALUES
  (200, 1000),
  (200, 1001),
  (201, 1002),
  (201, 1003),
  (201, 1004),
  (202, 1005),
  (202, 1006),
  (202, 1007);

-- g) jeter (PoubelleIntelligente ↔ Dechet)
INSERT INTO jeter(identifiantPoubelleIntelligente, identifiantDechet) VALUES
  (100, 1000),
  (100, 1003),
  (101, 1001),
  (102, 1002),
  (103, 1004),
  (104, 1005),
  (104, 1008),
  (101, 1009);