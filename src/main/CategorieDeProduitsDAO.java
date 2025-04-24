package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CategorieDeProduitsDAO extends CategorieDeProduits {

    public CategorieDeProduitsDAO(int identifiant, String nom) {
        super(identifiant, nom);
    }

    public static void ajouterCategorieBDD(CategorieDeProduits categorie) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCategorie) FROM CategorieDeProduits;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCategorie");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCategorie")) + 1;

        // Mise à jour de l'identifiant dans l'objet Java
        categorie.setIdentifiant(id);
        String nom = categorie.getNom();

        // Insertion dans la BDD
        requete = "INSERT INTO CategorieDeProduits(identifiantCategorie, nom) " +
                "VALUES (" + id + ",\"" + nom + "\");";
        requete(requete);
    }
}
