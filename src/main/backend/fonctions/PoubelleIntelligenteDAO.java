// PoubelleIntelligenteDAO.java
package main.backend.fonctions;

import main.outils.connexionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligenteDAO {
    public static void ajouterPoubelleIntelligenteBDD(PoubelleIntelligente p) throws Exception {
        String sql = "INSERT INTO poubelleintelligente (emplacement, capaciteMaximale, typeDechet, poids) VALUES ('"
                + p.getEmplacement() + "', "
                + p.getCapaciteMaximale() + ", '"
                + p.getType() + "', "
                + p.getPoids()
                + ");";

        // Exécute l'insertion et récupère l'ID généré
        try (
                Connection conn = DriverManager.getConnection(
                        connexionSQL.url, connexionSQL.user, connexionSQL.password);
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setIdentifiantPoubelle(rs.getInt(1));
                } else {
                    throw new IllegalStateException("Erreur : aucun ID généré pour la poubelle !");
                }
            }
        }
    }


    public static void actualiserPoubelleIntelligenteBDD(PoubelleIntelligente p, String... champs) throws Exception {
        if (champs.length == 0) return;
        StringBuilder sb = new StringBuilder("UPDATE poubelleintelligente SET ");
        for (int i = 0; i < champs.length; i++) {
            String champ = champs[i];
            sb.append(champ).append(" = ");
            switch (champ) {
                case "emplacement" -> sb.append("'").append(p.getEmplacement().replace("'", "''")).append("'");
                case "capaciteMaximale" -> sb.append(p.getCapaciteMaximale());
                case "typeDechet" -> sb.append("'").append(p.getType().name()).append("'");
                case "poids" -> sb.append(p.getPoids());
                default -> throw new IllegalArgumentException("Champ inconnu pour la Poubelle : " + champ);
            }
            if (i < champs.length - 1) sb.append(", ");
        }
        sb.append(" WHERE identifiantPoubelleIntelligente = ").append(p.getIdentifiantPoubelle()).append(";");
        requete(sb.toString());
    }


    public static void supprimerPoubelleIntelligenteBDD(PoubelleIntelligente p) throws Exception {
        requete(
                "DELETE FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = "
                        + p.getIdentifiantPoubelle()
                        + ";"
        );
    }

    public static PoubelleIntelligente lirePoubelleIntelligenteBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT emplacement, capaciteMaximale, typeDechet, poids FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = "
                        + id + ";",
                new ArrayList<>(Arrays.asList("emplacement", "capaciteMaximale", "typeDechet", "poids"))
        );
        if (rows.isEmpty()) return null;
        var r = rows.get(0);

        return new PoubelleIntelligente(
                id,
                r.get("emplacement"),
                Float.parseFloat(r.get("capaciteMaximale")),
                TypeDechetEnum.TypeDechet.valueOf(r.get("typeDechet")),
                Float.parseFloat(r.get("poids")),
                null, // pas de CentreDeTri directement dans la table poubelleintelligente
                Set.of(), // associations à charger si besoin
                Set.of()
        );
    }


    // méthode d’association :
    public static Set<PoubelleIntelligente> lirePoubellesParCentre(int idCentre) { /*…*/ return Set.of(); }
    public static PoubelleIntelligente lirePoubelleBDD(int idPoubelle) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT emplacement, capaciteMaximale, typeDechet, poids " +
                        "FROM poubelleintelligente " +
                        "WHERE identifiantPoubelleIntelligente = " + idPoubelle + ";",
                new ArrayList<>(Arrays.asList(
                        "emplacement",
                        "capaciteMaximale",
                        "typeDechet",
                        "poids"
                ))
        );
        if (rows.isEmpty()) return null;
        var row = rows.get(0);
        String em = row.get("emplacement");
        float capMax = Float.parseFloat(row.get("capaciteMaximale"));
        var type = TypeDechetEnum.TypeDechet.valueOf(row.get("typeDechet"));
        float poids = Float.parseFloat(row.get("poids"));

        return new PoubelleIntelligente(
                idPoubelle,
                em,
                capMax,
                type,
                poids,
                null, // pas de CentreDeTri directement en base
                new HashSet<>(), // Set de dépôts jetés
                new HashSet<>()  // Set de dépôts stockés
        );
    }

}
