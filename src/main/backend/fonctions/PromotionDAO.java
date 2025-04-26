package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PromotionDAO extends Promotion {

    public PromotionDAO(int idPromotion, int pourcentageRemise, int pointsRequis) {
        super(idPromotion, pourcentageRemise, pointsRequis);
    }

    public static void ajouterPromotionBDD(Promotion promotion) throws SQLException {
        String requete = "SELECT MAX(identifiantPromotion) FROM promotion;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPromotion");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantPromotion");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        promotion.setIdPromotion(id);
        float pourcentageRemise = promotion.getPourcentageRemise();
        float pointsRequis = promotion.getPointsRequis();

        requete = "INSERT INTO promotion(identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (" +
                id + "," + pourcentageRemise + "," + pointsRequis + ");";
        requete(requete);

        if (promotion.getConcerner() != null) {
            requete = "INSERT INTO concerner(identifiantPromotion, identifiantCategorieDeProduits) VALUES (" +
                    id + "," + promotion.getConcerner().getIdentifiantCategorie() + ");";
            requete(requete);
        }

        if (promotion.getDefinir() != null) {
            requete = "INSERT INTO definir(identifiantPromotion, identifiantContrat) VALUES (" +
                    id + "," + promotion.getDefinir().getIdContrat() + ");";
            requete(requete);
        }

        if (promotion.getUtiliser() != null) {
            for (Utilisateur u : promotion.getUtiliser()) {
                requete = "INSERT INTO utiliser(identifiantUtilisateur, identifiantPromotion) VALUES (" +
                        u.getIdUtilisateur() + "," + id + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerPromotionBDD(Promotion promotion) {
        int id = promotion.getIdPromotion();
        String requete = "DELETE FROM utiliser WHERE identifiantPromotion = " + id + ";";
        requete(requete);
        requete = "DELETE FROM definir WHERE identifiantPromotion = " + id + ";";
        requete(requete);
        requete = "DELETE FROM concerner WHERE identifiantPromotion = " + id + ";";
        requete(requete);
        requete = "DELETE FROM promotion WHERE identifiantPromotion = " + id + ";";
        requete(requete);
    }

    public static void actualiserPromotionBDD(Promotion promotion, String instruction) {
        int identifiantPromotion = promotion.getIdPromotion();
        String requete;
        if (instruction.equals("pourcentageRemise")) {
            requete = "UPDATE promotion SET pourcentageRemise = " + promotion.getPourcentageRemise() + " WHERE identifiantPromotion = " + identifiantPromotion + ";";
            requete(requete);
        }
        if (instruction.equals("pointsRequis")) {
            requete = "UPDATE promotion SET pointsRequis = " + promotion.getPointsRequis() + " WHERE identifiantPromotion = " + identifiantPromotion + ";";
            requete(requete);
        }
        if (instruction.equals("concerner")) {
            requete = "DELETE FROM concerner WHERE identifiantPromotion = " + identifiantPromotion + ";";
            requete(requete);
            requete = "INSERT INTO concerner(identifiantPromotion, identifiantCategorieDeProduits) VALUES (" + identifiantPromotion + "," + promotion.getConcerner().getIdentifiantCategorie() + ");";
            requete(requete);
        }
        if (instruction.equals("definir")) {
            requete = "DELETE FROM definir WHERE identifiantPromotion = " + identifiantPromotion + ";";
            requete(requete);
            requete = "INSERT INTO definir(identifiantPromotion, identifiantContrat) VALUES (" + identifiantPromotion + "," + promotion.getDefinir().getIdContrat() + ");";
            requete(requete);
        }
        if (instruction.equals("utiliser")) {
            requete = "DELETE FROM utiliser WHERE identifiantPromotion = " + identifiantPromotion + ";";
            requete(requete);
            Set<Utilisateur> utilisateurs = promotion.getUtiliser();
            for (Utilisateur utilisateur : utilisateurs) {
                requete = "INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) VALUES (" + identifiantPromotion + "," + utilisateur.getIdUtilisateur() + ");";
                requete(requete);
            }
        }
    }
}
