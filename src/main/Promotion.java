package main;

import java.util.Set;

public class Promotion {
    private int identifiantPromotion;
    private float pourcentageRemise;
    private float pointsRequis;

    //Constructeur
    public Promotion(){
        this.identifiantPromotion = 0;
        this.pourcentageRemise = 0;
        this.pointsRequis = 0;
    }

    public Promotion(int identifiantPromotion, float pourcentageRemise, float pointsRequis){
        this.identifiantPromotion = identifiantPromotion;
        this.pourcentageRemise = pourcentageRemise;
        this.pointsRequis = pointsRequis;
    }


    //Getter et Setter
    public int getIdentifiantPromotion() {
        return identifiantPromotion;
    }

    public void setIdentifiantPromotion(int identifiantPromotion) {
        this.identifiantPromotion = identifiantPromotion;
    }

    public float getPourcentageRemise() {
        return pourcentageRemise;
    }

    public void setPourcentageRemise(float pourcentageRemise) {
        this.pourcentageRemise = pourcentageRemise;
    }

    public float getPointsRequis() {
        return pointsRequis;
    }

    public void setPointsRequis(float pointsRequis) {
        this.pointsRequis = pointsRequis;
    }


    //Association avec Utilisateur : utiliser
    private Set<Utilisateur> utiliser;
    private Contrat definir;
    private CategorieDeProduits concerner;
}
