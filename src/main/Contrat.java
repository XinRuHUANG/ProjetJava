package main;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Contrat {
    private int identifiantContrat;
    private Date dateDebut;
    private Date dateFin;
    private String clauses;
    //Modélisation des associations
    private Set<Promotion> definir;

    public Contrat(int identifiantContrat, Date dateDebut, Date dateFin) {
        this.identifiantContrat = identifiantContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Contrat() {
    }

    public int getIdentifiantContrat() {
        return identifiantContrat;
    }

    public void setIdentifiantContrat(int identifiantContrat) {
        this.identifiantContrat = identifiantContrat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
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

    }
}
