package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;
import static main.backend.fonctions.TypeDechetEnum.TypeDechet;

public class DechetDAO {

    public static void ajouterDechetBDD(Dechet dechet) throws SQLException {

        // Récupérer le dernier identifiant
        String requete = "SELECT MAX(identifiantDechet) FROM dechet;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDechet");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantDechet");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        // Récupérer les infos du déchet
        dechet.setIdentifiantDechet(id);
        TypeDechet type = dechet.getType(); // enum -> String
        Depot contenir = dechet.getContenir();
        int idDepot = contenir.getIdentifiantDepot();  // Obtenir l'identifiant du dépôt via la classe depot

        requete = "INSERT INTO dechet(identifiantDechet, type, identifiantDepot) VALUES (" +
                id + ", '" + type + "', " + idDepot + ");"; // <-- ajouté ' autour de type
        requete(requete);

        if (dechet.getContenir() != null) {
            requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) VALUES (" +
                    id + ", " + dechet.getContenir().getIdentifiantDepot() + ");";
            requete(requete);
        }

        if (dechet.getStocker() != null) {
            requete = "INSERT INTO stocker(identifiantDechet, identifiantPoubelleIntelligente) VALUES (" +
                    id + ", " + dechet.getStocker().getIdentifiantPoubelle() + ");";
            requete(requete);
        }
    }

    public static void supprimerDechetBDD(Dechet dechet) {
        int id = dechet.getIdentifiantDechet();
        // Suppression du déchet de la table contenir
        String requete = "DELETE FROM contenir WHERE identifiantDechet = " + id + ";";
        requete(requete);
        // Supprimer le déchet de la table Dechet
        requete = "DELETE FROM dechet WHERE identifiantDechet = " + id + ";";
        requete(requete);
        // Supprimer le déchet de la table stocker
        requete = "DELETE FROM stocker WHERE identifiantDechet = " + id + ";";
        requete(requete);
    }

    public static void actualiserDechetBDD(Dechet dechet, String instruction) {
        int identifiantDechet = dechet.getIdentifiantDechet();
        String requete;
        if (instruction.equals("type")) {
            requete = "UPDATE dechet SET type = '" + dechet.getType() + "' WHERE identifiantDechet = " + identifiantDechet + ";";
            requete(requete);
        }
        if (instruction.equals("contenir")) {
            requete = "UPDATE contenir SET identifiantDepot = " + dechet.getContenir().getIdentifiantDepot() +
                    " WHERE identifiantDechet = " + identifiantDechet + ";";
            requete(requete);
        }
        if (instruction.equals("stocker")) {
            requete = "DELETE FROM stocker WHERE identifiantDechet = " + identifiantDechet + ";";
            requete(requete);
            requete = "INSERT INTO stocker(identifiantDechet, identifiantPoubelleIntelligente) VALUES (" +
                    identifiantDechet + ", " + dechet.getStocker().getIdentifiantPoubelle() + ");";
            requete(requete);
        }
    }
}
