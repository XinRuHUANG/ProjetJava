package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;
import static main.TypeDechetEnum.TypeDechet;

public class DechetDAO {

    public static void ajouterDechetBDD(Dechet dechet) {

        // Récupérer le dernier identifiant
        String sql = "SELECT MAX(identifiantDechet) FROM Dechet;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDechet");
        List<HashMap<String, String>> infos = requeteAvecAffichage(sql, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCentreDeTri")) + 1;

        //Récupérer les infos du déchet
        dechet.setIdentifiantDechet(id);
        TypeDechetEnum type = dechet.getType(); // enum -> String
        Depot contenir = dechet.getContenir();
        int idDepot = contenir.getIdentifiantDepot();  // Obtenir l'identifiant du dépôt via la classe depot

        // Construire la requête d'insertion
        sql = "INSERT INTO Dechet(identifiantDechet, type, identifiantDepot) VALUES (" +
                id + "," + type + "," + idDepot + ");";
        //Exécuter la requête
        requete(sql);
    }

    public static void retirerDechetBDD(Dechet dechet) {
        int id = dechet.getIdentifiantDechet();
        //Suppression du déchet de la table contenir
        String requete = "DELETE FROM contenir WHERE idDechet = " + id + ";";
        requete(requete);
        // Supprimer le déchet de la table Dechet
        requete = "DELETE FROM Dechet WHERE idDechet = " + id + ";";
        requete(requete);
    }

    public static void actualiserDechetBDD(Dechet dechet, String instruction){
        int identifiantDepot = dechet.getIdentifiantDechet();
        String requete;
        if (instruction=="type"){
            requete = "UPDATE Dechet SET type = " + dechet.getType() + ";";
            requete(requete);
        }
        if (instruction=="contenir"){
            requete = "UPDATE contenir SET identifiantDepot = " + dechet.getContenir().getIdentifiantDepot() + ";";
            requete(requete);
        }
    }
}
