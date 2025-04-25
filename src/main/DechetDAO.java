package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;
import static main.TypeDechetEnum.TypeDechet;

public class DechetDAO {

    public static void ajouterDechetBDD(Dechet dechet) {

        // Récupérer le dernier identifiant
        String requete = "SELECT MAX(identifiantDechet) FROM Dechet;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDechet");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantDechet")) + 1;

        //Récupérer les infos du déchet
        dechet.setIdentifiantDechet(id);
        TypeDechet type = dechet.getType(); // enum -> String
        Depot contenir = dechet.getContenir();
        int idDepot = contenir.getIdentifiantDepot();  // Obtenir l'identifiant du dépôt via la classe depot

        requete = "INSERT INTO Dechet(identifiantDechet, type, identifiantDepot) VALUES (" +
                id + "," + type + "," + idDepot + ");";
        requete(requete);

        requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) VALUES ("
                + id + "," + dechet.getContenir().getIdentifiantDepot() + ");";
        requete(requete);

        requete = "INSERT INTO stocker(identifiantDechet, identifiantPoubelleIntelligente) VALUES ("
                + id + "," + dechet.getStocker().getIdentifiantPoubelle() + ");";
        requete(requete);
        }

    public static void supprimerDechetBDD(Dechet dechet){
        int id = dechet.getIdentifiantDechet();
        //Suppression du déchet de la table contenir
        String requete = "DELETE FROM contenir WHERE identifiantDechet = " + id + ";";
        requete(requete);
        // Supprimer le déchet de la table Dechet
        requete = "DELETE FROM Dechet WHERE identifiantDechet = " + id + ";";
        requete(requete);
        //Supprimer le déchet de la table stocker
        requete = "DELETE FROM stocker WHERE identifiantDechet = " + id + ";";
        requete(requete);
    }

    public static void actualiserDechetBDD(Dechet dechet, String instruction){
        int identifiantDechet = dechet.getIdentifiantDechet();
        String requete;
        if (instruction=="type"){
            requete = "UPDATE Dechet SET type = " + dechet.getType() + ";";
            requete(requete);
        }
        if (instruction=="contenir"){
            requete = "UPDATE contenir SET identifiantDepot = " + dechet.getContenir().getIdentifiantDepot() + ";";
            requete(requete);
        }
        if (instruction=="stocker"){
            requete = "DELETE FROM stocker WHERE identifiantDechet = " + identifiantDechet + ";";
            requete(requete);
            requete = "INSERT INTO stocker(identifiantDechet, identifiantPoubelleIntelligente) VALUES ("
                    + identifiantDechet + "," + dechet.getStocker().getIdentifiantPoubelle() + ");";
            requete(requete);
            }
        }
}
