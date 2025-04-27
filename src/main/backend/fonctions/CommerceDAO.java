// CommerceDAO.java
package main.backend.fonctions;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CommerceDAO {
    /**
     * Crée un commerce, récupère son ID auto-généré,
     * puis insère ses associations en base.
     */
    public static void ajouterCommerceBDD(Commerce c) throws Exception {
        // 1) on insère d’abord le commerce
        requete("INSERT INTO commerce(nom) VALUES ('" + c.getNom() + "');");

        // 2) on récupère l'ID auto-généré
        var rows = requeteAvecAffichage(
                "SELECT LAST_INSERT_ID() AS id;",
                new ArrayList<>(Arrays.asList("id"))
        );
        if (rows.isEmpty()) {
            throw new IllegalStateException("Erreur : aucun ID généré pour le commerce !");
        }
        int idCom = Integer.parseInt(rows.get(0).get("id"));
        if (idCom <= 0) {
            throw new IllegalStateException("Erreur : ID généré invalide pour le commerce !");
        }
        c.setIdentifiantCommerce(idCom);

        // 3) on associe ce commerce aux catégories qu’il propose
        for (CategorieDeProduits cat : c.getProposer()) {
            requete(
                    "INSERT INTO proposer(identifiantCommerce, identifiantCategorieDeProduits) " +
                            "VALUES (" + idCom + ", " + cat.getIdentifiantCategorie() + ");"
            );
        }

        // 4) on associe ce commerce aux centres de tri + contrats
        List<CentreDeTri> centres = c.getCommercer();
        List<Contrat> contrats = c.getContrat();
        if (centres.size() != contrats.size()) {
            throw new IllegalArgumentException("Listes centres / contrats de tailles différentes");
        }
        for (int i = 0; i < centres.size(); i++) {
            int idCentre = centres.get(i).getIdCentreDeTri();
            int idContr = contrats.get(i).getIdContrat();
            requete(
                    "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                            "VALUES (" + idCentre + ", " + idCom + ", " + idContr + ");"
            );
        }
    }

    /**
     * Lit un commerce et toutes ses associations.
     */
    public static Commerce lireCommerceBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT nom FROM commerce WHERE identifiantCommerce = " + id + ";",
                new ArrayList<>(Arrays.asList("nom"))
        );
        if (rows.isEmpty()) return null;
        String nom = rows.get(0).get("nom");
        List<CentreDeTri> centres = CentreDeTriDAO.lireCentresParCommerce(id);
        List<Contrat>     contrats = ContratDAO.lireContratsParCommerce(id);
        Set<CategorieDeProduits> cats = CategorieDeProduitsDAO.lireCategoriesParCommerce(id);
        return new Commerce(id, nom, centres, contrats, cats);
    }

    /** l’implémentation « réelle » */
    public static Commerce lireCommerceFromBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT nom FROM Commerce WHERE identifiantCommerce = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        String nom = r.get("nom");
        return new Commerce(
                id,
                nom,
                CentreDeTriDAO.lireCentresParCommerce(id),
                ContratDAO.lireContratsParCommerce(id),
                CategorieDeProduitsDAO.lireCategoriesParCommerce(id)
        );
    }

    public static void actualiserCommerceBDD(Commerce c, String... cols) throws Exception {
        StringBuilder sb = new StringBuilder("UPDATE commerce SET ");
        for (int i = 0; i < cols.length; i++) {
            String col = cols[i];
            sb.append(col).append(" = '")
                    .append(col.equals("nom") ? c.getNom() : "")
                    .append("'");
            if (i < cols.length - 1) sb.append(", ");
        }
        sb.append(" WHERE identifiantCommerce = ").append(c.getIdentifiantCommerce()).append(";");
        requete(sb.toString());
    }

    /**
     * Supprime le commerce (cascade sur les associations).
     */
    public static void supprimerCommerceBDD(Commerce c) throws Exception {
        requete(
                "DELETE FROM commerce WHERE identifiantCommerce = "
                        + c.getIdentifiantCommerce() + ";"
        );
    }

    // Associations
    public static List<CentreDeTri> lireCentresParCommerce(int idComm) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantCentreDeTri FROM commercer WHERE identifiantCommerce = " + idComm + ";",
                new ArrayList<>(Arrays.asList("identifiantCentreDeTri"))
        );
        var list = new ArrayList<CentreDeTri>();
        for (var r : rows) {
            list.add(CentreDeTriDAO.lireCentreDeTriBDD(
                    Integer.parseInt(r.get("identifiantCentreDeTri"))));
        }
        return list;
    }

    public static List<Contrat> lireContratsParCommerce(int idComm) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantContrat FROM contrat WHERE identifiantCommerce = " + idComm + ";",
                new ArrayList<>(Arrays.asList("identifiantContrat"))
        );
        var list = new ArrayList<Contrat>();
        for (var r : rows) {
            list.add(ContratDAO.lireContratBDD(
                    Integer.parseInt(r.get("identifiantContrat"))));
        }
        return list;
    }

    public static Set<CategorieDeProduits> lireCategoriesParCommerce(int idCommerce) throws Exception {
        List<HashMap<String,String>> rows = requeteAvecAffichage(
                "SELECT cp.identifiantCategorieDeProduits, cp.nom " +
                        "FROM categoriedeproduits cp " +
                        "JOIN proposer pr ON cp.identifiantCategorieDeProduits = pr.identifiantCategorieDeProduits " +
                        "WHERE pr.identifiantCommerce = " + idCommerce + ";",
                new ArrayList<>(Arrays.asList(
                        "identifiantCategorieDeProduits",
                        "nom"
                ))
        );

        Set<CategorieDeProduits> result = new HashSet<>();
        for (var row : rows) {
            int    id  = Integer.parseInt(row.get("identifiantCategorieDeProduits"));
            String nom = row.get("nom");
            result.add(new CategorieDeProduits(
                    id,
                    nom,
                    new HashSet<>(), // concerner
                    new HashSet<>()  // proposer
            ));
        }
        return result;
    }

    public static List<Commerce> lireCommercesParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT c.identifiantCommerce, c.nom " +
                        "FROM commerce c " +
                        "JOIN commercer cm ON c.identifiantCommerce = cm.identifiantCommerce " +
                        "WHERE cm.identifiantCentreDeTri = " + idCentre + ";",
                new ArrayList<String>(Arrays.asList(
                        "identifiantCommerce",
                        "nom"
                ))
        );
        var result = new ArrayList<Commerce>();
        for (var row : rows) {
            int id = Integer.parseInt(row.get("identifiantCommerce"));
            result.add(new Commerce(id, row.get("nom"),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new HashSet<>()));
        }
        return result;
    }

    public static Set<Commerce> lireCommercesParCategorie(int idCategorie) throws Exception {
        List<HashMap<String,String>> rows = requeteAvecAffichage(
                "SELECT c.identifiantCommerce, c.nom " +
                        "FROM commerce c " +
                        "JOIN proposer p ON c.identifiantCommerce = p.identifiantCommerce " +
                        "WHERE p.identifiantCategorieDeProduits = " + idCategorie + ";",
                new ArrayList<String>(Arrays.asList(
                        "identifiantCommerce",
                        "nom"
                ))
        );

        Set<Commerce> result = new HashSet<Commerce>();
        for (java.util.Map<String,String> row : rows) {
            int    id   = Integer.parseInt(row.get("identifiantCommerce"));
            String nom  = row.get("nom");
            result.add(new Commerce(
                    id,
                    nom,
                    new ArrayList<CentreDeTri>(),    // on ne charge pas les centres ici
                    new ArrayList<Contrat>(),       // ni les contrats
                    new HashSet<CategorieDeProduits>() // ni les catégories
            ));
        }
        return result;
    }
}
