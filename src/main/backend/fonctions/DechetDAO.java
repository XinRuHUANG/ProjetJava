// DechetDAO.java
package main.backend.fonctions;

import main.outils.connexionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DechetDAO {
    public static void ajouterDechetBDD(Dechet d) throws Exception {
        Connection conn = DriverManager.getConnection(connexionSQL.url, connexionSQL.user, connexionSQL.password);
        try {
            // Création d'un statement
            Statement stmt = conn.createStatement();
            // Exécute l'insertion avec récupération automatique des clés générées
            stmt.executeUpdate(
                    "INSERT INTO dechet (typeDechet) VALUES ('" + d.getType() + "')",
                    Statement.RETURN_GENERATED_KEYS
            );

            // Récupérer la clé générée
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                d.setIdentifiantDechet(keys.getInt(1));
            } else {
                throw new IllegalStateException("Erreur : aucun ID généré pour le déchet !");
            }

            keys.close();
            stmt.close();
        } finally {
            conn.close();
        }
    }


    public static Dechet lireDechetBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT typeDechet FROM dechet WHERE identifiantDechet = " + id + ";",
                new ArrayList<>(Arrays.asList("typeDechet"))
        );
        if (rows.isEmpty()) {
            return null;
        }
        var r = rows.get(0);
        TypeDechetEnum.TypeDechet type = TypeDechetEnum.TypeDechet.valueOf(r.get("typeDechet"));
        return new Dechet(id, type, null, null);
    }

    public static void actualiserDechetBDD(Dechet d, String... champs) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String f : champs) {
            sb.append(f).append("='");
            switch(f) {
                case "typeDechet": sb.append(d.getType()); break;
                default: continue;
            }
            sb.append("',");
        }
        String setClause = sb.substring(0, sb.length() - 1);
        requete("UPDATE dechet SET " + setClause + " WHERE identifiantDechet = " + d.getIdentifiantDechet());
    }

    public static void supprimerDechetBDD(Dechet d) throws Exception {
        requete("DELETE FROM dechet WHERE identifiantDechet = " + d.getIdentifiantDechet());
    }

    public static List<Dechet> lireDechetsParDepot(int idDepot) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantDechet, typeDechet, identifiantPoubelleIntelligente " +
                        "FROM dechet WHERE identifiantDepot = " + idDepot + ";",
                new ArrayList<String>(Arrays.asList(
                        "identifiantDechet",
                        "typeDechet",
                        "identifiantPoubelleIntelligente"
                ))
        );
        var result = new ArrayList<Dechet>();
        for (var row : rows) {
            int id = Integer.parseInt(row.get("identifiantDechet"));
            var type = TypeDechetEnum.TypeDechet.valueOf(row.get("typeDechet"));
            Depot dpt = DepotDAO.lireDepotBDD(idDepot);
            PoubelleIntelligente pib = PoubelleIntelligenteDAO.lirePoubelleBDD(
                    Integer.parseInt(row.get("identifiantPoubelleIntelligente"))
            );
            result.add(new Dechet(id, type, dpt, pib));
        }
        return result;
    }
}
