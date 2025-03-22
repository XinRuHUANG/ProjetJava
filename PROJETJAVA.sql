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


CREATE TABLE PoubelleIntelligente(
	idPoubelle INT AUTO_INCREMENT PRIMARY KEY,
        Emplacement String,
	poidsMax DECIMAL,
        volume DECIMAL,
        typePoubelle VARCHAR(10);

CREATE TABLE CentreTri(
	idCentreTri INT AUTO_INCREMENT PRIMARY KEY,
        Emplacement String,
	nomCentreTri VARCHAR(30),
        FOREIGN KEY (idEmplacement) REFERENCES Emplacement(idEmplacement));

CREATE TABLE Utilisateur(
	idUtilisateur INT AUTO_INCREMENT PRIMARY KEY,
	nom INT ,
	prenom INT,
	pointFidelite DECIMAL;

CREATE TABLE Commerce(
	idCommerce INT AUTO_INCREMENT PRIMARY KEY,
        nomCommerce VARCHAR(15),
        Emplacement String);	

CREATE TABLE Contrat(
	idCommerce INT AUTO_INCREMENT PRIMARY KEY,
        idCentreTri INT,
	dateDebut DATE,
        dateFin DATE,
	CONSTRAINT pk_Contrat PRIMARY KEY (idCommerce, idCentreTri, dateDebut),
	FOREIGN KEY fk_commerce(idCommerce) REFERENCES Commerce(idCommerce), 
	FOREIGN KEY fk_centre(idCentreTri) REFERENCES CentreTri(idCentreTri));

CREATE TABLE Depot(
	idDepot INT AUTO_INCREMENT PRIMARY KEY,
        date DATE,
	heure LocalTime,
        points DECIMAL);

CREATE TABLE Dechet(
	idDechet INT AUTO_INCREMENT PRIMARY KEY,
	type VARCHAR(10));

CREATE TABLE CategorieDeproduits(
	idCat INT AUTO_INCREMENT PRIMARY KEY,
	nom String);

CREATE TABLE Promotion(
	idPromotion INT,
	pourcentageRemise DECIMAL,
	ptRequis DECIMAL);

    

