package main.backend.fonctions;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.sql.Statement;

public class ContratDAO {

    public static void ajouterContratBDD(Contrat contrat) throws Exception {
        var conn = main.outils.connexionSQL.getConnection();
        try {
            var stmt = conn.createStatement();
            stmt.executeUpdate(
                    String.format(
                            "INSERT INTO contrat (dateDebut, dateFin, clauses) VALUES ('%s', '%s', '%s')",
                            contrat.getDateDebut(),
                            contrat.getDateFin(),
                            contrat.getClauses()
                    ),
                    Statement.RETURN_GENERATED_KEYS // attention ici sans java.sql devant
            );

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                contrat.setIdContrat(rs.getInt(1));
            } else {
                throw new IllegalStateException("Erreur : Aucun ID généré pour le contrat !");
            }

            rs.close();
            stmt.close();
        } finally {
            conn.close();
        }
    }


    public static void lierPromotionContrat(Contrat contrat, Promotion promotion) throws Exception {
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion est null !");
        }
        if (contrat.getIdContrat() < 0) {
            throw new IllegalStateException("Contrat non enregistré en base !");
        }
        if (promotion.getIdPromotion() < 0) {
            throw new IllegalStateException("Promotion non enregistrée en base !");
        }

        requete(
                "INSERT INTO definir (identifiantContrat, identifiantPromotion) " +
                        "VALUES (" + contrat.getIdContrat() + ", " + promotion.getIdPromotion() + ");"
        );
    }

    public static Contrat lireContratBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT dateDebut, dateFin, clauses FROM contrat WHERE identifiantContrat = " + id + ";",
                new ArrayList<String>(Arrays.asList("dateDebut", "dateFin", "clauses"))
        );
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        LocalDate dateDebut = LocalDate.parse(r.get("dateDebut"));
        LocalDate dateFin = LocalDate.parse(r.get("dateFin"));
        String clauses = r.get("clauses");
        return new Contrat(id, dateDebut, dateFin, clauses, null, null, null);
    }

    public static Contrat lireContratParPromotion(int idPromotion) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantContrat FROM definir WHERE identifiantPromotion = " + idPromotion + ";",
                new ArrayList<String>(Arrays.asList("identifiantContrat"))
        );
        if (rows.isEmpty()) return null;
        int idContrat = Integer.parseInt(rows.get(0).get("identifiantContrat"));
        return lireContratBDD(idContrat);
    }

    public static void actualiserContratBDD(Contrat c, String... champs) throws Exception {
        if (champs.length == 0) return;
        StringBuilder sb = new StringBuilder();
        for (String f : champs) {
            sb.append(f).append("='");
            switch (f) {
                case "dateDebut": sb.append(c.getDateDebut()); break;
                case "dateFin": sb.append(c.getDateFin()); break;
                case "clauses": sb.append(c.getClauses()); break;
                default: continue;
            }
            sb.append("',");
        }
        String setClause = sb.substring(0, sb.length() - 1); // retirer la dernière virgule
        requete("UPDATE contrat SET " + setClause + " WHERE identifiantContrat = " + c.getIdContrat());
    }

    public static void supprimerContratBDD(Contrat c) throws Exception {
        // D'abord supprimer les associations
        requete("DELETE FROM definir WHERE identifiantContrat = " + c.getIdContrat() + ";");
        // Ensuite supprimer le contrat
        requete("DELETE FROM contrat WHERE identifiantContrat = " + c.getIdContrat() + ";");
    }

    public static List<Contrat> lireContratsParCentre(int idCentre) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT c.identifiantContrat, c.dateDebut, c.dateFin, c.clauses " +
                        "FROM contrat c " +
                        "JOIN commercer cm ON c.identifiantContrat = cm.identifiantContrat " +
                        "WHERE cm.identifiantCentreDeTri = " + idCentre + ";",
                new ArrayList<String>(Arrays.asList("identifiantContrat", "dateDebut", "dateFin", "clauses"))
        );
        List<Contrat> result = new ArrayList<>();
        for (var row : rows) {
            int idContrat = Integer.parseInt(row.get("identifiantContrat"));
            LocalDate dateDebut = LocalDate.parse(row.get("dateDebut"));
            LocalDate dateFin = LocalDate.parse(row.get("dateFin"));
            String clauses = row.get("clauses");
            result.add(new Contrat(idContrat, dateDebut, dateFin, clauses, null, null, null));
        }
        return result;
    }

    public static List<Contrat> lireContratsParCommerce(int idCommerce) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT c.identifiantContrat, c.dateDebut, c.dateFin, c.clauses " +
                        "FROM contrat c " +
                        "JOIN commercer cm ON c.identifiantContrat = cm.identifiantContrat " +
                        "WHERE cm.identifiantCommerce = " + idCommerce + ";",
                new ArrayList<String>(Arrays.asList("identifiantContrat", "dateDebut", "dateFin", "clauses"))
        );
        List<Contrat> result = new ArrayList<>();
        for (var row : rows) {
            int idContrat = Integer.parseInt(row.get("identifiantContrat"));
            LocalDate dateDebut = LocalDate.parse(row.get("dateDebut"));
            LocalDate dateFin = LocalDate.parse(row.get("dateFin"));
            String clauses = row.get("clauses");
            result.add(new Contrat(idContrat, dateDebut, dateFin, clauses, null, null, null));
        }
        return result;
    }
}
