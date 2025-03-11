package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;


public class Contrat {
    int id;
    String dateDebut;
    String dateFin;

    public Contrat(int id, String dateDebut, String dateFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Contrat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public static void renouvelerContrat(Connection connection, int id, int idCommerce, int idCentreTri, String dateDebut, String dateFin){
        String query = "INSERT INTO Contrat (idCommerce, idCentreTri, dateDebut, dateFin VALUES (?,?,?,?);";
        requete(query);
    }

    public void lireRègles(){

    }
}
