package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CentreDeTriDAO extends CentreDeTri{

    public CentreDeTriDAO(int idCentreDeTri, String nom, String adresse, Set<PoubelleIntelligente> poubelles, List<Commerce> commerce, List<Contrat> contrats) {
        super(idCentreDeTri, nom, adresse, poubelles, commerce, contrats);
    }

    public static void ajouterCentreBDD(CentreDeTri centreDeTri){

        //Création de l'identifiant
        String requete = "SELECT MAX(identifiantCentreDeTri) FROM CentreDeTri;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCentreDeTri");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCentreDeTri")) + 1;

        //Récupération des infos
        centreDeTri.setIdCentreDeTri(id);
        String nom = centreDeTri.getNom();
        String adresse = centreDeTri.getAdresse();
        Set<PoubelleIntelligente> poubelleIntelligente = centreDeTri.getPoubelles();
        List<Commerce> commerce = centreDeTri.getCommerce();
        List<Contrat> contrats = centreDeTri.getContrats();

        //Ajout du nouveau centre dans la table CentreDeTri
        requete = "INSERT INTO CentreDeTri(identifiantCentreDeTri, nom, adresse) " +
                "VALUES (" + Integer.toString(id) + "," + nom + "," + adresse +");";
        requete(requete);

        //Ajout des liaisons entre le nouveau centre de tri et les commerces associés dans la table "commercer"
        int n = commerce.size();
        for(int k = 0; k < n; k++){
            int identifiantCommerce = commerce.get(k).getIdentifiantCommerce();
            int identifiantContrat = contrats.get(k).getIdentifiantContrat();
            requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                    "VALUES (" + Integer.toString(id) + "," + identifiantCommerce + "," + identifiantContrat +");";
            requete(requete);
        }
        //Ajout des liaisons entre les poubelles et le centre de tri dans la BDD
        n = poubelleIntelligente.size();
        for(PoubelleIntelligente poubelle : poubelleIntelligente){
            int identifiantPoubelleIntelligente = poubelle.getIdentifiantPoubelle();
            requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) " +
                    "VALUES (" + Integer.toString(id) + "," + identifiantPoubelleIntelligente + ");";
            requete(requete);
        }
    }

}
