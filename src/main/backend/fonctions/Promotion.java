package main.backend.fonctions;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Promotion {
    private int idPromotion;
    private float pourcentageRemise;
    private float pointsRequis;
    private CategorieDeProduits concerner;
    private Contrat definir;
    private Set<Utilisateur> utiliser;

    public Promotion(int id, float pr, float pts) {
        this.idPromotion = id;
        this.pourcentageRemise = pr;
        this.pointsRequis = pts;
    }

    // getters / setters…
    public int getIdPromotion() {return idPromotion;}
    public void setIdPromotion(int idPromotion) {this.idPromotion = idPromotion;}

    public float getPourcentageRemise() {return pourcentageRemise;}
    public void setPourcentageRemise(float pourcentageRemise) {this.pourcentageRemise = pourcentageRemise;}

    public float getPointsRequis() {return pointsRequis;}
    public void setPointsRequis(float pointsRequis) {this.pointsRequis = pointsRequis;}

    public CategorieDeProduits getConcerner() {return concerner;}
    public void setConcerner(CategorieDeProduits concerner) {this.concerner = concerner;}

    public Contrat getDefinir() {return definir;}
    public void setDefinir(Contrat definir) {this.definir = definir;}

    public Set<Utilisateur> getUtiliser() {return utiliser;}
    public void setUtiliser(Set<Utilisateur> utiliser) {this.utiliser = utiliser;}

    public static Promotion ajouterPromotion(float pr, float pts) {return new Promotion(0, pr, pts);}

    public void modifierPromotion(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "pourcentageRemise" -> this.pourcentageRemise = (float)e.getValue();
                case "pointsRequis"      -> this.pointsRequis = (float)e.getValue();
                case "concerner"         -> this.concerner = (CategorieDeProduits)e.getValue();
                case "definir"           -> this.definir = (Contrat)e.getValue();
                case "utiliser"          -> this.utiliser = (Set<Utilisateur>)e.getValue();
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void supprimerPromotion() {
        // plus d'appel DAO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotion)) return false;
        Promotion that = (Promotion) o;
        return idPromotion == that.idPromotion
                && Float.compare(that.pourcentageRemise, pourcentageRemise) == 0
                && Float.compare(that.pointsRequis, pointsRequis) == 0
                && Objects.equals(concerner, that.concerner)
                && Objects.equals(definir, that.definir)
                && Objects.equals(utiliser, that.utiliser);
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
