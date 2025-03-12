package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;
import static main.Commerce.listerProduitsPromo;


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

    public static void renouvelerContrat(int id, int idCommerce, int idCentreTri, String newDateDebut, String newDateFin){
        String query = "UPDATE Contrat SET dateDebut = "+newDateDebut+", dateFin = "+newDateFin+" WHERE id = "+id+";";
        requete(query);
    }

    public void lireRegles(int idCommerce){
        String query = "SELECT pourcentageRemise, ptRequis, dateDebut, dateFin FROM Contrat, Promotion";
        ArrayList<String> regles = new ArrayList<>();
        requeteAvecAffichage(query, regles);

        listerProduitsPromo(idCommerce);
    }
}
