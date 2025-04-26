package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class ContratDAO {

    public static void ajouterContratBDD(Contrat contrat) {

        // Récupérer le dernier identifiant
        String requete = "SELECT MAX(identifiantContrat) FROM Contrat;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantContrat");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.get(0).get("identifiantContrat")) + 1;

        // Récupérer les infos du contrat
        contrat.setIdContrat(id);
        String dateDebut = contrat.getDateDebut().toString();
        String dateFin = contrat.getDateFin().toString();
        String clauses = contrat.getClauses();
        int idCentre = contrat.getCommercer().getIdCentreDeTri();

        requete = "INSERT INTO Contrat(identifiantContrat, dateDebut, dateFin, clauses, identifiantCentreDeTri) VALUES (" +
                id + ", '" + dateDebut + "', '" + dateFin + "', '" + clauses + "', " + idCentre + ");";
        requete(requete);

        requete = "INSERT INTO definir(identifiantContrat, identifiantPromotion) VALUES (" +
                id + ", '" + contrat.getDefinir().getIdPromotion() + ");";
        requete(requete);

        requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) VALUES (" +
                contrat.getCommercer().getIdCentreDeTri() + "," + contrat.getCommerce().getIdentifiantCommerce() + "," + contrat.getIdContrat() + ");";
        requete(requete);
    }

    public static void supprimerContratBDD(Contrat contrat) {
        int id = contrat.getIdContrat();
        String requete = "DELETE FROM definir WHERE identifiantContrat = " + id + ";";
        requete(requete);
        requete = "DELETE FROM commercer WHERE identifiantContrat = " + id + ";";
        requete(requete);
        requete = "DELETE FROM Contrat WHERE identifiantContrat = " + id + ";";
        requete(requete);
    }

    public static void actualiserContratBDD(Contrat contrat, String instruction){
        int id = contrat.getIdContrat();
        String requete;
        if (instruction == "dateDebut") {
            requete = "UPDATE Contrat SET dateDebut = " + contrat.getDateDebut() + " WHERE identifiantContrat = " + id + ";";
            requete(requete);
        }
        if (instruction == "dateFin") {
            requete = "UPDATE Contrat SET dateFin = " + contrat.getDateFin() + " WHERE identifiantContrat = " + id + ";";
            requete(requete);
        }
        if (instruction == "clauses") {
            requete = "UPDATE Contrat SET clauses = " + contrat.getClauses() + " WHERE identifiantContrat = " + id + ";";
            requete(requete);
        }
        if (instruction == "commercer" || instruction == "commerce") {
            requete = "DELETE FROM commercer WHERE identifiantContrat = " + id + ";";
            requete(requete);
            requete = "INSERT INTO commercer(identifiantContrat, identifiantCommerce, identifiantCentreDeTri) VALUES ("
                    + id + "," + contrat.getCommerce().getIdentifiantCommerce() + "," + contrat.getCommercer().getIdCentreDeTri() + ");";
            requete(requete);
        }
        if (instruction == "definir") {
            requete = "DELETE FROM definir WHERE identifiantContrat = " + id + ";";
            requete(requete);
            requete = "INSERT INTO definir(identifiantContrat, identifiantPromotion) VALUES ("
                    + id + "," + contrat.getDefinir().getIdPromotion() + ");";
            requete(requete);
        }
    }
}
