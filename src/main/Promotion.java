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

    public CategorieDeProduits getConcerner() {
        return concerner;
    }

    public void setConcerner(CategorieDeProduits concerner) {
        this.concerner = concerner;
    }

    public Contrat getDefinir() {
        return definir;
    }

    public void setDefinir(Contrat definir) {
        this.definir = definir;
    }

    public Set<Utilisateur> getUtiliser() {
        return utiliser;
    }

    public void setUtiliser(Set<Utilisateur> utiliser) {
        this.utiliser = utiliser;
    }

    public float getPointsRequis() {
        return pointsRequis;
    }

    public void setPointsRequis(float pointsRequis) {
        this.pointsRequis = pointsRequis;
    }

    public float getPourcentageRemise() {
        return pourcentageRemise;
    }

    public void setPourcentageRemise(float pourcentageRemise) {
        this.pourcentageRemise = pourcentageRemise;
    }

    public int getIdentifiantPromotion() {
        return identifiantPromotion;
    }

    public void setIdentifiantPromotion(int identifiantPromotion) {
        this.identifiantPromotion = identifiantPromotion;
    }

    public Promotion(int identifiantPromotion, float pourcentageRemise, float pointsRequis) {
        this.identifiantPromotion = identifiantPromotion;
        this.pourcentageRemise = pourcentageRemise;
        this.pointsRequis = pointsRequis;
    }
}
