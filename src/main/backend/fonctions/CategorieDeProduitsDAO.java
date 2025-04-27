package main.backend.fonctions;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

import java.util.*;

public class CategorieDeProduitsDAO {

    public static void ajouterCategorieDeProduitsBDD(CategorieDeProduits cat) throws Exception {
        // INSERT
        requete(
                "INSERT INTO `CategorieDeProduits` (`nom`) VALUES ('"
                        + cat.getNom().replace("'", "''") + "');"
        );
        // On récupère l'ID tout juste créé
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCategorieDeProduits` " +
                        "FROM `CategorieDeProduits` " +
                        "WHERE `nom` = '" + cat.getNom().replace("'", "''") + "' " +
                        "ORDER BY `identifiantCategorieDeProduits` DESC LIMIT 1;",
                new ArrayList<>(List.of("identifiantCategorieDeProduits"))
        );
        int id = Integer.parseInt(rows.get(0).get("identifiantCategorieDeProduits"));
        cat.setIdentifiantCategorie(id);
    }

    public static CategorieDeProduits lireCategorieDeproduitsBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `nom` FROM `CategorieDeProduits` " +
                        "WHERE `identifiantCategorieDeProduits` = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        if (rows.isEmpty()) return null;
        String nom = rows.get(0).get("nom");
        Set<Promotion> promos    = PromotionDAO.lirePromotionsParCategorie(id);
        Set<Commerce>  commerces = CommerceDAO.lireCommercesParCategorie(id);
        return new CategorieDeProduits(id, nom, promos, commerces);
    }

    public static List<CategorieDeProduits> lireToutesCategoriesBDD() throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCategorieDeProduits` FROM `CategorieDeProduits`;",
                new ArrayList<>(List.of("identifiantCategorieDeProduits"))
        );
        var result = new ArrayList<CategorieDeProduits>();
        for (var r : rows) {
            int id = Integer.parseInt(r.get("identifiantCategorieDeProduits"));
            result.add(lireCategorieDeproduitsBDD(id));
        }
        return result;
    }

    public static void actualiserCategorieDeproduitsBDD(CategorieDeProduits cat, String... cols) throws Exception {
        if (cols.length == 0) return;
        StringBuilder sb = new StringBuilder("UPDATE `CategorieDeProduits` SET ");
        for (int i = 0; i < cols.length; i++) {
            String c = cols[i];
            sb.append("`").append(c).append("` = '")
                    .append(c.equals("nom") ? cat.getNom().replace("'", "''") : "")
                    .append("'");
            if (i < cols.length - 1) sb.append(", ");
        }
        sb.append(" WHERE `identifiantCategorieDeProduits` = ")
                .append(cat.getIdentifiantCategorie()).append(";");
        requete(sb.toString());
    }

    public static void supprimerCategorieDeproduitsBDD(CategorieDeProduits cat) throws Exception {
        requete(
                "DELETE FROM `CategorieDeProduits` " +
                        "WHERE `identifiantCategorieDeProduits` = "
                        + cat.getIdentifiantCategorie() + ";"
        );
    }

    public static Set<Promotion> lirePromotionsParCategorie(int idCat) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantPromotion` FROM `concerner` " +
                        "WHERE `identifiantCategorieDeProduits` = " + idCat + ";",
                new ArrayList<>(List.of("identifiantPromotion"))
        );
        var set = new HashSet<Promotion>();
        for (var r : rows) {
            int idPromo = Integer.parseInt(r.get("identifiantPromotion"));
            set.add(PromotionDAO.lirePromotionBDD(idPromo));
        }
        return set;
    }

    public static Set<CategorieDeProduits> lireCategoriesParPromotion(int idPromo) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCategorieDeProduits` FROM `concerner` " +
                        "WHERE `identifiantPromotion` = " + idPromo + ";",
                new ArrayList<>(List.of("identifiantCategorieDeProduits"))
        );
        var set = new HashSet<CategorieDeProduits>();
        for (var r : rows) {
            int idCat = Integer.parseInt(r.get("identifiantCategorieDeProduits"));
            set.add(lireCategorieDeproduitsBDD(idCat));
        }
        return set;
    }

    public static CategorieDeProduits lireCategorieBDD(int idCategorie) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `nom` FROM `CategorieDeProduits` " +
                        "WHERE `identifiantCategorieDeProduits` = " + idCategorie + ";",
                new ArrayList<>(List.of("nom"))
        );
        if (rows.isEmpty()) return null;
        String nom = rows.get(0).get("nom");
        return new CategorieDeProduits(idCategorie, nom, new HashSet<>(), new HashSet<>());
    }

    public static CategorieDeProduits lireCategorieBDDParPromotion(int idPromotion) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT c.`identifiantCategorieDeProduits`, c.`nom` " +
                        "FROM `CategorieDeProduits` c " +
                        "JOIN `concerner` ce ON c.`identifiantCategorieDeProduits` = ce.`identifiantCategorieDeProduits` " +
                        "WHERE ce.`identifiantPromotion` = " + idPromotion + ";",
                new ArrayList<>(List.of("identifiantCategorieDeProduits","nom"))
        );
        if (rows.isEmpty()) return null;
        var row = rows.get(0);
        int id   = Integer.parseInt(row.get("identifiantCategorieDeProduits"));
        String nom = row.get("nom");
        return new CategorieDeProduits(id, nom, new HashSet<>(), new HashSet<>());
    }

    public static Set<CategorieDeProduits> lireCategoriesParCommerce(int idCommerce) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT cp.`identifiantCategorieDeproduits`, cp.`nom` " +
                        "FROM `CategorieDeProduits` cp " +
                        "JOIN `proposer` p ON cp.`identifiantCategorieDeProduits` = p.`identifiantCategorieDeProduits` " +
                        "WHERE p.`identifiantCommerce` = " + idCommerce + ";",
                new ArrayList<>(List.of("identifiantCategorieDeProduits","nom"))
        );
        var result = new HashSet<CategorieDeProduits>();
        for (var row : rows) {
            int    id  = Integer.parseInt(row.get("identifiantCategorieDeProduits"));
            String nom = row.get("nom");
            result.add(new CategorieDeProduits(id, nom, new HashSet<>(), new HashSet<>()));
        }
        return result;
    }
}
