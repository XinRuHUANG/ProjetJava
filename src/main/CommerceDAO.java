package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CommerceDAO extends Commerce {

    public CommerceDAO(int identifiant, String nom) {
        super(identifiant, nom);
    }

    public static void ajouterCommerceBDD(Commerce commerce) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCommerce) FROM Commerce;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCommerce");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCommerce")) + 1;

        // Mise à jour de l'identifiant dans l'objet Java
        commerce.setIdentifiantCommerce(id);
        String nom = commerce.getNom();

        // Insertion dans la BDD
        requete = "INSERT INTO Commerce(identifiantCommerce, nom) " +
                "VALUES (" + id + ",\"" + nom + "\");";
        requete(requete);
    }
}
