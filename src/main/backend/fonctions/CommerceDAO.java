package main.backend.fonctions;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommerceDAO {
    // À adapter selon ta config MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/projetjava";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";

    /**
     * Crée un commerce, récupère son ID auto-généré, puis insère ses associations.
     */
    public static void ajouterCommerceBDD(Commerce c) throws Exception {
        String sqlInsert = "INSERT INTO Commerce(nom) VALUES (?);";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, c.getNom());
            int affected = pst.executeUpdate();
            if (affected == 0) {
                throw new IllegalStateException("Erreur : aucun commerce inséré !");
            }
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setIdentifiantCommerce(rs.getInt(1));
                } else {
                    throw new IllegalStateException("Erreur : ID généré invalide pour le commerce !");
                }
            }

            int idCom = c.getIdentifiantCommerce();
            // 3) associer aux catégories
            for (CategorieDeProduits cat : c.getProposer()) {
                requete(
                        "INSERT INTO proposer(identifiantCommerce,identifiantCategorieDeProduits) VALUES (" +
                                idCom + "," + cat.getIdentifiantCategorie() + ");"
                );
            }

            // 4) associer aux centres de tri + contrats
            List<CentreDeTri> centres = c.getCommercer();
            List<Contrat> contrats = c.getContrat();
            if (centres.size() != contrats.size()) {
                throw new IllegalArgumentException("Listes centres / contrats de tailles différentes");
            }
            for (int i = 0; i < centres.size(); i++) {
                int idCentre = centres.get(i).getIdCentreDeTri();
                int idContr = contrats.get(i).getIdContrat();
                requete(
                        "INSERT INTO commercer(identifiantCentreDeTri,identifiantCommerce,identifiantContrat) " +
                                "VALUES (" + idCentre + "," + idCom + "," + idContr + ");"
                );
            }
        }
    }

    /**
     * Lit un commerce et toutes ses associations.
     */
    public static Commerce lireCommerceBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT nom FROM Commerce WHERE identifiantCommerce = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        if (rows.isEmpty()) return null;
        String nom = rows.get(0).get("nom");
        List<CentreDeTri> centres = CentreDeTriDAO.lireCentresParCommerce(id);
        List<Contrat> contrats = ContratDAO.lireContratsParCommerce(id);
        Set<CategorieDeProduits> cats = CategorieDeProduitsDAO.lireCategoriesParCommerce(id);
        return new Commerce(id, nom, centres, contrats, cats);
    }

    public static void actualiserCommerceBDD(Commerce c, String... cols) throws Exception {
        if (cols.length == 0) return;
        StringBuilder sb = new StringBuilder("UPDATE Commerce SET ");
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

    /** Supprime le commerce (cascade sur les associations). */
    public static void supprimerCommerceBDD(Commerce c) throws Exception {
        requete(
                "DELETE FROM Commerce WHERE identifiantCommerce = " + c.getIdentifiantCommerce() + ";"
        );
    }

    // ---- Associations ----

    public static List<CentreDeTri> lireCentresParCommerce(int idComm) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantCentreDeTri FROM commercer WHERE identifiantCommerce = " + idComm + ";",
                new ArrayList<>(List.of("identifiantCentreDeTri"))
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
                "SELECT identifiantContrat FROM commercer WHERE identifiantCommerce = " + idComm + ";",
                new ArrayList<>(List.of("identifiantContrat"))
        );
        var list = new ArrayList<Contrat>();
        for (var r : rows) {
            list.add(ContratDAO.lireContratBDD(
                    Integer.parseInt(r.get("identifiantContrat"))));
        }
        return list;
    }

    public static Set<CategorieDeProduits> lireCategoriesParCommerce(int idCat) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT cp.identifiantCategorieDeProduits, cp.nom " +
                        "FROM CategorieDeProduits cp " +
                        "JOIN proposer pr ON cp.identifiantCategorieDeProduits = pr.identifiantCategorieDeProduits " +
                        "WHERE pr.identifiantCommerce = " + idCat + ";",
                new ArrayList<>(List.of("identifiantCategorieDeProduits","nom"))
        );
        var result = new HashSet<CategorieDeProduits>();
        for (var row : rows) {
            int id  = Integer.parseInt(row.get("identifiantCategorieDeProduits"));
            String nom = row.get("nom");
            result.add(new CategorieDeProduits(id, nom, new HashSet<>(), new HashSet<>()));
        }
        return result;
    }

    /**
     * Lit tous les commerces proposant une catégorie donnée.
     */
    public static Set<Commerce> lireCommercesParCategorie(int idCategorie) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT c.identifiantCommerce, c.nom " +
                        "FROM Commerce c " +
                        "JOIN proposer p ON c.identifiantCommerce = p.identifiantCommerce " +
                        "WHERE p.identifiantCategorieDeProduits = " + idCategorie + ";",
                new ArrayList<>(List.of("identifiantCommerce","nom"))
        );
        var result = new HashSet<Commerce>();
        for (var row : rows) {
            int id   = Integer.parseInt(row.get("identifiantCommerce"));
            String nom = row.get("nom");
            result.add(new Commerce(
                    id,
                    nom,
                    new ArrayList<>(), // pas de centres chargés ici
                    new ArrayList<>(), // pas de contrats chargés ici
                    new HashSet<>()    // pas de catégories chargées ici
            ));
        }
        return result;
    }
    /**
     * Lit tous les commerces d'un centre de tri donné.
     */
    public static List<Commerce> lireCommercesParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT c.identifiantCommerce, c.nom " +
                        "FROM Commerce c " +
                        "JOIN commercer cm ON c.identifiantCommerce = cm.identifiantCommerce " +
                        "WHERE cm.identifiantCentreDeTri = " + idCentre + ";",
                new ArrayList<>(List.of("identifiantCommerce", "nom"))
        );
        var result = new ArrayList<Commerce>();
        for (var row : rows) {
            int id = Integer.parseInt(row.get("identifiantCommerce"));
            result.add(new Commerce(
                    id,
                    row.get("nom"),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new HashSet<>()
            ));
        }
        return result;
    }
}
