package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Commerce {
    private int identifiantCommerce;
    private String nom;
    //modélisation des associations
    private Set<CentreDeTri> commercer;
    private Set<CategorieDeProduits> proposer;

    public int getIdentifiantCommerce() {
        return identifiantCommerce;
    }

    public void setIdentifiantCommerce(int identifiantCommerce) {
        this.identifiantCommerce = identifiantCommerce;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<CentreDeTri> getCommercer() {
        return commercer;
    }

    public void setCommercer(Set<CentreDeTri> commercer) {
        this.commercer = commercer;
    }

    public Set<CategorieDeProduits> getProposer() {
        return proposer;
    }

    public void setProposer(Set<CategorieDeProduits> proposer) {
        this.proposer = proposer;
    }



    @Override
    public String toString() {
        return "Commerce{" +
                "identifiantCommerce=" + identifiantCommerce +
                ", nom='" + nom + '\'' +
                ", commercer=" + commercer +
                ", proposer=" + proposer +
                '}';
    }

    public static void definirContrat(){}
    public static void listerProduitsPromo(){}

    public Commerce(int identifiantCommerce, String nom) {
        this.identifiantCommerce = identifiantCommerce;
        this.nom = nom;
    }

    public static Commerce ajouterCommerce(String nom){
        String requete = "SELECT MAX(identifiantCommerce) FROM Commerce;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCommerce");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCommerce")) + 1;

        Commerce commerce = new Commerce(id, nom);
        //Création dans la base de données
        requete = "INSERT INTO Commerce(identifiantCommerce, nom) VALUES (" + Integer.toString(id) + "," + nom + ");";
        requete(requete);
        return commerce;
    }

    public void retirerCommerce(){
        int identifiantCommerce = this.identifiantCommerce;
        String requete = "DELETE FROM Commerce WHERE identifiant = " + Integer.toString(identifiantCommerce) + ";";
    }

}
