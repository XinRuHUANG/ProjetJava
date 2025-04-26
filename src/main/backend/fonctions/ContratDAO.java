package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class ContratDAO {

    public static void ajouterContratBDD(Contrat contrat) throws SQLException {
        // Récupérer le dernier identifiant
        String requete = "SELECT MAX(identifiantContrat) FROM contrat;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantContrat");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantContrat");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        // Récupérer les infos du contrat
        contrat.setIdContrat(id);
        String dateDebut = contrat.getDateDebut().toString();
        String dateFin = contrat.getDateFin().toString();
        String clauses = contrat.getClauses();
        int idCentre = contrat.getCommercer().getIdCentreDeTri();

        requete = "INSERT INTO contrat(identifiantContrat, dateDebut, dateFin, clauses, identifiantCentreDeTri) VALUES (" +
                id + ", '" + dateDebut + "', '" + dateFin + "', '" + clauses + "', " + idCentre + ");";
        requete(requete);

        if (contrat.getDefinir() != null) {
            requete = "INSERT INTO definir(identifiantContrat, identifiantPromotion) VALUES (" +
                    id + ", " + contrat.getDefinir().getIdPromotion() + ");";
            requete(requete);
        }

        if (contrat.getCommerce() != null && contrat.getCommercer() != null) {
            requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) VALUES (" +
                    contrat.getCommercer().getIdCentreDeTri() + "," +
                    contrat.getCommerce().getIdentifiantCommerce() + "," +
                    id + ");";
            requete(requete);
        }
    }

    public static void supprimerContratBDD(Contrat contrat) {
        int id = contrat.getIdContrat();
        String requete = "DELETE FROM definir WHERE identifiantContrat = " + id + ";";
        requete(requete);
        requete = "DELETE FROM commercer WHERE identifiantContrat = " + id + ";";
        requete(requete);
        requete = "DELETE FROM contrat WHERE identifiantContrat = " + id + ";";
        requete(requete);
    }

    public static void actualiserContratBDD(Contrat contrat, String instruction) {
        int id = contrat.getIdContrat();
        String requete;
        if (instruction.equals("dateDebut")) {
            requete = "UPDATE contrat SET dateDebut = '" + contrat.getDateDebut() + "' WHERE identifiantContrat = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("dateFin")) {
            requete = "UPDATE contrat SET dateFin = '" + contrat.getDateFin() + "' WHERE identifiantContrat = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("clauses")) {
            requete = "UPDATE contrat SET clauses = '" + contrat.getClauses() + "' WHERE identifiantContrat = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("commercer") || instruction.equals("commerce")) {
            requete = "DELETE FROM commercer WHERE identifiantContrat = " + id + ";";
            requete(requete);
            requete = "INSERT INTO commercer(identifiantContrat, identifiantCommerce, identifiantCentreDeTri) VALUES (" +
                    id + "," + contrat.getCommerce().getIdentifiantCommerce() + "," + contrat.getCommercer().getIdCentreDeTri() + ");";
            requete(requete);
        }
        if (instruction.equals("definir")) {
            requete = "DELETE FROM definir WHERE identifiantContrat = " + id + ";";
            requete(requete);
            requete = "INSERT INTO definir(identifiantContrat, identifiantPromotion) VALUES (" +
                    id + "," + contrat.getDefinir().getIdPromotion() + ");";
            requete(requete);
        }
    }
}
