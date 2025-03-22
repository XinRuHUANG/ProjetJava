package main;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Commerce {
    private int identifiantCommerce;
    private String nom;

    //Constructeur
    public Commerce(){
        this.identifiantCommerce = 0;
        this.nom = "";
    }

    public Commerce(int identifiantCommerce, String nom){
        this.identifiantCommerce = identifiantCommerce;
        this.nom = nom;
    }

    //Getters et Setters
    public int getIdentifiantCommerce() {return identifiantCommerce;}
    public void setIdentifiantCommerce(int identifiantCommerce) {this.identifiantCommerce = identifiantCommerce;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    //Modélisation de l'association : commercer
    private Set<CentreDeTri> commercer;

    public void ajouterCentreDeTri(CentreDeTri centre) {
        this.commercer.add(centre);
        centre.ajouterCentre(centre.getNom(), centre.getAddresse());
    }

    public void retirerCentreDeTri(CentreDeTri centre) {
        this.commercer.remove(centre);
        centre.retirerCentre(centre.getIdentifiantCentre()); // Maintient la cohérence bidirectionnelle
    }

    public Set<CentreDeTri> getCommercer() {return commercer;}
    public void setCommercer(Set<CentreDeTri> commercer) {this.commercer = commercer;}

    //Modelisation de l'association : proposer
    private Set<CategorieDeProduits> proposer;
    public Set<CategorieDeProduits> getProposer() {return proposer;}
    public void setProposer(Set<CategorieDeProduits> proposer) {this.proposer = proposer;}


}
