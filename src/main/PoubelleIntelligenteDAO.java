package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligenteDAO {

    public static void ajouterPoubelleBDD(PoubelleIntelligente poubelleIntelligente){
        String requete = "SELECT MAX(identifiantPoubelleIntelligente) FROM PoubelleIntelligente;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPoubelleIntelligente");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantPoubelleIntelligente")) + 1;

        requete = "INSERT INTO PoubelleIntelligente(identifiant, type, emplacement, capaciteMaximale) VALUES (" + Integer.toString(id) + ","
                + poubelleIntelligente.getType().toString() + "," + poubelleIntelligente.getEmplacement() + ","
                + Float.toString(poubelleIntelligente.getCapaciteMaximale()) + ");";
        requete(requete);

        requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) VALUES (" + poubelleIntelligente.getGerer().getIdCentreDeTri();
        requete(requete);
        Set<Depot> jeter = poubelleIntelligente.getJeter();
        for (Depot depot : jeter){
            requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) VALUES (" + depot.getIdentifiantDepot() + "," + poubelleIntelligente.getIdentifiantPoubelle() + ");";
            requete(requete);
        }
    }

    public static void supprimerPoubelleBDD(PoubelleIntelligente poubelleIntelligente){
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete = "DELETE FROM gerer WHERE identifiantPoubelleIntelligente = " + poubelleIntelligente.getIdentifiantPoubelle() + ";";
        requete(requete);
        requete = "DELETE FROM jeter WHERE identifiantPoubelleIntelligente =" + poubelleIntelligente.getIdentifiantPoubelle() + ";";
        requete(requete);
        requete = "DELETE FROM PoubelleIntelligente WHERE identifiantPoubelleIntelligente = " + Integer.toString(id) + ";";
        requete(requete);
    }

    public static void actualiserPoubelleIntelligenteBDD(PoubelleIntelligente poubelleIntelligente, String instruction){
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete;
        if (instruction == "emplacement") {
            requete = "UPDATE PoubelleIntelligente SET emplacement = " + poubelleIntelligente.getEmplacement()
                    + " WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction == "capaciteMaximale") {
            requete = "UPDATE PoubelleIntelligente SET capaciteMaximale = " + poubelleIntelligente.getCapaciteMaximale()
                    + " WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction == "type") {
            requete = "UPDATE PoubelleIntelligente SET type = " + poubelleIntelligente.getType()
                    + " WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction == "poids") {
            requete = "UPDATE PoubelleIntelligente SET poids = " + poubelleIntelligente.getPoids()
                    + " WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
        }
        if (instruction == "gerer") {
            requete = "DELETE FROM gerer WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
            requete = "INSERT INTO gerer(identifiantPoubelleIntelligente, identifiantCentreDeTri) VALUES("
                    + id + "," + poubelleIntelligente.getGerer().getIdCentreDeTri() + ");";
            requete(requete);
        }
        if (instruction == "jeter") {
            requete = "DELETE FROM jeter WHERE identifiantPoubelleIntelligente = " + id + ";";
            requete(requete);
            Set<Depot> depots = poubelleIntelligente.getJeter();
            for(Depot depot : depots){
                requete = "INSERT INTO gerer(identifiantPoubelleIntelligente, identifiantDepot) VALUES("
                        + id + "," + depot.getIdentifiantDepot() + ");";
                requete(requete);
            }
        }
    }

    public static void collecterDechetsBDD(PoubelleIntelligente poubelleIntelligente){
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete = "UPDATE PoubelleIntelligente SET poids = " + 0 + " WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
    }

    public static void actualiserPoidsBDD(PoubelleIntelligente poubelleIntelligente){
        int id = poubelleIntelligente.getIdentifiantPoubelle();
        String requete = "UPDATE PoubelleIntelligente SET poids = " + poubelleIntelligente.getPoids() + " WHERE identifiantPoubelleIntelligente = " + id + ";";
        requete(requete);
    }
}
