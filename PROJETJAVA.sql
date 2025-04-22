DROP DATABASE IF EXISTS Recyclage;
CREATE DATABASE Recyclage;
USE Recyclage;

DROP TABLE IF EXISTS Commerce;
DROP TABLE IF EXISTS CentreTri;
DROP TABLE IF EXISTS PoubelleIntelligente;
DROP TABLE IF EXISTS Depot;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Contrat;
DROP TABLE IF EXISTS CategorieDeProduits;
DROP TABLE IF EXISTS Dechet;
DROP TABLE IF EXISTS Promotion;


CREATE TABLE CentreTri(
	idCentreTri INT AUTO_INCREMENT PRIMARY KEY,
    Emplacement VARCHAR(30),
	nomCentreTri VARCHAR(30));

CREATE TABLE PoubelleIntelligente(
	idPoubelle INT AUTO_INCREMENT PRIMARY KEY, 
	idCentreDeTri INT,
    Emplacement VARCHAR(30),
	poidsMax DECIMAL,
    volume DECIMAL,
    typePoubelle VARCHAR(10),
	FOREIGN KEY (idCentreDeTri) REFERENCES CentreTri(idCentreTri));

CREATE TABLE Utilisateur(
	idUtilisateur INT AUTO_INCREMENT PRIMARY KEY,
	nom INT ,
	prenom INT,
	pointFidelite DECIMAL);

CREATE TABLE Commerce(
	idCommerce INT AUTO_INCREMENT PRIMARY KEY,
    nomCommerce VARCHAR(15),
    Emplacement VARCHAR(30));

CREATE TABLE Contrat(
	idContrat INT AUTO_INCREMENT PRIMARY KEY,
	dateDebut DATE,
    dateFin DATE);

CREATE TABLE Depot(
	idDepot INT AUTO_INCREMENT PRIMARY KEY,
	idUtilisateur INT,
    date DATE,
    points DECIMAL,
	FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur));

CREATE TABLE Dechet(
	idDechet INT AUTO_INCREMENT PRIMARY KEY,
	idDepot INT,
	type VARCHAR(10),
	FOREIGN KEY (idDepot) REFERENCES Depot(idDepot));

CREATE TABLE CategorieDeProduits(
	idCat INT AUTO_INCREMENT PRIMARY KEY,
	nom VARCHAR(20));

CREATE TABLE Promotion(
	idPromotion INT AUTO_INCREMENT PRIMARY KEY,
	idCat INT,
	idContrat INT,
	pourcentageRemise DECIMAL,
	ptRequis DECIMAL,
	FOREIGN KEY (idCat) REFERENCES CategorieDeProduits(idCat),
	FOREIGN KEY (idContrat) REFERENCES Contrat(idContrat));

    

