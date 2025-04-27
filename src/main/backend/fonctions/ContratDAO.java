package main.backend.fonctions;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ContratDAO {
    public static void ajouterContratBDD(Contrat c) throws Exception {
        requete("INSERT INTO contrat (dateDebut, dateFin, clauses, identifiantCentreDeTri, identifiantCommerce) " +
                "VALUES ('" + c.getDateDebut() + "','" + c.getDateFin() + "','" + c.getClauses() + "'," +
                c.getCommercer().getIdCentreDeTri() + "," + c.getCommerce().getIdentifiantCommerce() + ")");
        var rows = requeteAvecAffichage("SELECT LAST_INSERT_ID() AS id", new ArrayList<>(Arrays.asList("id")));
        c.setIdContrat(Integer.parseInt(rows.get(0).get("id")));
        // associations
        Promotion def = c.getDefinir();
        if (def != null) {
            requete("INSERT INTO definir (identifiantContrat, identifiantPromotion) VALUES (" + c.getIdContrat() + "," + def.getIdPromotion() + ")");
        }
    }


    /**
     * Lit le contrat associé à une promotion donnée.
     */
    public static Contrat lireContratParPromotion(int idPromotion) throws Exception {
        // On récupère l'identifiant du contrat lié à cette promotion
        List<HashMap<String,String>> rows = requeteAvecAffichage(
                "SELECT identifiantContrat FROM contrat WHERE identifiantPromotion = " + idPromotion + ";",
                new ArrayList<>(Arrays.asList("identifiantContrat"))
        );
        if (rows.isEmpty()) {
            return null;
        }
        int idContrat = Integer.parseInt(rows.get(0).get("identifiantContrat"));
        // On délègue ensuite à la méthode de lecture standard
        return lireContratBDD(idContrat);
    }

    public static Contrat lireContratBDD(int id) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("dateDebut","dateFin","clauses","identifiantCentreDeTri","identifiantCommerce"));
        var rows = requeteAvecAffichage("SELECT dateDebut,dateFin,clauses,identifiantCentreDeTri,identifiantCommerce FROM contrat WHERE identifiantContrat = " + id, cols);
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        CentreDeTri ct = CentreDeTriDAO.lireCentreDeTriBDD(Integer.parseInt(r.get("identifiantCentreDeTri")));
        Commerce cm = CommerceDAO.lireCommerceBDD(Integer.parseInt(r.get("identifiantCommerce")));
        Contrat c = new Contrat(id,
                LocalDate.parse(r.get("dateDebut")),
                LocalDate.parse(r.get("dateFin")),
                r.get("clauses"), ct, cm, null);
        // définir
        Promotion p = PromotionDAO.lirePromotionParContrat(id);
        c.setDefinir(p);
        return c;
    }

    public static void actualiserContratBDD(Contrat c, String... champs) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String f : champs) {
            sb.append(f).append("='");
            switch(f) {
                case "dateDebut": sb.append(c.getDateDebut()); break;
                case "dateFin": sb.append(c.getDateFin()); break;
                case "clauses": sb.append(c.getClauses()); break;
                default: continue;
            }
            sb.append("',");
        }
        String setClause = sb.substring(0, sb.length()-1);
        requete("UPDATE contrat SET " + setClause + " WHERE identifiantContrat = " + c.getIdContrat());
    }

    public static void supprimerContratBDD(Contrat c) throws Exception {
        requete("DELETE FROM contrat WHERE identifiantContrat = " + c.getIdContrat());
    }

    // association
    public static Promotion readPromotionParContrat(int idContrat) throws Exception {
        var cols = new ArrayList<>(Arrays.asList("identifiantPromotion"));
        var rows = requeteAvecAffichage("SELECT identifiantPromotion FROM definir WHERE identifiantContrat = " + idContrat, cols);
        if (rows.isEmpty()) return null;
        return PromotionDAO.lirePromotionBDD(Integer.parseInt(rows.get(0).get("identifiantPromotion")));
    }

    public static List<Contrat> lireContratsParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantContrat, dateDebut, dateFin, clauses, identifiantCommerce " +
                        "FROM contrat WHERE identifiantCentreDeTri = " + idCentre + ";",
                new ArrayList<String>(Arrays.asList(
                        "identifiantContrat",
                        "dateDebut",
                        "dateFin",
                        "clauses",
                        "identifiantCommerce"
                ))
        );
        var result = new ArrayList<Contrat>();
        for (var row : rows) {
            int id    = Integer.parseInt(row.get("identifiantContrat"));
            LocalDate d1 = LocalDate.parse(row.get("dateDebut"));
            LocalDate d2 = LocalDate.parse(row.get("dateFin"));
            String clauses = row.get("clauses");
            Commerce cm = CommerceDAO.lireCommerceBDD(
                    Integer.parseInt(row.get("identifiantCommerce")));
            CentreDeTri ct = CentreDeTriDAO.lireCentreDeTriBDD(idCentre);
            Promotion pr = PromotionDAO.lirePromotionParContrat(id);
            result.add(new Contrat(id, d1, d2, clauses, ct, cm, pr));
        }
        return result;
    }

    /**
     * Renvoie tous les contrats pour un commerce donné.
     */
    public static List<Contrat> lireContratsParCommerce(int idCommerce) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantContrat, dateDebut, dateFin, clauses, identifiantCentreDeTri " +
                        "FROM contrat WHERE identifiantCommerce = " + idCommerce + ";",
                new ArrayList<String>(Arrays.asList(
                        "identifiantContrat",
                        "dateDebut",
                        "dateFin",
                        "clauses",
                        "identifiantCentreDeTri"
                ))
        );
        var result = new ArrayList<Contrat>();
        for (var row : rows) {
            int id    = Integer.parseInt(row.get("identifiantContrat"));
            LocalDate d1 = LocalDate.parse(row.get("dateDebut"));
            LocalDate d2 = LocalDate.parse(row.get("dateFin"));
            String clauses = row.get("clauses");
            CentreDeTri ct = CentreDeTriDAO.lireCentreDeTriBDD(
                    Integer.parseInt(row.get("identifiantCentreDeTri")));
            Commerce cm = CommerceDAO.lireCommerceBDD(idCommerce);
            Promotion pr = PromotionDAO.lirePromotionParContrat(id);
            result.add(new Contrat(id, d1, d2, clauses, ct, cm, pr));
        }
        return result;
    }
}

