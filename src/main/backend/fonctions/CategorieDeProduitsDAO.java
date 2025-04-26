package main.backend.fonctions;

import java.sql.SQLException;
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

    public static void ajouterCategorieDeProduitsBDD(CategorieDeProduits categorieDeProduits) throws SQLException {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCategorieDeProduits) FROM categoriedeproduits;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCategorieDeProduits");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantCategorieDeProduits");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        // Mise à jour de l'identifiant dans l'objet Java
        categorieDeProduits.setIdentifiantCategorie(id);
        String nom = categorieDeProduits.getNom();
        Set<Promotion> concerner = categorieDeProduits.getConcerner();
        Set<Commerce> proposer = categorieDeProduits.getProposer();

        // Insertion dans la BDD
        requete = "INSERT INTO categoriedeproduits(identifiantCategorieDeProduits, nom) " +
                "VALUES (" + id + ", '" + nom + "');";
        requete(requete);

        if (categorieDeProduits.getConcerner() != null) {
            for (Promotion p : categorieDeProduits.getConcerner()) {
                requete = "INSERT INTO concerner(identifiantCategorieDeProduits, identifiantPromotion) " +
                        "VALUES (" + id + "," + p.getIdPromotion() + ");";
                requete(requete);
            }
        }

        if (categorieDeProduits.getProposer() != null) {
            for (Commerce c : categorieDeProduits.getProposer()) {
                requete = "INSERT INTO proposer(identifiantCategorieDeProduits, identifiantCommerce) " +
                        "VALUES (" + id + "," + c.getIdentifiantCommerce() + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerCategorieDeProduitsBDD(CategorieDeProduits categorieDeProduits) {
        int id = categorieDeProduits.getIdentifiantCategorie();
        String requete = "DELETE FROM proposer WHERE identifiantCategorieDeProduits = " + id + ";";
        requete(requete);
        requete = "DELETE FROM concerner WHERE identifiantCategorieDeProduits = " + id + ";";
        requete(requete);
        requete = "DELETE FROM categoriedeproduits WHERE identifiantCategorieDeProduits = " + id + ";";
        requete(requete);
    }

    public static void actualiserCategorieDeProduitsBDD(CategorieDeProduits categorieDeProduits, String instruction) {
        int id = categorieDeProduits.getIdentifiantCategorie();
        String requete;
        if (instruction.equals("nom")) {
            requete = "UPDATE categoriedeproduits SET nom = '" + categorieDeProduits.getNom() + "' WHERE identifiantCategorieDeProduits = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("concerner")) {
            Set<Promotion> concerner = categorieDeProduits.getConcerner();
            requete = "DELETE FROM concerner WHERE identifiantCategorieDeProduits = " + id + ";";
            requete(requete);
            for (Promotion promotion : concerner) {
                requete = "INSERT INTO concerner(identifiantCategorieDeProduits, identifiantPromotion) VALUES (" + id + "," + promotion.getIdPromotion() + ");";
                requete(requete);
            }
        }
        if (instruction.equals("proposer")) {
            Set<Commerce> proposer = categorieDeProduits.getProposer();
            requete = "DELETE FROM proposer WHERE identifiantCategorieDeProduits = " + id + ";";
            requete(requete);
            for (Commerce commerce : proposer) {
                requete = "INSERT INTO proposer(identifiantCategorieDeProduits, identifiantCommerce) VALUES (" + id + "," + commerce.getIdentifiantCommerce() + ");";
                requete(requete);
            }
        }
    }
}
