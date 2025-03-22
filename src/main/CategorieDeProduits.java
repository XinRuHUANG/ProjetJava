package main;

import java.util.Set;

public class CategorieDeProduits {
    private int identifiantCategorie;
    private String nom;
    //Modélisation des associations
    private Set<Promotion> concerner;
    private Set<Commerce> proposer;
}
