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
            int identifiantContrat = contrats.get(k).getIdContrat();
            requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                    "VALUES (" + Integer.toString(id) + "," + identifiantCommerce + "," + identifiantContrat +");";
            requete(requete);
        }
        //Ajout des liaisons entre les poubelles et le centre de tri dans la BDD
        for(PoubelleIntelligente poubelle : poubelleIntelligente){
            int identifiantPoubelleIntelligente = poubelle.getIdentifiantPoubelle();
            requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) " +
                    "VALUES (" + Integer.toString(id) + "," + identifiantPoubelleIntelligente + ");";
            requete(requete);
        }
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
                int identifiantContrat = contrats.get(k).getIdContrat();
                requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                        "VALUES (" + Integer.toString(identifiantCentreDeTri) + "," + identifiantCommerce + "," + identifiantContrat +");";
                requete(requete);
                }
            }
    }

    public static void supprimerCentreBDD(CentreDeTri centredetri){
        int identifiantCentreDeTri = centredetri.getIdCentreDeTri();
        String requete = "DELETE FROM CentreDeTri WHERE identifiantCentreDeTri =" + Integer.toString(identifiantCentreDeTri) + ";";
        requete(requete);
        requete = "DELETE FROM commercer WHERE identifiantCentreDeTri =" + Integer.toString(identifiantCentreDeTri) + ";";
        requete(requete);
        requete = "DELETE FROM gerer WHERE identifiantCentreDeTri =" + Integer.toString(identifiantCentreDeTri) + ";";
        requete(requete);
    }

}
