package main;

import java.util.*;

import static main.CommerceDAO.*;

public class Commerce {

    //attributs
    private int identifiantCommerce;
    private String nom;
    //modélisation des associations
    private List<CentreDeTri> commercer;
    private List<Contrat> contrats;
    private Set<CategorieDeProduits> proposer;

    public int getIdentifiantCommerce() {return identifiantCommerce;}
    public void setIdentifiantCommerce(int identifiantCommerce) {this.identifiantCommerce = identifiantCommerce;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public List<CentreDeTri> getCommercer() {return commercer;}
    public void setCommercer(List<CentreDeTri> commercer) {this.commercer = commercer;}

    public Set<CategorieDeProduits> getProposer() {return proposer;}
    public void setProposer(Set<CategorieDeProduits> proposer) {this.proposer = proposer;}

    public List<Contrat> getContrat() {return contrats;}
    public void setContrat(List<Contrat> contrat) {this.contrats = contrat;}

    public static void definirContrat(){}
    public static void listerProduitsPromo(){}

    public Commerce(int identifiantCommerce, String nom, List<CentreDeTri> commercer, List<Contrat> contrats, Set<CategorieDeProduits> proposer) {
        this.identifiantCommerce = identifiantCommerce;
        this.nom = nom;
        this.commercer = commercer;
        this.contrats = contrats;
        this.proposer = proposer;
    }

    //Methode de classe
    public static Commerce ajouterCommerce(String nom, List<CentreDeTri> commercer, List<Contrat> contrats, Set<CategorieDeProduits> proposer){
        /* Crée et ajoute un nouveau commerce à la base de données.
         * @param nom Le nom du commerce.
         * @param commercer La liste des centres de tri avec lesquels le commerce est lié.
         * @param contrats La liste des contrats associés au commerce.
         * @param proposer L'ensemble des catégories de produits proposées par le commerce.
         * @return Le commerce nouvellement créé.
         */
        Commerce commerce = new Commerce(0,nom, commercer, contrats, proposer);
        ajouterCommerceBDD(commerce);
        return commerce;
    }

    public void supprimerCommerce(){
        supprimerCommerceBDD(this);
    }

    public void modifierCommerce(Map<String, Object> modifications){
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="nom"){
                String nom = (String) obj;
                this.setNom(nom);
                actualiserCommerceBDD(this, cle);
            }
            if (cle=="commercer"){
                assert modifications.containsKey("contrats");
                List<CentreDeTri> commercer = (List<CentreDeTri>) obj;
                this.setCommercer(commercer);
                actualiserCommerceBDD(this, cle);
            }
            if (cle=="proposer"){
                Set<CategorieDeProduits> proposer = (Set<CategorieDeProduits>) obj;
                this.setProposer(proposer);
                actualiserCommerceBDD(this, cle);
            }
            if (cle=="contrats"){
                assert modifications.containsKey("commercer");
                List<Contrat> contrats = (List<Contrat>) obj;
                this.setContrat(contrats);
                actualiserCommerceBDD(this, cle);
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Commerce commerce = (Commerce) obj;
        return this.identifiantCommerce == commerce.identifiantCommerce &&
                this.nom == commerce.nom && this.commercer.equals(commerce.commercer)
                && this.proposer.equals(commerce.proposer) && this.contrats.equals(commerce.contrats);
    }

    @Override
    public String toString() {
        return "Commerce{" +
                "identifiantCommerce=" + identifiantCommerce +
                ", nom='" + nom + '\'' +
                ", commercer=" + commercer +
                ", contrats=" + contrats +
                ", proposer=" + proposer +
                '}';
    }
}
