package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class ContratDAO {

    public static void ajouterContratBDD(Contrat contrat) {

        // Récupérer le dernier identifiant
        String sql = "SELECT MAX(id) FROM Contrat;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("id");
        List<HashMap<String, String>> infos = requeteAvecAffichage(sql, attributs);
        int id = 1;
        if (!infos.isEmpty() && infos.getFirst().get("id") != null) {
            id = Integer.parseInt(infos.getFirst().get("id")) + 1;
        }

        // Récupérer les infos du contrat
        contrat.setIdContrat(id);
        String dateDebut = contrat.getDateDebut().toString();
        String dateFin = contrat.getDateFin().toString();
        String clauses = contrat.getClauses();
        int idCentre = contrat.getCentre().getIdCentreDeTri();

        // Construire la requête d'insertion
        sql = "INSERT INTO Contrat(id, dateDebut, dateFin, clauses, idCentre) VALUES (" +
                id + ", '" + dateDebut + "', '" + dateFin + "', '" + clauses + "', " + idCentre + ");";

        // Exécuter la requête
        requete(sql);
    }

    public static void supprimerContratBDD(Contrat contrat) {
        int id = contrat.getIdContrat();

        // Supprimer le contrat de la table Contrat
        String sql = "DELETE FROM Contrat WHERE id = " + id + ";";
        requete(sql);
    }

    public static void modifierContratBDD(Contrat contrat) {
        int id = contrat.getIdContrat();
        String dateDebut = contrat.getDateDebut().toString();
        String dateFin = contrat.getDateFin().toString();
        String clauses = contrat.getClauses();
        int idCentre = contrat.getCentre().getIdCentreDeTri();

        // Construire la requête de mise à jour
        String sql = "UPDATE Contrat SET " +
                "dateDebut = '" + dateDebut + "', " +
                "dateFin = '" + dateFin + "', " +
                "clauses = '" + clauses + "', " +
                "idCentre = " + idCentre +
                " WHERE id = " + id + ";";

        // Exécuter la requête
        requete(sql);
    }
}
