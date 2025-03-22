package main;

import java.util.Set;

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
}
