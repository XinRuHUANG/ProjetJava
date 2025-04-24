DROP DATABASE IF EXISTS projet_java;
CREATE DATABASE projet_java;
USE projet_java;
DROP TABLE IF EXISTS Poubelle;
DROP TABLE IF EXISTS Centre;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Depot;
DROP TABLE IF EXISTS Commerce;
DROP TABLE IF EXISTS Contrat;
DROP TABLE IF EXISTS Dechet;
DROP TABLE IF EXISTS Categorie;
DROP TABLE IF EXISTS Promotion;
DROP TABLE IF EXISTS Jeter;
DROP TABLE IF EXISTS Utiliser;
DROP TABLE IF EXISTS Proposer;
DROP TABLE IF EXISTS Commercer;


 CREATE TABLE Centre (
id INT PRIMARY KEY,
nom VARCHAR(100),
adresse VARCHAR(100)
);

CREATE TABLE Poubelle(
	id INT PRIMARY KEY,
    emplacement VARCHAR(50) NOT NULL,
    capacite INT,
    type ENUM('Plastique', 'Verre', 'Carton', 'Metal') NOT NULL,
    idCentre INT,
    /*idEmplacement INT,*/
    /*FOREIGN KEY (idEmplacement) REFERENCES Emplacement(id),*/
    FOREIGN KEY (idCentre) REFERENCES Centre(id)
    );

CREATE TABLE Contrat (
    id INT PRIMARY KEY,
    dateDebut DATE,
    dateFin DATE,
    clauses VARCHAR(200),
    idCentre INT,
    FOREIGN KEY (idCentre) REFERENCES CentreDeTri(idCentre)
    );

CREATE TABLE Commerce (
    id INT PRIMARY KEY,
    nom VARCHAR(255)
);

CREATE TABLE Utilisateur (
    id INT PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    PointsFidelite INT
);

CREATE TABLE Depot (
    id INT PRIMARY KEY,
    pointsFidelite INT,
    dateFin DATE,
    heure TIME NOT NULL,
    idUtilisateur INT UNIQUE,
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id)
);

CREATE TABLE Dechet (
    id INT PRIMARY KEY,
    type ENUM('Plastique', 'Verre', 'Carton', 'Metal') NOT NULL,
    idDepot INT UNIQUE,
    FOREIGN KEY (idDepot) REFERENCES Depot(id)
);

CREATE TABLE Categorie (
    id INT PRIMARY KEY,
    nom VARCHAR(100)
);

CREATE TABLE Promotion (
    id INT PRIMARY KEY,
    Remise FLOAT,
    pointsRequis FLOAT,
    idCategorie INT UNIQUE,
    idContrat INT UNIQUE,
    FOREIGN KEY (idCategorie) REFERENCES Categorie(id),
    FOREIGN KEY (idContrat) REFERENCES Contrat(id)
);

CREATE TABLE Commercer (
    idCentre INT,
    idCommerce INT,
    PRIMARY KEY (idCentre, idCommerce),
    FOREIGN KEY (idCentre) REFERENCES Centre(id),
    FOREIGN KEY (idCommerce) REFERENCES Commerce(id)
);

CREATE TABLE Jeter (
    idPoubelle INT,
    idDepot INT,
    PRIMARY KEY (idPoubelle, idDepot),
    FOREIGN KEY (idPoubelle) REFERENCES Poubelle(id),
    FOREIGN KEY (idDepot) REFERENCES Depot(id)
);

CREATE TABLE Utiliser (
	idUtilisateur INT,
    idPromotion INT,
    PRIMARY KEY (idUtilisateur, idPromotion),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id),
    FOREIGN KEY (idPromotion) REFERENCES Promotion(id)
    );

CREATE TABLE Proposer (
	idCategorie INT,
    idCommerce INT,
    PRIMARY KEY (idCategorie, idCommerce),
	FOREIGN KEY (idCategorie) REFERENCES Categorie(id),
    FOREIGN KEY (idCommerce) REFERENCES Commerce(id)
);


INSERT INTO Centre VALUES
(1, 'Centre Nord', '12 Rue Verte'),
(2, 'Centre Sud', '25 Avenue Bleue'),
(3, 'Centre Est', '8 Boulevard Jaune');

INSERT INTO Poubelle VALUES
(1, 'Place de la République', 50, 'Plastique', 1),
(2, 'Rue des Lilas', 30, 'Verre', 2),
(3, 'Parc Central', 40, 'Carton', 3),
(4, 'Place du Marché', 60, 'Metal', 1);

INSERT INTO Utilisateur VALUES
(1, 'Dupont', 'Jean', 100),
(2, 'Martin', 'Sophie', 200),
(3, 'Durand', 'Paul', 150);

INSERT INTO Commerce VALUES
(1, 'Supermarché Bio'),
(2, 'Boutique Écolo'),
(3, 'Magasin Zéro Déchet');

INSERT INTO Contrat VALUES
(1, '2024-01-01', '2025-01-01', 'Contrat annuel avec Centre Nord'),
(2, '2023-06-01', '2024-06-01', 'Contrat spécial recyclage verre'),
(3, '2024-03-15', '2025-03-15', 'Partenariat éco-responsable');

INSERT INTO Depot VALUES
(1, 15, '2024-03-01', '08:30:00', 1),
(2, 10, '2024-03-02', '12:45:00', 2),
(3, 3, '2024-03-03', '15:20:00', 3);


INSERT INTO Dechet VALUES
(1, 'Plastique', 1),
(2, 'Verre', 2),
(3, 'Carton', 3);

INSERT INTO Categorie VALUES
(1, 'Recyclable'),
(2, 'Non Recyclable'),
(3, 'Organique');


SELECT *
FROM Utilisateur;
