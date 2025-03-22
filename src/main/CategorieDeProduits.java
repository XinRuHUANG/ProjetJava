package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CategorieDeProduits {
    private int identifiantCategorie;
    private String nom;
    //Modélisation des associations
    private Set<Promotion> concerner;
    private Set<Commerce> proposer;

    public int getIdentifiantCategorie() {
        return identifiantCategorie;
    }

    public void setIdentifiantCategorie(int identifiantCategorie) {
        this.identifiantCategorie = identifiantCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Promotion> getConcerner() {
        return concerner;
    }

    public void setConcerner(Set<Promotion> concerner) {
        this.concerner = concerner;
    }

    public Set<Commerce> getProposer() {
        return proposer;
    }

    public void setProposer(Set<Commerce> proposer) {
        this.proposer = proposer;
    }

    public CategorieDeProduits(int identifiantCategorie, String nom) {
        this.identifiantCategorie = identifiantCategorie;
        this.nom = nom;
    }
    public CategorieDeProduits(){}

    public static CategorieDeProduits creerCategorie(String nom){

        //récupération du dernier identifiant dans la BDD
        String requete = "SELECT MAX(identificationCategorie) FROM CategorieDeProduits;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identificationCategorie");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);
        int identificationCategorie = Integer.parseInt(infos.getFirst().get("identificationCategorie")) + 1;
        CategorieDeProduits categorie = new CategorieDeProduits(identificationCategorie,nom);
        //Création dans la base de données
        requete = "INSERT INTO CategorieDeProduits(identificationCategorie, nom) VALUES ("+Integer.toString(identificationCategorie)+","+nom+");";
        requete(requete);
        return categorie;
    }
    public void supprimerCategorie(CategorieDeProduits categorie){
        int identificationCategorie = categorie.identifiantCategorie;
        //Suppression dans la BDD
        String requete = "DELETE FROM CategorieDeProduits WHERE identificationCategorie = " + Integer.toString(identificationCategorie) + ";";
        requete(requete);
    }
}
