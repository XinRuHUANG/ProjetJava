package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DepotDAO extends Depot {

    public DepotDAO(int identifiantDepot, LocalDate date, LocalTime heure, float points) {
        super(identifiantDepot, date, heure, points);
    }

    // Méthode pour ajouter un dépôt dans la base de données
    public static void ajouterDepotBDD(Depot depot) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantDepot) FROM Depot;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDepot");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.get(0).get("identifiantDepot")) + 1;

        // Récupération des informations
        depot.setIdentifiantDepot(id);
        LocalDate date = depot.getDate();
        LocalTime heure = depot.getHeure();
        float points = depot.getPoints();

        // Insertion du nouveau dépôt dans la table Depot
        requete = "INSERT INTO Depot(identifiantDepot, date, heure, points) " +
                  "VALUES (" + Integer.toString(id) + ", '" + date.toString() + "', '" + heure.toString() + "', " + points + ");";
        requete(requete);

        // Liaison avec les déchets dans la table contenir
        for (Dechet dechet : depot.getContenir()) {
            requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) VALUES (" + 
                      Integer.toString(dechet.getIdentifiantDechet()) + ", " + Integer.toString(id) + ");";
            requete(requete);
        }
    }

    // Méthode pour récupérer un dépôt par son identifiant
    public static Depot getDepotById(int identifiantDepot) {
        String requete = "SELECT * FROM Depot WHERE identifiantDepot = " + Integer.toString(identifiantDepot) + ";";
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, new ArrayList<>());
        if (infos.isEmpty()) {
            return null;
        }
        HashMap<String, String> depotInfo = infos.get(0);
        LocalDate date = LocalDate.parse(depotInfo.get("date"));
        LocalTime heure = LocalTime.parse(depotInfo.get("heure"));
        float points = Float.parseFloat(depotInfo.get("points"));

        Depot depot = new Depot(identifiantDepot, date, heure, points);
        // Récupérer les déchets associés
        requete = "SELECT * FROM contenir WHERE identifiantDepot = " + Integer.toString(identifiantDepot) + ";";
        List<HashMap<String, String>> dechetsInfos = requeteAvecAffichage(requete, new ArrayList<>());
        List<Dechet> dechets = new ArrayList<>();
        for (HashMap<String, String> dechetInfo : dechetsInfos) {
            int identifiantDechet = Integer.parseInt(dechetInfo.get("identifiantDechet"));
            // Vous devez ici récupérer l'objet Dechet via un autre DAO, mais pour simplifier, on utilise ici juste l'identifiant
            dechets.add(new Dechet(identifiantDechet));  // Vous devrez ajouter un DAO pour Dechet
        }
        depot.setContenir(dechets);
        return depot;
    }

    // Méthode pour supprimer un dépôt
    public static void supprimerDepot(int identifiantDepot) {
        String requete = "DELETE FROM Depot WHERE identifiantDepot = " + Integer.toString(identifiantDepot) + ";";
        requete(requete);
        // Supprimer les liaisons dans la table "contenir"
        requete = "DELETE FROM contenir WHERE identifiantDepot = " + Integer.toString(identifiantDepot) + ";";
        requete(requete);
    }
}
