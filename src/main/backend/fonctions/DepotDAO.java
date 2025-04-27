// DepotDAO.java
package main.backend.fonctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DepotDAO {
    public static void ajouterDepotBDD(Depot dp) throws Exception {
        requete("INSERT INTO depot (date,heure,points) VALUES ('" + dp.getDate() + "','" + dp.getHeure() + "'," + dp.getPoints() + ")");
        var rows = requeteAvecAffichage("SELECT LAST_INSERT_ID() AS id", new ArrayList<>(Arrays.asList("id")));
        dp.setIdentifiantDepot(Integer.parseInt(rows.get(0).get("id")));
    }

    public static void actualiserDepotBDD(Depot dp, String... champs) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String f : champs) {
            sb.append(f).append("=");
            switch(f) {
                case "points": sb.append(dp.getPoints()); break;
                default: continue;
            }
            sb.append(",");
        }
        String setClause = sb.substring(0,sb.length()-1);
        requete("UPDATE depot SET " + setClause + " WHERE identifiantDepot = " + dp.getIdentifiantDepot());
    }

    public static Depot lireDepotBDD(int idDepot) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT date, heure, points, identifiantUtilisateur, identifiantPoubelleIntelligente " +
                        "FROM depot WHERE identifiantDepot = " + idDepot + ";",
                new ArrayList<String>(Arrays.asList(
                        "date",
                        "heure",
                        "points",
                        "identifiantUtilisateur",
                        "identifiantPoubelleIntelligente"
                ))
        );
        var row = rows.get(0);
        LocalDate d = LocalDate.parse(row.get("date"));
        LocalTime h = LocalTime.parse(row.get("heure"));
        float pts   = Float.parseFloat(row.get("points"));
        var depot = new Depot(idDepot, d, h, pts);
        // on peut charger utilisateur, poubelle, déchets… si besoin
        return depot;
    }

    public static void supprimerDepotBDD(Depot dp) throws Exception {
        requete("DELETE FROM depot WHERE identifiantDepot = " + dp.getIdentifiantDepot());
    }

    // associations
    public static List<Dechet> lireDechetsParDepot(int idDepot) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("identifiantDechet"));
        var rows = requeteAvecAffichage("SELECT identifiantDechet FROM contenir WHERE identifiantDepot = " + idDepot, cols);
        List<Dechet> list = new ArrayList<>();
        for (var r : rows) list.add(DechetDAO.lireDechetBDD(Integer.parseInt(r.get("identifiantDechet"))));
        return list;
    }

    public static List<Utilisateur> lireUtilisateurParDepot(int idDepot) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("identifiantUtilisateur"));
        var rows = requeteAvecAffichage("SELECT identifiantUtilisateur FROM deposer WHERE identifiantDepot = " + idDepot, cols);
        List<Utilisateur> list = new ArrayList<>();
        for (var r : rows) list.add(UtilisateurDAO.lireUtilisateurBDD(Integer.parseInt(r.get("identifiantUtilisateur"))));
        return list;
    }


}

