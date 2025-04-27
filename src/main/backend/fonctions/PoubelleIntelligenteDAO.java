// PoubelleIntelligenteDAO.java
package main.backend.fonctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligenteDAO {
    public static void ajouterPoubelleIntelligenteBDD(PoubelleIntelligente p) throws Exception {
        requete(
                "INSERT INTO poubelleintelligente (emplacement, capaciteMaximale, typeDechet, poids, identifiantCentreDeTri) VALUES ('"
                        + p.getEmplacement() + "', "
                        + p.getCapaciteMaximale() + ", '"
                        + p.getType() + "', "
                        + p.getPoids() + ", "
                        + p.getGerer().getIdCentreDeTri()
                        + ");"
        );
        var rows = requeteAvecAffichage(
                "SELECT LAST_INSERT_ID() AS id;",
                new ArrayList<>(Arrays.asList("id"))
        );
        p.setIdentifiantPoubelle(Integer.parseInt(rows.get(0).get("id")));
    }

    public static void actualiserPoubelleIntelligenteBDD(PoubelleIntelligente p, String... champs) throws Exception {
        for (var champ : champs) {
            switch (champ) {
                case "poids" -> requete(
                        "UPDATE poubelleintelligente SET poids = "
                                + p.getPoids()
                                + " WHERE identifiantPoubelleIntelligente = " + p.getIdentifiantPoubelle() + ";"
                );
                default -> throw new IllegalArgumentException("Champ inconnu : " + champ);
            }
        }
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
                "SELECT emplacement, capaciteMaximale, typeDechet, poids, identifiantCentreDeTri FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = "
                        + id + ";",
                new ArrayList<>(Arrays.asList("emplacement", "capaciteMaximale", "typeDechet", "poids", "identifiantCentreDeTri"))
        );
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        CentreDeTri ct = CentreDeTriDAO.lireCentreDeTriBDD(Integer.parseInt(r.get("identifiantCentreDeTri")));
        return new PoubelleIntelligente(
                id,
                r.get("emplacement"),
                Float.parseFloat(r.get("capaciteMaximale")),
                TypeDechetEnum.TypeDechet.valueOf(r.get("typeDechet")),
                Float.parseFloat(r.get("poids")),
                ct,
                Set.of(), // associations à charger si nécessaire
                Set.of()
        );
    }

    // méthode d’association :
    public static Set<PoubelleIntelligente> lirePoubellesParCentre(int idCentre) { /*…*/ return Set.of(); }
    public static PoubelleIntelligente lirePoubelleBDD(int idPoubelle) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT emplacement, capaciteMaximale, typeDechet, poids, identifiantCentreDeTri " +
                        "FROM poubelleintelligente " +
                        "WHERE identifiantPoubelleIntelligente = " + idPoubelle + ";",
                new ArrayList<String>(Arrays.asList(
                        "emplacement",
                        "capaciteMaximale",
                        "typeDechet",
                        "poids",
                        "identifiantCentreDeTri"
                ))
        );
        var row = rows.get(0);
        String em    = row.get("emplacement");
        float capMax = Float.parseFloat(row.get("capaciteMaximale"));
        var type     = TypeDechetEnum.TypeDechet.valueOf(row.get("typeDechet"));
        float poids  = Float.parseFloat(row.get("poids"));
        int idCentre = Integer.parseInt(row.get("identifiantCentreDeTri"));
        CentreDeTri ct = CentreDeTriDAO.lireCentreDeTriBDD(idCentre);
        return new PoubelleIntelligente(idPoubelle, em, capMax, type, poids,
                ct, new HashSet<>(), new HashSet<>());
    }
}
