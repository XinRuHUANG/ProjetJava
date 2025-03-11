package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;


public class Commerce {
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static void definirContrat(int id, int idCommerce, int idCentreTri, String dateDebut, String dateFin){
        String query = "INSERT INTO Contrat (idCommerce, idCentreTri, dateDebut, dateFin VALUES ("+idCommerce+","+idCentreTri+","+dateDebut+","+dateFin+");";
        requete(query);
    }

    public static void listerProduitsPromo(Connection connection){
        String query = "SELECT * FROM Promotion, Commerce WHERE Promotion.idCommerce = Commerce.idCommerce;";
        ArrayList<String> list = new ArrayList<>();
        requeteAvecAffichage(query,list);
    }
}
