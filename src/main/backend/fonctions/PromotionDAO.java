package main.backend.fonctions;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

import java.util.*;
import java.time.LocalDate;

public class PromotionDAO {

    /**
     * Insère une promotion en base et récupère son ID.
     */
    public static void ajouterPromotionBDD(Promotion promo) throws Exception {
        String sql = String.format(
                "INSERT INTO promotion (pourcentageRemise, pointsRequis) VALUES (%f, %f);",
                promo.getPourcentageRemise(),
                promo.getPointsRequis()
        );
        requete(sql);

        var rows = requeteAvecAffichage(
                "SELECT identifiantPromotion AS id\n" +
                        "  FROM promotion\n" +
                        " ORDER BY identifiantPromotion DESC\n" +
                        " LIMIT 1;",
                new ArrayList<>(Arrays.asList("id"))
        );
        if (rows.isEmpty()) {
            throw new Exception("Impossible de récupérer l'ID de la promotion insérée");
        }
        promo.setIdPromotion(Integer.parseInt(rows.get(0).get("id")));
    }

    /**
     * Lit une promotion complète (avec catégorie, contrat et utilisateurs).
     */
    public static Promotion lirePromotionBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT pourcentageRemise, pointsRequis\n" +
                        "  FROM promotion\n" +
                        " WHERE identifiantPromotion = " + id + ";",
                new ArrayList<>(Arrays.asList("pourcentageRemise", "pointsRequis"))
        );
        if (rows.isEmpty()) return null;

        var r = rows.get(0);
        float pr  = Float.parseFloat(r.get("pourcentageRemise"));
        float pts = Float.parseFloat(r.get("pointsRequis"));

        // 1) catégorie -> méthode dédiée
        CategorieDeProduits cat = CategorieDeProduitsDAO.lireCategorieBDDParPromotion(id);

        // 2) contrat qui définit cette promo (s'il y en a un seul)
        Contrat def = ContratDAO.lireContratParPromotion(id);

        // 3) utilisateurs qui ont utilisé cette promo
        Set<Utilisateur> utilisateurs = UtilisateurDAO.lireUtilisateursParPromotion(id);

        Promotion promo = new Promotion(id, pr, pts);
        promo.setConcerner(cat);
        promo.setDefinir(def);
        promo.setUtiliser(utilisateurs);
        return promo;
    }

    /**
     * Lit toutes les promotions existantes.
     */
    public static List<Promotion> lireToutesPromotionsBDD() throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantPromotion FROM promotion;",
                new ArrayList<>(Arrays.asList("identifiantPromotion"))
        );
        List<Promotion> list = new ArrayList<>();
        for (var r : rows) {
            int id = Integer.parseInt(r.get("identifiantPromotion"));
            Promotion p = lirePromotionBDD(id);
            if (p != null) list.add(p);
        }
        return list;
    }

    /**
     * Met à jour les colonnes spécifiées.
     */
    public static void actualiserPromotionBDD(Promotion promo, String... cols) throws Exception {
        if (cols.length == 0) return;
        StringBuilder sb = new StringBuilder("UPDATE promotion SET ");
        for (int i = 0; i < cols.length; i++) {
            String c = cols[i];
            sb.append(c).append(" = ");
            switch(c) {
                case "pourcentageRemise" -> sb.append(promo.getPourcentageRemise());
                case "pointsRequis"      -> sb.append(promo.getPointsRequis());
                default                  -> throw new IllegalArgumentException("Colonne inconnue: " + c);
            }
            if (i < cols.length - 1) sb.append(", ");
        }
        sb.append(" WHERE identifiantPromotion = ").append(promo.getIdPromotion()).append(";");
        requete(sb.toString());
    }

    /**
     * Supprime une promotion.
     */
    public static void supprimerPromotionBDD(Promotion promo) throws Exception {
        requete(
                "DELETE FROM promotion\n" +
                        " WHERE identifiantPromotion = " + promo.getIdPromotion() + ";"
        );
    }

    /**
     * Toutes les promos liées à une catégorie.
     */
    public static Set<Promotion> lirePromotionsParCategorie(int idCat) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantPromotion\n" +
                        "  FROM concerner\n" +
                        " WHERE identifiantCategorieDeProduits = " + idCat + ";",
                new ArrayList<>(Arrays.asList("identifiantPromotion"))
        );
        Set<Promotion> set = new HashSet<>();
        for (var r : rows) {
            set.add(lirePromotionBDD(Integer.parseInt(r.get("identifiantPromotion"))));
        }
        return set;
    }

    /**
     * La promo définie par un contrat donné.
     */
    public static Promotion lirePromotionParContrat(int idContrat) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT p.identifiantPromotion\n" +
                        "  FROM promotion p\n" +
                        "  JOIN contrat c ON p.identifiantPromotion = c.definir\n" +
                        " WHERE c.idContrat = " + idContrat + ";",
                new ArrayList<>(Arrays.asList("identifiantPromotion"))
        );
        if (rows.isEmpty()) return null;
        int idPromo = Integer.parseInt(rows.get(0).get("identifiantPromotion"));
        return lirePromotionBDD(idPromo);
    }
}
