package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CategorieDeProduitsDAO extends CategorieDeProduits {

    public CategorieDeProduitsDAO(int identifiantCategorieDeProduits, String nom, Set<Promotion> concerner, Set<Commerce> proposer) {
        super(identifiantCategorieDeProduits, nom, concerner, proposer);
    }

    public static void ajouterCategorieDeProduitsBDD(CategorieDeProduits categorieDeProduits) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCategorieDeProduits) FROM CategorieDeProduits;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCategorieDeProduits");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCategorieDeProduits")) + 1;

        // Mise à jour de l'identifiant dans l'objet Java
        categorieDeProduits.setIdentifiantCategorie(id);
        String nom = categorieDeProduits.getNom();
        Set<Promotion> concerner = categorieDeProduits.getConcerner();
        Set<Commerce> proposer = categorieDeProduits.getProposer();

        // Insertion dans la BDD
        requete = "INSERT INTO CategorieDeProduits(identifiantCategorieDeProduits, nom) " +
                "VALUES (" + id + "," + nom + ");";
        requete(requete);
        requete = "INSERT INTO concerner(identifiantCategorieDeProduits, identifiantPromotion) VALUES (" + categorieDeProduits.getIdentifiantCategorie() +
                "," + categorieDeProduits.getConcerner() + ");";
        requete(requete);
        for (Commerce commerce : proposer) {
            requete = "INSERT INTO proposer(identifiantCategorieDeProduits, identifiantCommerce) VALUES (" + categorieDeProduits.getIdentifiantCategorie()
                    + "," + commerce.getIdentifiantCommerce() + ");";
            requete(requete);
        }

    }
    public static void supprimerCategorieDeProduitsBDD(CategorieDeProduits categorieDeProduits){
        int id = categorieDeProduits.getIdentifiantCategorie();
        String requete = "DELETE FROM proposer WHERE identifiantCategorieDeProduits = " + id + ";";
        requete(requete);
        requete = "DELETE FROM concerner WHERE identifiantCategorieDeProduits = " + id + ";";
        requete(requete);
        requete = "DELETE FROM CategorieDeProduits WHERE identifiantCategorieDeProduits = " + id + ";";
        requete(requete);
    }

    public static void actualiserCategorieDeProduitsBDD(CategorieDeProduits categorieDeProduits, String instruction){
        int id = categorieDeProduits.getIdentifiantCategorie();
        String requete;
        if (instruction=="nom"){
            requete = "UPDATE CategorieDeProduits SET nom = " + categorieDeProduits.getNom() + " WHERE identifiantCategorieDeProduits = " + id + ";";
            requete(requete);
        }
        if (instruction=="concerner"){
            Set<Promotion> concerner = categorieDeProduits.getConcerner();
            requete = "DELETE FROM concerner WHERE identifiantCategorieDeProduits = " + id + ";";
            requete(requete);
            for(Promotion promotion : concerner){
                requete = "INSERT INTO concerner(identifiantCategorieDeProduits, identifiantPromotion) VALUES (" + id + "," + promotion.getIdPromotion() + ");";
                requete(requete);
            }
        }
        if (instruction=="proposer"){
            Set<Commerce> proposer = categorieDeProduits.getProposer();
            requete = "DELETE FROM proposer WHERE identifiantCategorieDeProduits = " + id + ";";
            requete(requete);
            for(Commerce commerce : proposer){
                requete = "INSERT INTO proposer(identifiantCategorieDeProduits, identifiantCommerce) VALUES (" + id + "," + commerce.getIdentifiantCommerce() + ");";
                requete(requete);
            }
        }
    }
}
