package main;

public class Compte {
    private int pointsFidelite;
    private int codeAcces;

    public Compte(int pointsFidelite, int codeAcces) {
        this.pointsFidelite = pointsFidelite;
        this.codeAcces = codeAcces;
    }

    public int getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(int pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public int getCodeAcces() {
        return codeAcces;
    }

    public void setCodeAcces(int codeAcces) {
        this.codeAcces = codeAcces;
    }

    public static void afficherHistorique(){}
    public static void afficherPoints(){}
    public static void afficherPromotions(){}

}
