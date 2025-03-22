package main;

import java.util.Set;

public class Promotion {
    private int identifiantPromotion;
    private float pourcentageRemise;
    private float pointsRequis;
    //modélisation des associations
    private Set<Utilisateur> utiliser;
    private Contrat definir;
    private CategorieDeProduits concerner;
}
