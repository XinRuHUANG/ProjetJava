package main.backend.fonctions;

import main.outils.connexionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DepotDAO {

    // Dans DepotDAO.java
    public static void ajouterDepotBDD(Depot dp) throws Exception {
        // 1) On construit la requête en concaténant : dp.getPoints() produira "0.0" (avec un point)
        String sql =
                "INSERT INTO depot (date, heure, points) VALUES ('"
                        + dp.getDate().toString()       // "YYYY-MM-DD"
                        + "','"
                        + dp.getHeure().withNano(0).toString()  // "HH:MM:SS"
                        + "',"
                        + dp.getPoints()               // Float.toString() → "0.0"
                        + ")";

        // 2) On ouvre directement une connexion JDBC pour récupérer l'ID généré
        try (
                Connection conn = DriverManager.getConnection(
                        connexionSQL.url,
                        connexionSQL.user,
                        connexionSQL.password
                );
                Statement stmt = conn.createStatement()
        ) {
            // Exécution + demande de clés générées
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dp.setIdentifiantDepot(rs.getInt(1));
                } else {
                    throw new IllegalStateException(
                            "Erreur : aucun ID généré pour le dépôt !");
                }
            }
        }
    }


    public static void actualiserDepotBDD(Depot dp, String... champs) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String f : champs) {
            sb.append(f).append("=");

            switch (f) {
                case "points":
                    sb.append(dp.getPoints());
                    break;
                case "date":
                    sb.append("'").append(dp.getDate()).append("'");
                    break;
                case "heure":
                    sb.append("'").append(dp.getHeure()).append("'");
                    break;
                default:
                    continue; // continue marche seulement en syntaxe normale
            }
            sb.append(",");
        }
        String setClause = sb.substring(0, sb.length() - 1);
        requete("UPDATE depot SET " + setClause + " WHERE identifiantDepot = " + dp.getIdentifiantDepot());
    }

    public static Depot lireDepotBDD(int idDepot) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT date, heure, points FROM depot WHERE identifiantDepot = " + idDepot + ";",
                new ArrayList<>(Arrays.asList("date", "heure", "points"))
        );
        if (rows.isEmpty()) {
            return null;
        }
        var row = rows.get(0);
        LocalDate date = LocalDate.parse(row.get("date"));
        LocalTime heure = LocalTime.parse(row.get("heure"));
        float points = Float.parseFloat(row.get("points"));
        return new Depot(idDepot, date, heure, points);
    }

    public static void supprimerDepotBDD(Depot dp) throws Exception {
        requete("DELETE FROM depot WHERE identifiantDepot = " + dp.getIdentifiantDepot());
    }

    // associations
    public static List<Dechet> lireDechetsParDepot(int idDepot) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("identifiantDechet"));
        var rows = requeteAvecAffichage(
                "SELECT identifiantDechet FROM contenir WHERE identifiantDepot = " + idDepot,
                cols
        );
        List<Dechet> list = new ArrayList<>();
        for (var r : rows) {
            list.add(DechetDAO.lireDechetBDD(Integer.parseInt(r.get("identifiantDechet"))));
        }
        return list;
    }

    public static List<Utilisateur> lireUtilisateurParDepot(int idDepot) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("identifiantUtilisateur"));
        var rows = requeteAvecAffichage(
                "SELECT identifiantUtilisateur FROM posseder WHERE identifiantDepot = " + idDepot,
                cols
        );
        List<Utilisateur> list = new ArrayList<>();
        for (var r : rows) {
            list.add(UtilisateurDAO.lireUtilisateurBDD(Integer.parseInt(r.get("identifiantUtilisateur"))));
        }
        return list;
    }

    /**
     * Ajoute un déchet à un dépôt dans la table contenir.
     */
    public static void ajouterDechetDepot(Depot depot, Dechet dechet) throws Exception {
        requete(
                "INSERT INTO contenir (identifiantDepot, identifiantDechet) VALUES (" +
                        depot.getIdentifiantDepot() + ", " +
                        dechet.getIdentifiantDechet() + ");"
        );
    }

    /**
     * Supprime un déchet spécifique d'un dépôt dans la table contenir.
     */
    public static void supprimerDechetDepot(Depot depot, Dechet dechet) throws Exception {
        requete(
                "DELETE FROM contenir WHERE identifiantDepot = " + depot.getIdentifiantDepot() +
                        " AND identifiantDechet = " + dechet.getIdentifiantDechet() + ";"
        );
    }

    /**
     * Vide complètement un dépôt de tous ses déchets dans la table contenir.
     */
    public static void viderDepot(Depot depot) throws Exception {
        requete(
                "DELETE FROM contenir WHERE identifiantDepot = " + depot.getIdentifiantDepot() + ";"
        );
    }

    /**
     * Liste tous les déchets associés à un dépôt.
     */
    public static List<Dechet> listerDechets(Depot depot) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantDechet FROM contenir WHERE identifiantDepot = " + depot.getIdentifiantDepot() + ";",
                new ArrayList<>(Arrays.asList("identifiantDechet"))
        );

        List<Dechet> liste = new ArrayList<>();
        for (var row : rows) {
            int idDechet = Integer.parseInt(row.get("identifiantDechet"));
            Dechet d = DechetDAO.lireDechetBDD(idDechet);
            if (d != null) liste.add(d);
        }
        return liste;
    }
}
