package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligenteDAO {

    public static void ajouterPoubelleBDD(PoubelleIntelligente poubelleIntelligente){
        String requete = "SELECT MAX(identifiantPoubelle) FROM PoubelleIntelligente;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPoubelle");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantPoubelle")) + 1;

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
        String requete = "DELETE FROM PoubelleIntelligente WHERE identifiantPoubelleIntelligente = " + Integer.toString(id) + ";";
        requete(requete);
        requete = "DELETE FROM gerer WHERE identifiantPoubelleIntelligente = " + poubelleIntelligente.getIdentifiantPoubelle() + ";";
        requete(requete);
        requete = "DELETE FROM jeter WHERE identifiantPoubelleIntelligente =" + poubelleIntelligente.getIdentifiantPoubelle() + ";";
        requete(requete);
    }

    public static void actualiserCentreBDD(CentreDeTri centredetri, String instruction){
        int identifiantCentreDeTri = centredetri.getIdCentreDeTri();
        String requete;
        if (instruction=="nom"){
            requete = "UPDATE CentreDeTri SET nom = "+ centredetri.getNom() +" WHERE identifiantCentreDeTri =" + identifiantCentreDeTri + ";";
            requete(requete);
        }
        if (instruction=="adresse"){
            requete = "UPDATE CentreDeTri SET adresse = "+ centredetri.getAdresse() +" WHERE identifiantCentreDeTri =" + identifiantCentreDeTri + ";";
            requete(requete);
        }
        if (instruction=="poubelles"){
            requete = "DELETE FROM gerer WHERE identifiantCentreDeTri =" + identifiantCentreDeTri + ";";
            requete(requete);
            Set<PoubelleIntelligente> poubelles = centredetri.getPoubelles();
            int n = poubelles.size();
            for(PoubelleIntelligente poubelle : poubelles){
                int identifiantPoubelleIntelligente = poubelle.getIdentifiantPoubelle();
                requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) " +
                        "VALUES (" + Integer.toString(identifiantCentreDeTri) + "," + identifiantPoubelleIntelligente + ");";
                requete(requete);
            }
        }
        //Si les commerces sont modifiés alors les contrats sont aussi modifiés (rappel: le kème commerce est associé au kème contrat dans notre modèle)
        if (instruction=="commerces" || instruction=="contrats"){
            //nettoyage
            requete = "DELETE FROM commercer WHERE identifiantCentreDeTri =" + identifiantCentreDeTri + ";";
            requete(requete);
            //ajout
            List<Commerce> commerce = centredetri.getCommerce();
            List<Contrat> contrats = centredetri.getContrats();
            int n = commerce.size();
            for(int k = 0; k < n; k++){
                int identifiantCommerce = commerce.get(k).getIdentifiantCommerce();
                int identifiantContrat = contrats.get(k).getIdentifiantContrat();
                requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                        "VALUES (" + Integer.toString(identifiantCentreDeTri) + "," + identifiantCommerce + "," + identifiantContrat +");";
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
