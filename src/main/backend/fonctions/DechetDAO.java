// DechetDAO.java
package main.backend.fonctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DechetDAO {
    public static void ajouterDechetBDD(Dechet d) throws Exception {
        requete("INSERT INTO dechet (type, identifiantDepot, identifiantPoubelleIntelligente) VALUES ('"
                + d.getType() + "'," + d.getContenir().getIdentifiantDepot() + "," + d.getStocker().getIdentifiantPoubelle() + ")");
        var rows = requeteAvecAffichage("SELECT LAST_INSERT_ID() AS id", new ArrayList<>(Arrays.asList("id")));
        d.setIdentifiantDechet(Integer.parseInt(rows.get(0).get("id")));
    }

    public static Dechet lireDechetBDD(int id) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("type","identifiantDepot","identifiantPoubelleIntelligente"));
        var rows = requeteAvecAffichage("SELECT type,identifiantDepot,identifiantPoubelleIntelligente FROM dechet WHERE identifiantDechet = " + id, cols);
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        Depot depot = DepotDAO.lireDepotBDD(Integer.parseInt(r.get("identifiantDepot")));
        PoubelleIntelligente pi = PoubelleIntelligenteDAO.lirePoubelleIntelligenteBDD(Integer.parseInt(r.get("identifiantPoubelleIntelligente")));
        return new Dechet(id, TypeDechetEnum.TypeDechet.valueOf(r.get("type")), depot, pi);
    }

    public static void actualiserDechetBDD(Dechet d, String... champs) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String f : champs) {
            sb.append(f).append("='");
            switch(f) {
                case "type": sb.append(d.getType()); break;
                default: continue;
            }
            sb.append("',");
        }
        String setClause = sb.substring(0, sb.length()-1);
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
