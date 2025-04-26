package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DepotDAO {
    public static void creerDepotBDD(Depot depot) throws SQLException {
        String requete = "SELECT MAX(identifiantDepot) FROM depot;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDepot");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantDepot");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int identifiantDepot = previousMax + 1;

        depot.setIdentifiantDepot(identifiantDepot); // (Ajouté sinon problème id null dans INSERT suivants)

        requete = "INSERT INTO depot(identifiantDepot, date, heure, points) VALUES (" +
                identifiantDepot + ", '" + depot.getDate().toString() + "', '" + depot.getHeure().toString() + "', " + depot.getPoints() + ");";
        requete(requete);

        if (depot.getPosseder() != null) {
            requete = "INSERT INTO posseder(identifiantDepot,identifiantUtilisateur) VALUES (" +
                    identifiantDepot + "," + depot.getPosseder().getIdUtilisateur() + ");";
            requete(requete);
        }

        Set<PoubelleIntelligente> poubelles = depot.getJeter();
        if (poubelles != null) {
            for (PoubelleIntelligente p : poubelles) {
                requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) VALUES (" +
                        identifiantDepot + "," + p.getIdentifiantPoubelle() + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerDepotBDD(Depot depot) {
        int id = depot.getIdentifiantDepot();
        String requete = "DELETE FROM posseder WHERE identifiantDepot = " + id + ";";
        requete(requete);
        requete = "DELETE FROM contenir WHERE identifiantDepot = " + id + ";";
        requete(requete);
        requete = "DELETE FROM jeter WHERE identifiantDepot = " + id + ";";
        requete(requete);
        requete = "DELETE FROM depot WHERE identifiantDepot = " + id + ";";
        requete(requete);
    }

    public static void actualiserDepotBDD(Depot depot, String instruction) {
        int identifiantDepot = depot.getIdentifiantDepot();
        String requete;
        if (instruction.equals("date")) {
            requete = "UPDATE depot SET date = '" + depot.getDate() + "' WHERE identifiantDepot = " + identifiantDepot + ";";
            requete(requete);
        }
        if (instruction.equals("heure")) {
            requete = "UPDATE depot SET heure = '" + depot.getHeure() + "' WHERE identifiantDepot = " + identifiantDepot + ";";
            requete(requete);
        }
        if (instruction.equals("points")) {
            requete = "UPDATE depot SET points = " + depot.getPoints() + " WHERE identifiantDepot = " + identifiantDepot + ";";
            requete(requete);
        }
        if (instruction.equals("jeter")) {
            requete = "DELETE FROM jeter WHERE identifiantDepot = " + identifiantDepot + ";";
            requete(requete);
            Set<PoubelleIntelligente> jeter = depot.getJeter();
            for (PoubelleIntelligente poubelle : jeter) {
                int identifiantPoubelleIntelligente = poubelle.getIdentifiantPoubelle();
                requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) " +
                        "VALUES (" + identifiantDepot + "," + identifiantPoubelleIntelligente + ");";
                requete(requete);
            }
        }
        if (instruction.equals("posseder")) {
            requete = "DELETE FROM posseder WHERE identifiantDepot = " + identifiantDepot + ";";
            requete(requete);
            requete = "INSERT INTO posseder(identifiantDepot, identifiantUtilisateur) VALUES (" +
                    identifiantDepot + "," + depot.getPosseder().getIdUtilisateur() + ");";
            requete(requete);
        }
        if (instruction.equals("contenir")) {
            requete = "DELETE FROM contenir WHERE identifiantDepot = " + identifiantDepot + ";";
            requete(requete);
            int n = depot.getContenir().size();
            for (int k = 0; k < n; k++) {
                requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) " +
                        "VALUES (" + depot.getContenir().get(k).getIdentifiantDechet() + "," + identifiantDepot + ");";
                requete(requete);
            }
        }
    }

    public static void ajouterDechetDepotBDD(Depot depot, Dechet dechet) {
        String requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) VALUES (" +
                dechet.getIdentifiantDechet() + "," + depot.getIdentifiantDepot() + ");";
        requete(requete);
    }

    public static void supprimerDechetDepotBDD(Depot depot, Dechet dechet) {
        String requete = "DELETE FROM contenir WHERE identifiantDepot = " +
                depot.getIdentifiantDepot() + " AND identifiantDechet = " + dechet.getIdentifiantDechet() + ";";
        requete(requete);
    }
}
