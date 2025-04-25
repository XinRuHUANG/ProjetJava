package main;

import java.util.Map;
import java.util.Set;

import static main.PromotionDAO.*;

public class Promotion {

    //Attributs de classe
    private int idPromotion;
    private float pourcentageRemise;
    private float pointsRequis;

    //Modelisation des associations
    private CategorieDeProduits concerner;
    private Contrat definir;
    private Set<Utilisateur> utiliser;

    //Constructeur
    public Promotion(int idPromotion, float pourcentageRemise, float pointsRequis){
        this.idPromotion = idPromotion;
        this.pourcentageRemise = pourcentageRemise;
        this.pointsRequis = pointsRequis;
    }

    //Getter et Setter
    public Set<Utilisateur> getUtiliser() {
        return utiliser;
    }
    public void setUtiliser(Set<Utilisateur> utiliser) {
        this.utiliser = utiliser;
    }

    public int getIdPromotion() {
        return idPromotion;
    }
    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
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

    public CategorieDeProduits getConcerner() {
        return concerner;
    }
    public void setConcerner(CategorieDeProduits concerner) {this.concerner = concerner;}

    public Contrat getDefinir() {
        return definir;
    }
    public void setDefinir(Contrat definir) {
        this.definir = definir;
    }

    public static Promotion ajouterPromotion(float pourcentageRemise, float pointsRequis){
        Promotion promotion = new Promotion(0,pourcentageRemise,pointsRequis);
        ajouterPromotionBDD(promotion);
        return promotion;
    }
    public void supprimerPromotion(){
        supprimerPromotionBDD(this);
    }
    public void modifierPromotion(Map<String, Object> modifications){
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="pourcentageRemise"){
                float  pourcentageRemise = (float) obj;
                this.setPourcentageRemise(pourcentageRemise);
                actualiserPromotionBDD(this, cle);
            }
            if (cle=="pointsRequis"){
                float pointsRequis = (float) obj;
                this.setPointsRequis(pointsRequis);
                actualiserPromotionBDD(this, cle);
            }
            if (cle=="concerner"){
                CategorieDeProduits concerner = (CategorieDeProduits) obj;
                this.setConcerner(concerner);
                actualiserPromotionBDD(this, cle);
            }
            if (cle=="definir"){
                Contrat definir = (Contrat) obj;
                this.setDefinir(definir);
                actualiserPromotionBDD(this, cle);
            }
            if (cle=="utiliser"){
                Set<Utilisateur> posseder = (Set<Utilisateur>) obj;
                this.setUtiliser(posseder);
                actualiserPromotionBDD(this, cle);
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Promotion promotion = (Promotion) obj;
        return this.idPromotion == promotion.idPromotion
                && this.pourcentageRemise == promotion.pourcentageRemise && this.pointsRequis == promotion.pointsRequis
                && this.concerner.equals(promotion.concerner) && this.definir.equals(promotion.definir)
                && this.utiliser.equals(promotion.utiliser);
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "idPromotion=" + idPromotion +
                ", pourcentageRemise=" + pourcentageRemise +
                ", pointsRequis=" + pointsRequis +
                ", concerner=" + concerner +
                ", definir=" + definir +
                ", utiliser=" + utiliser +
                '}';
    }
}
