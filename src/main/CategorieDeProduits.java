package main;

import java.util.*;

import static main.CategorieDeProduitsDAO.*;

public class CategorieDeProduits {
    private int identifiantCategorieDeProduits;
    private String nom;
    //Modélisation des associations
    private Set<Promotion> concerner;
    private Set<Commerce> proposer;

    public int getIdentifiantCategorie() {
        return identifiantCategorieDeProduits;
    }
    public void setIdentifiantCategorie(int identifiantCategorie) {this.identifiantCategorieDeProduits = identifiantCategorie;}

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

    public CategorieDeProduits(int identifiantCategorieDeProduits, String nom, Set<Promotion> concerner, Set<Commerce> proposer) {
        this.identifiantCategorieDeProduits = identifiantCategorieDeProduits;
        this.nom = nom;
        this.concerner = concerner;
        this.proposer = proposer;
    }

    public static CategorieDeProduits creerCategorieDeProduits(String nom, Set<Promotion> concerner, Set<Commerce> proposer){
        CategorieDeProduits categorie = new CategorieDeProduits(0,nom,concerner,proposer);
        ajouterCategorieDeProduitsBDD(categorie);
        return categorie;
    }

    public void supprimerCategorieDeProduits(){
        supprimerCategorieDeProduitsBDD(this);
    }

    public void modifierCategorieDeProduitsBDD(Map<String, Object> modifications){
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="nom"){
                String nom = (String) obj;
                this.setNom(nom);
                actualiserCategorieDeProduitsBDD(this, cle);
            }
            if (cle=="concerner"){
                Set<Promotion> concerner = (Set<Promotion>) obj;
                this.setConcerner(concerner);
                actualiserCategorieDeProduitsBDD(this, cle);
            }
            if (cle=="proposer"){
                Set<Commerce> proposer = (Set<Commerce>) obj;
                this.setProposer(proposer);
                actualiserCategorieDeProduitsBDD(this, cle);
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        CategorieDeProduits categorieDeProduits = (CategorieDeProduits) obj;
        return this.identifiantCategorieDeProduits == categorieDeProduits.identifiantCategorieDeProduits &&
                this.nom == categorieDeProduits.nom && this.concerner.equals(categorieDeProduits.concerner)
                && this.proposer.equals(categorieDeProduits.proposer);
        }

    @Override
    public String toString() {
        return "CategorieDeProduits{" +
                "identifiantCategorieDeProduits=" + identifiantCategorieDeProduits +
                ", nom='" + nom + '\'' +
                ", concerner=" + concerner +
                ", proposer=" + proposer +
                '}';
    }
}
