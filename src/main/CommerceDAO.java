package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CommerceDAO extends Commerce {

    public CommerceDAO(int identifiantCommerce, String nom, List<CentreDeTri> commercer, List<Contrat> contrats, Set<CategorieDeProduits> proposer) {
        super(identifiantCommerce, nom, commercer, contrats, proposer);
    }

    public static void ajouterCommerceBDD(Commerce commerce) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCommerce) FROM Commerce;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCommerce");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.get(0).get("identifiantCommerce")) + 1;

        // Mise à jour de l'identifiant dans l'objet Java
        commerce.setIdentifiantCommerce(id);
        String nom = commerce.getNom();
        List<CentreDeTri> commercer = commerce.getCommercer();
        Set<CategorieDeProduits> proposer = commerce.getProposer();
        List<Contrat> contrats = commerce.getContrat();

        // Insertion dans la BDD
        requete = "INSERT INTO Commerce(identifiantCommerce, nom) " +
                "VALUES (" + id + "," + nom + ");";
        requete(requete);

        int n = commercer.size();
        for (int k=0;k<n;k++){
            requete = "INSERT INTO commercer(identifiantCommerce, identifiantCentreDeTri, identifiantContrat) " +
                    "VALUES (" + id + "," + commercer.get(k).getIdCentreDeTri() + "," + contrats.get(k).getIdContrat() + ");";
            requete(requete);
        }
        for (CategorieDeProduits categorieDeProduits : proposer){
            requete = "INSERT INTO proposer(identifiantCategorieDeProduits, identifiantCommerce) VALUES (" + categorieDeProduits.getIdentifiantCategorie() +
                    "," + id + ");";
            requete(requete);
        }
    }
    public static void supprimerCommerceBDD(Commerce commerce){
        int id = commerce.getIdentifiantCommerce();
        String requete = "DELETE FROM commercer WHERE identifiantCommerce = " + commerce.getIdentifiantCommerce() + ";";
        requete(requete);
        requete = "DELETE FROM proposer WHERE identifiantCommerce = " + commerce.getIdentifiantCommerce() + ";";
        requete(requete);
        requete = "DELETE FROM Commerce WHERE identifiantCommerce = " + commerce.getIdentifiantCommerce() + ";";
        requete(requete);
    }

    public static void actualiserCommerceBDD(Commerce commerce, String instruction){
        int id = commerce.getIdentifiantCommerce();
        String requete;
        int n;
        if (instruction=="nom"){
            requete = "UPDATE Commerce SET nom = " + commerce.getNom() + " WHERE identifiantCommerce = " + id + ";";
            requete(requete);
        }
        if (instruction=="commercer" || instruction == "contrats"){
            List<CentreDeTri> commercer = commerce.getCommercer();
            List<Contrat> contrats = commerce.getContrat();
            requete = "DELETE FROM commercer WHERE identifiantCommerce = " + id + ";";
            requete(requete);
            n = commercer.size();
            for(int k=0; k<n; k++){
                requete = "INSERT INTO commerce(identifiantCommerce, identifiantCentreDeTri, identifiantContrat) VALUES ("
                        + id + "," + commercer.get(k).getIdCentreDeTri() + "," + contrats.get(k).getIdContrat() + ");";
                requete(requete);
            }
        }
        if (instruction=="proposer"){
            Set<CategorieDeProduits> proposer = commerce.getProposer();
            requete = "DELETE FROM proposer WHERE identifiantCommerce = " + id + ";";
            requete(requete);
            for(CategorieDeProduits categorieDeProduits : proposer){
                requete = "INSERT INTO proposer(identifiantCommerce, identifiantCategorieDeProduits) VALUES ("
                        + id + "," + categorieDeProduits.getIdentifiantCategorie() + ");";
                requete(requete);
            }
        }
    }
}
