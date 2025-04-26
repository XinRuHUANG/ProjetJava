package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligenteDAO {

    public static void ajouterPoubelleBDD(PoubelleIntelligente poubelleIntelligente) throws SQLException {
        String requete = "SELECT MAX(identifiantPoubelleIntelligente) FROM poubelleintelligente;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPoubelleIntelligente");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantPoubelleIntelligente");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        poubelleIntelligente.setIdentifiantPoubelle(id);

        requete = "INSERT INTO poubelleintelligente(identifiantPoubelleIntelligente, type, emplacement, capaciteMaximale) VALUES (" +
                id + ", '" + poubelleIntelligente.getType().toString() + "', '" + poubelleIntelligente.getEmplacement() + "', " +
                poubelleIntelligente.getCapaciteMaximale() + ");";
        requete(requete);

        if (poubelleIntelligente.getGerer() != null) {
            requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) VALUES (" +
                    poubelleIntelligente.getGerer().getIdCentreDeTri() + "," + id + ");";
            requete(requete);
        }

        if (poubelleIntelligente.getJeter() != null) {
            for (Depot d : poubelleIntelligente.getJeter()) {
                requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) VALUES (" +
                        d.getIdentifiantDepot() + "," + id + ");";
                requete(requete);
            }
        }

        if (poubelleIntelligente.getStocker() != null) {
            for (Dechet d : poubelleIntelligente.getStocker()) {
                requete = "INSERT INTO stocker(identifiantDechet, identifiantPoubelleIntelligente) VALUES (" +
                        d.getIdentifiantDechet() + "," + id + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerPoubelleBDD(PoubelleIntelligente poubelleIntelligente) {
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete = "DELETE FROM gerer WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
        requete = "DELETE FROM jeter WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
        requete = "DELETE FROM stocker WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
        requete = "DELETE FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
    }

    public static void actualiserPoubelleIntelligenteBDD(PoubelleIntelligente poubelleIntelligente, String instruction) {
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete;
        if (instruction.equals("emplacement")) {
            requete = "UPDATE poubelleintelligente SET emplacement = '" + poubelleIntelligente.getEmplacement() +
                    "' WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("capaciteMaximale")) {
            requete = "UPDATE poubelleintelligente SET capaciteMaximale = " + poubelleIntelligente.getCapaciteMaximale() +
                    " WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("type")) {
            requete = "UPDATE poubelleintelligente SET type = '" + poubelleIntelligente.getType() +
                    "' WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("poids")) {
            requete = "UPDATE poubelleintelligente SET poids = " + poubelleIntelligente.getPoids() +
                    " WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("gerer")) {
            requete = "DELETE FROM gerer WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
            requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) VALUES (" +
                    poubelleIntelligente.getGerer().getIdCentreDeTri() + "," + id + ");";
            requete(requete);
        }
        if (instruction.equals("jeter")) {
            requete = "DELETE FROM jeter WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
            Set<Depot> depots = poubelleIntelligente.getJeter();
            for (Depot depot : depots) {
                requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) VALUES (" +
                        depot.getIdentifiantDepot() + "," + id + ");";
                requete(requete);
            }
        }
        if (instruction.equals("stocker")) {
            requete = "DELETE FROM stocker WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
            Set<Dechet> dechets = poubelleIntelligente.getStocker();
            for (Dechet dechet : dechets) {
                requete = "INSERT INTO stocker(identifiantDechet, identifiantPoubelleIntelligente) VALUES (" +
                        dechet.getIdentifiantDechet() + "," + id + ");";
                requete(requete);
            }
        }
    }

    public static void collecterDechetsBDD(PoubelleIntelligente poubelleIntelligente) {
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete = "UPDATE poubelleintelligente SET poids = 0 WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
        requete = "DELETE FROM stocker WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
    }

    public static void actualiserPoidsBDD(PoubelleIntelligente poubelleIntelligente) {
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete = "UPDATE poubelleintelligente SET poids = " + poubelleIntelligente.getPoids() +
                " WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
    }
}
