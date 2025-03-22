package main;

import java.util.Set;

public class Commerce {
    private int identifiantCommerce;
    private String nom;
    //modélisation des associations
    private Set<CentreDeTri> commercer;
    private Set<CategorieDeProduits> proposer;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static void definirContrat(){}
    public static void listerProduitsPromo(){}
}
