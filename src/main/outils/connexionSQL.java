package main.outils;

import java.sql.*;
import java.util.*;

public class connexionSQL {
    public static final String url      = "jdbc:mysql://localhost:3306/projetjava";
    public static final String user     = "root";
    public static final String password = "";

    public connexionSQL() { }

    /**
     * Donne une connexion directe à la base.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Exécute une requête d’action (INSERT/UPDATE/DELETE) ou un SELECT sans renvoyer de résultat.
     */
    public static void requete(String requete) {
        String trimmed = requete.trim().toUpperCase(Locale.ROOT);
        boolean isSelect = trimmed.startsWith("SELECT");

        try (
                Connection conn = getConnection();
                Statement stmt  = conn.createStatement()
        ) {
            if (isSelect) {
                try (ResultSet rs = stmt.executeQuery(requete)) {
                    // Résultat ignoré volontairement
                }
            } else {
                stmt.executeUpdate(requete);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pour un SELECT : renvoie la liste de HashMap[colonne→valeur] pour chaque ligne.
     * Pour un INSERT/UPDATE/DELETE : renvoie une liste à un seul élément
     * contenant la clé générée (attributs.get(0)) dans le champ indexé 1 du ResultSet.
     */
    public static List<HashMap<String, String>> requeteAvecAffichage(
            String requete,
            ArrayList<String> attributs
    ) throws SQLException {
        List<HashMap<String, String>> result = new ArrayList<>();
        String trimmed = requete.trim().toUpperCase(Locale.ROOT);

        try (
                Connection conn = getConnection();
                Statement stmt  = conn.createStatement()
        ) {
            if (trimmed.startsWith("SELECT")) {
                try (ResultSet rs = stmt.executeQuery(requete)) {
                    while (rs.next()) {
                        HashMap<String, String> ligne = new HashMap<>();
                        for (int i = 0; i < attributs.size(); i++) {
                            String cle = attributs.get(i);
                            String val = rs.getString(i + 1);
                            ligne.put(cle, val);
                        }
                        result.add(ligne);
                    }
                }
            } else {
                stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    String keyName = attributs.isEmpty() ? "generated_key" : attributs.get(0);
                    if (keys.next()) {
                        HashMap<String, String> ligne = new HashMap<>();
                        ligne.put(keyName, keys.getString(1));
                        result.add(ligne);
                    }
                }
            }
        }

        return result;
    }
}
