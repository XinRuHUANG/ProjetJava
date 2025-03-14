DROP DATABASE IF EXISTS Recyclage;
CREATE DATABASE Recyclage;
USE Recyclage;

DROP TABLE IF EXISTS Commerce;
DROP TABLE IF EXISTS Emplacement;
DROP TABLE IF EXISTS CentreTri;
DROP TABLE IF EXISTS PoubelleIntelligente;
DROP TABLE IF EXISTS Corbeille;
DROP TABLE IF EXISTS Compte;
DROP TABLE IF EXISTS Menage;
DROP TABLE IF EXISTS Contrat;
DROP TABLE IF EXISTS historiqueDepot;

CREATE TABLE Emplacement(
	idEmplacement INT AUTO_INCREMENT PRIMARY KEY,
    	nomEmplacement VARCHAR(50),
	latitude DECIMAL(12,10) NOT NULL,
	longitude DECIMAL(13,10) NOT NULL,
	CONSTRAINT check_latitude CHECK (latitude BETWEEN -90 AND 90),
	CONSTRAINT check_longitude CHECK (longitude BETWEEN -180 AND 180));

CREATE TABLE PoubelleIntelligente(
	idPoubelle INT AUTO_INCREMENT PRIMARY KEY,
        idEmplacement INT,
	poidsMax DECIMAL,
        volume DECIMAL,
        typePoubelle VARCHAR(10),
        FOREIGN KEY (idEmplacement) REFERENCES Emplacement(idEmplacement));

CREATE TABLE CentreTri(
	idCentreTri INT AUTO_INCREMENT PRIMARY KEY,
        idEmplacement INT,
	nomCentreTri VARCHAR(30),
        FOREIGN KEY (idEmplacement) REFERENCES Emplacement(idEmplacement));

CREATE TABLE Menage(
	idMenage INT AUTO_INCREMENT PRIMARY KEY,
        idEmplacement INT,
	nomRepresentant INT ,
	prenomRepresentant INT,
        FOREIGN KEY (idEmplacement) REFERENCES Emplacement(idEmplacement));
 
CREATE TABLE Corbeille(
	idCorbeille INT AUTO_INCREMENT PRIMARY KEY,
	qtePlastique DECIMAL,
	qteVerre DECIMAL,
	qteCarton DECIMAL,
	qteMetaux DECIMAL,
        idMenage INT,
        FOREIGN KEY (idMenage) REFERENCES Menage(idMenage));

CREATE TABLE Compte(
	idCompte INT AUTO_INCREMENT PRIMARY KEY, 
        idMenage INT,
	ptFidelite INT,
        codeAcces INT,
        FOREIGN KEY (idMenage) REFERENCES Menage(idMenage));

CREATE TABLE Commerce(
	idCommerce INT AUTO_INCREMENT PRIMARY KEY,
        nomCommerce VARCHAR(15),
        idEmplacement INT,
        FOREIGN KEY (idEmplacement) REFERENCES Emplacement(idEmplacement));	

CREATE TABLE Contrat(
	idCommerce INT,
        idCentreTri INT,
	dateDebut DATE,
        dateFin DATE,
	CONSTRAINT pk_Contrat PRIMARY KEY (idCommerce, idCentreTri, dateDebut),
	FOREIGN KEY fk_commerce(idCommerce) REFERENCES Commerce(idCommerce), 
	FOREIGN KEY fk_centre(idCentreTri) REFERENCES CentreTri(idCentreTri));

CREATE TABLE historiqueDepot(
	idPoubelle INT,
        idCorbeille INT,
        idCompte INT,
        CONSTRAINT pk_Depot PRIMARY KEY (idPoubelle, idCorbeille, idCompte),
        FOREIGN KEY (idCorbeille) REFERENCES Corbeille(idCorbeille), 
	FOREIGN KEY (idPoubelle) REFERENCES PoubelleIntelligente(idPoubelle));

CREATE TABLE Promotion(
       idCommerce INT,
       idCompte INT,
       pourcentageRemise INT,
       ptRequis INT,
       CONSTRAINT pk_promotion PRIMARY KEY (idCommerce,idCompte),
       FOREIGN KEY (idCommerce) REFERENCES Commerce(idCommerce),
       FOREIGN KEY (idCompte) REFERENCES Compte(idCompte));

    

