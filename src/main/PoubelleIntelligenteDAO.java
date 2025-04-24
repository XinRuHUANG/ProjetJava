package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligenteDAO extends PoubelleIntelligente {

    public PoubelleIntelligenteDAO(int identifiantPoubelle, String emplacement, float capaciteMaximale, TypeDechet type) {
        super(identifiantPoubelle, emplacement, capaciteMaximale, type);
    }

    // Méthode pour ajouter une poubelle dans la base de données
    public static void ajouterPoubelleBDD(PoubelleIntelligente poubelle) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantPoubelle) FROM PoubelleIntelligente;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPoubelle");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.get(0).get("identifiantPoubelle")) + 1;

        // Récupération des informations
        poubelle.setIdentifiantPoubelle(id);
        String emplacement = poubelle.getEmplacement();
        float capaciteMaximale = poubelle.getCapaciteMaximale();
        String type = poubelle.getType().toString();

        // Insertion de la nouvelle poubelle dans la table PoubelleIntelligente
        requete = "INSERT INTO PoubelleIntelligente(identifiantPoubelle, type, emplacement, capaciteMaximale) " +
                  "VALUES (" + Integer.toString(id) + ", '" + type + "', '" + emplacement + "', " + capaciteMaximale + ");";
        requete(requete);

        // Liaison avec le centre de tri
        for (CentreDeTri centreDeTri : poubelle.getGerer()) {
            requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) " +
                      "VALUES (" + Integer.toString(centreDeTri.getIdCentreDeTri()) + ", " + Integer.toString(id) + ");";
            requete(requete);
        }
    }

    // Méthode pour récupérer une poubelle par son identifiant
    public static PoubelleIntelligente getPoubelleById(int identifiantPoubelle) {
        String requete = "SELECT * FROM PoubelleIntelligente WHERE identifiantPoubelle = " + Integer.toString(identifiantPoubelle) + ";";
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, new ArrayList<>());
        if (infos.isEmpty()) {
            return null;
        }
        HashMap<String, String> poubelleInfo = infos.get(0);
        String emplacement = poubelleInfo.get("emplacement");
        float capaciteMaximale = Float.parseFloat(poubelleInfo.get("capaciteMaximale"));
        TypeDechet type = TypeDechet.valueOf(poubelleInfo.get("type"));

        PoubelleIntelligente poubelle = new PoubelleIntelligente(identifiantPoubelle, emplacement, capaciteMaximale, type);
        return poubelle;
    }

    // Méthode pour supprimer une poubelle
    public static void supprimerPoubelle(int identifiantPoubelle) {
        String requete = "DELETE FROM PoubelleIntelligente WHERE identifiantPoubelle = " + Integer.toString(identifiantPoubelle) + ";";
        requete(requete);
        // Supprimer les liaisons dans la table "gerer"
        requete = "DELETE FROM gerer WHERE identifiantPoubelleIntelligente = " + Integer.toString(identifiantPoubelle) + ";";
        requete(requete);
    }
}
