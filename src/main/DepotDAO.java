package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.CentreDeTriDAO.actualiserCentreBDD;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class DepotDAO {
    public static void creerDepotBDD(Depot depot){
        String requete = "SELECT MAX(identifiantDepot) FROM Depot;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDepot");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);
        int identifiantDepot = Integer.parseInt(infos.getFirst().get("identifiantDepot")) + 1;

        requete = "INSERT INTO Depot(identifiantDepot, date, heure, points) VALUES ("+Integer.toString(identifiantDepot)+","
                +depot.getDate().toString()+","+depot.getHeure().toString()+ "," + depot.getPoints() + ");";
        requete(requete);

        requete = "INSERT INTO posseder(identifiantDepot,identifiantUtilisateur) VALUES ("
                +depot.getIdentifiantDepot() + "," + depot.getPosseder().getIdUtilisateur() + ");";
        requete(requete);

        Set<PoubelleIntelligente> poubelleIntelligentes = depot.getJeter();
        for(PoubelleIntelligente poubelle : poubelleIntelligentes){
            requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) " +
                    "VALUES (" + depot.getIdentifiantDepot() + "," + poubelle.getIdentifiantPoubelle() + ");";
            requete(requete);
        }
    }

    public static void supprimerDepotBDD(Depot depot){
        int id = depot.getIdentifiantDepot();
        String requete = "DELETE FROM posseder WHERE identifiantDepot = " + id + ";";
        requete(requete);
        requete = "DELETE FROM contenir WHERE identifiantDepot = " + id + ";";
        requete(requete);
        requete = "DELETE FROM jeter WHERE identifiantDepot = " + id + ";";
        requete(requete);
        requete = "DELETE FROM Depot WHERE identifiantDepot = " + id + ";";
        requete(requete);
    }

    public static void actualiserDepotBDD(Depot depot, String instruction){
        int identifiantDepot = depot.getIdentifiantDepot();
        String requete;
        if (instruction=="date"){
            requete = "UPDATE Depot SET date = " + depot.getDate() + ";";
            requete(requete);
        }
        if (instruction=="heure"){
            requete = "UPDATE Depot SET heure = " + depot.getHeure() + ";";
            requete(requete);
        }
        if (instruction=="points"){
            requete = "UPDATE Depot SET points = " + depot.getPoints() + ";";
            requete(requete);
        }
        if (instruction=="jeter"){
            requete = "DELETE FROM jeter WHERE identifiantDepot =" + identifiantDepot + ";";
            requete(requete);
            Set<PoubelleIntelligente> jeter = depot.getJeter();
            for(PoubelleIntelligente poubelle : jeter){
                int identifiantPoubelleIntelligente = poubelle.getIdentifiantPoubelle();
                requete = "INSERT INTO jeter(identifiantDepot, identifiantPoubelleIntelligente) " +
                        "VALUES (" + identifiantDepot + "," + identifiantPoubelleIntelligente + ");";
                requete(requete);
            }
        }
        if (instruction=="posseder"){
            requete = "DELETE FROM posseder WHERE identifiantDepot =" + identifiantDepot + ";";
            requete(requete);
            requete = "INSERT INTO posseder(identifiantDepot, identifiantUtilisateur) VALUES(" + identifiantDepot + "," + depot.getPosseder().getIdUtilisateur() + ");";
            requete(requete);
        }
        if (instruction=="contenir"){
            requete = "DELETE FROM contenir WHERE identifiantDepot =" + identifiantDepot + ";";
            requete(requete);
            int n = depot.getContenir().size();
            for(int k = 0; k<n; k++){
                requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) " +
                        "VALUES (" + depot.getContenir().get(k).getIdentifiantDechet() + ","
                        +  identifiantDepot + ");";
                requete(requete);
            }
        }
    }
    public static void ajouterDechetDepotBDD(Depot depot, Dechet dechet){
        String requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) VALUES ("+
                dechet.getIdentifiantDechet() +","+depot.getIdentifiantDepot()+");";
        requete(requete);
    }
    public static void supprimerDechetDepotBDD(Depot depot, Dechet dechet){
        String requete = "DELETE FROM contenir where identifiantDepot = " + depot.getIdentifiantDepot() + " AND identifiantDechet = " + dechet.getIdentifiantDechet() + ";";
        requete(requete);
    }
}
