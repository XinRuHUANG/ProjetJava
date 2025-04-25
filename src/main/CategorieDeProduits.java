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

    public static CategorieDeProduits creerCategorieDeProduits(String nom, Set<Promotion> concerner, Set<Commerce> proposer) {
        /* Crée une nouvelle catégorie de produits et l'ajoute à la base de données.
         *
         * @param nom Le nom de la catégorie de produits.
         * @param concerner Les promotions associées à la catégorie de produits.
         * @param proposer Les commerces qui proposent cette catégorie de produits.
         * @return La catégorie de produits nouvellement créée.
         */
        CategorieDeProduits categorie = new CategorieDeProduits(0, nom, concerner, proposer);
        ajouterCategorieDeProduitsBDD(categorie);
        return categorie;
    }


    public void supprimerCategorieDeProduits() {
        /*Supprime la catégorie de produits de la base de données.
         */
        supprimerCategorieDeProduitsBDD(this);
    }


    public void modifierCategorieDeProduitsBDD(Map<String, Object> modifications) {
        /*Modifie les attributs de la catégorie de produits en fonction des modifications spécifiées dans la map.
         *
         * @param modifications Map contenant les attributs à modifier.
         */
        for (Map.Entry<String, Object> entry : modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle.equals("nom")) {
                String nom = (String) obj;
                this.setNom(nom);
                actualiserCategorieDeProduitsBDD(this, cle);
            }
            if (cle.equals("concerner")) {
                Set<Promotion> concerner = (Set<Promotion>) obj;
                this.setConcerner(concerner);
                actualiserCategorieDeProduitsBDD(this, cle);
            }
            if (cle.equals("proposer")) {
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
