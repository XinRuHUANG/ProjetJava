package main.backend.fonctions;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

import java.util.*;

public class CentreDeTriDAO {

    public static void ajouterCentreDeTriBDD(CentreDeTri ct) throws Exception {
        // INSERT
        requete(
                "INSERT INTO `CentreDeTri` (`nom`,`adresse`) VALUES ('"
                        + ct.getNom().replace("'", "''") + "','"
                        + ct.getAdresse().replace("'", "''") + "');"
        );
        // récupérer l’ID créé
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCentreDeTri` " +
                        "FROM `CentreDeTri` " +
                        "WHERE `nom` = '" + ct.getNom().replace("'", "''") + "' " +
                        "ORDER BY `identifiantCentreDeTri` DESC LIMIT 1;",
                new ArrayList<>(List.of("identifiantCentreDeTri"))
        );
        int id = Integer.parseInt(rows.get(0).get("identifiantCentreDeTri"));
        ct.setIdCentreDeTri(id);
    }

    public static CentreDeTri lireCentreDeTriBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `nom`,`adresse` FROM `CentreDeTri` " +
                        "WHERE `identifiantCentreDeTri` = " + id + ";",
                new ArrayList<>(List.of("nom","adresse"))
        );
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        String nom     = r.get("nom");
        String adresse = r.get("adresse");

        // assoc
        Set<PoubelleIntelligente> poubelles = PoubelleIntelligenteDAO.lirePoubellesParCentre(id);
        List<Commerce>           commerces = CommerceDAO.lireCommercesParCentre(id);
        List<Contrat>            contrats  = ContratDAO.lireContratsParCentre(id);

        return new CentreDeTri(id, nom, adresse, poubelles, commerces, contrats);
    }

    public static List<CentreDeTri> lireTousCentresDeTriBDD() throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCentreDeTri` FROM `CentreDeTri`;",
                new ArrayList<>(List.of("identifiantCentreDeTri"))
        );
        List<CentreDeTri> result = new ArrayList<>();
        for (var r : rows) {
            int id = Integer.parseInt(r.get("identifiantCentreDeTri"));
            result.add(lireCentreDeTriBDD(id));
        }
        return result;
    }

    public static void actualiserCentreBDD(CentreDeTri ct, String... cols) throws Exception {
        if (cols.length == 0) return;
        StringBuilder sb = new StringBuilder("UPDATE `CentreDeTri` SET ");
        for (int i = 0; i < cols.length; i++) {
            String c = cols[i];
            sb.append("`").append(c).append("` = ");
            switch (c) {
                case "nom" -> sb.append("'").append(ct.getNom().replace("'", "''")).append("'");
                case "adresse" -> sb.append("'").append(ct.getAdresse().replace("'", "''")).append("'");
                default -> throw new IllegalArgumentException("Colonne inconnue : " + c);
            }
            if (i < cols.length - 1) sb.append(", ");
        }
        sb.append(" WHERE `identifiantCentreDeTri` = ").append(ct.getIdCentreDeTri()).append(";");
        requete(sb.toString());
    }

    public static void supprimerCentreBDD(CentreDeTri ct) throws Exception {
        requete(
                "DELETE FROM `CentreDeTri` " +
                        "WHERE `identifiantCentreDeTri` = " + ct.getIdCentreDeTri() + ";"
        );
    }

    public static Set<PoubelleIntelligente> lirePoubellesParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantPoubelleIntelligente` FROM `gerer` " +
                        "WHERE `identifiantCentreDeTri` = " + idCentre + ";",
                new ArrayList<>(List.of("identifiantPoubelleIntelligente"))
        );
        Set<PoubelleIntelligente> set = new HashSet<>();
        for (var r : rows) {
            int idP = Integer.parseInt(r.get("identifiantPoubelleIntelligente"));
            set.add(PoubelleIntelligenteDAO.lirePoubelleBDD(idP));
        }
        return set;
    }

    public static List<Commerce> lireCommercesParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCommerce` FROM `commercer` " +
                        "WHERE `identifiantCentreDeTri` = " + idCentre + ";",
                new ArrayList<>(List.of("identifiantCommerce"))
        );
        List<Commerce> list = new ArrayList<>();
        for (var r : rows) {
            int idC = Integer.parseInt(r.get("identifiantCommerce"));
            list.add(CommerceDAO.lireCommerceBDD(idC));
        }
        return list;
    }

    public static List<Contrat> lireContratsParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantContrat` FROM `commercer` " +
                        "WHERE `identifiantCentreDeTri` = " + idCentre + ";",
                new ArrayList<>(List.of("identifiantContrat"))
        );
        List<Contrat> list = new ArrayList<>();
        for (var r : rows) {
            int idCt = Integer.parseInt(r.get("identifiantContrat"));
            list.add(ContratDAO.lireContratBDD(idCt));
        }
        return list;
    }

    public static List<CentreDeTri> lireCentresParCommerce(int idCommerce) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT `identifiantCentreDeTri` " +
                        "FROM `commercer` " +
                        "WHERE `identifiantCommerce` = " + idCommerce + ";",
                new ArrayList<>(List.of("identifiantCentreDeTri"))
        );
        List<CentreDeTri> result = new ArrayList<>();
        for (var r : rows) {
            int idCt = Integer.parseInt(r.get("identifiantCentreDeTri"));
            result.add(lireCentreDeTriBDD(idCt));
        }
        return result;
    }

}
