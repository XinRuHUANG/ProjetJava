package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.DepotDAO.actualiserDepotBDD;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PromotionDAO extends Promotion{

    public PromotionDAO(int idPromotion, int pourcentageRemise, int pointsRequis) {
        super(idPromotion,pourcentageRemise,pointsRequis);
    }

    // Ajouter une promotion
    public static void ajouterPromotionBDD(Promotion promotion) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantPromotion) FROM Promotion";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPromotion");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantPromotion")) + 1;

        //Récupération des infos
        promotion.setIdPromotion(id);
        float pourcentageRemise = promotion.getPourcentageRemise();
        float pointsRequis = promotion.getPointsRequis();
        //Ajout dans la table Promotion
        requete = "INSERT INTO Promotion(identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (" +
                id + "," + pourcentageRemise + "," + pointsRequis + ");";
        requete(requete);
        //Ajout dans la table concerner
        requete = "INSERT INTO concerner(identifiantPromotion, identifiantCategorieDeProduits) VALUES (" +
                id + "," + promotion.getConcerner().getIdentifiantCategorie() + ");";
        requete(requete);
        //Ajout dans la table definir
        requete = "INSERT INTO concerner(identifiantPromotion, identifiantContrat) VALUES (" +
                id + "," + promotion.getDefinir().getIdContrat() + ");";
        requete(requete);
        //Ajout dans la table utiliser
        Set<Utilisateur> utiliser = promotion.getUtiliser();
        for(Utilisateur utilisateur : utiliser) {
            requete = "INSERT INTO utiliser(identifiantUtilisateur, identifiantPromotion) VALUES (" +
                    utilisateur.getIdUtilisateur() + "," + id + ");";
            requete(requete);
        }
    }

    // Supprimer une promotion
    public static void supprimerPromotionBDD(Promotion promotion) {
        int id = promotion.getIdPromotion();
        String requete = "DELETE FROM utiliser WHERE identifiantPromotion = " + id + ");";
        requete(requete);
        requete = "DELETE FROM definir WHERE identifiantPromotion = " + id + ");";
        requete(requete);
        requete = "DELETE FROM concerner WHERE identifiantPromotion = " + id + ");";
        requete(requete);
        requete = "DELETE FROM Promotion WHERE identifiantPromotion = " + id + ");";
        requete(requete);
    }

    public static void actualiserPromotionBDD(Promotion promotion, String instruction){
        int identifiantPromotion = promotion.getIdPromotion();
        String requete;
        if (instruction=="pourcentageRemise"){
            requete = "UPDATE Promotion SET pourcentageRemise = " + promotion.getPourcentageRemise() + ";";
            requete(requete);
        }
        if (instruction=="pointsRequis"){
            requete = "UPDATE Promotion SET pointsRequis = " + promotion.getPointsRequis() + ";";
            requete(requete);
        }
        if (instruction=="concerner"){
            requete = "DELETE FROM concerner WHERE identifiantCategorieDeProduits = "
                    + promotion.getConcerner().getIdentifiantCategorie() + ";";
            requete(requete);
            requete = "INSERT INTO concerner(identifiantPromotion, identifiantCategorieDeProduits) VALUES (" + identifiantPromotion + ","
                    + promotion.getConcerner().getIdentifiantCategorie() + ");";
            requete(requete);
        }
        if (instruction=="definir"){
            requete = "DELETE FROM definir WHERE identifiantContrat = "
                    + promotion.getDefinir().getIdContrat() + ";";
            requete(requete);
            requete = "INSERT INTO definir (identifiantPromotion, identifiantContrat) VALUES (" + identifiantPromotion + ","
                    + promotion.getDefinir().getIdContrat() + ");";
            requete(requete);
        }
        if (instruction=="utiliser"){
            requete = "DELETE FROM utiliser WHERE identifiantPromotion = " + identifiantPromotion + ");";
            requete(requete);
            Set<Utilisateur> utilisateurs = promotion.getUtiliser();
            for(Utilisateur utilisateur : utilisateurs){
                requete = "INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) VALUES (" + identifiantPromotion + "," + utilisateur.getIdUtilisateur() + ");";
                requete(requete);
            }
        }
    }
}
