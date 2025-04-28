package main.backend.fonctions;

public class Promotion {
    private String nom;
    private double remise;
    private int pointsRequis;

    public Promotion(String nom, double remise, int pointsRequis) {
        this.nom = nom;
        this.remise = remise;
        this.pointsRequis = pointsRequis;
    }

    public Promotion(int i, float remise, float v) {
    }

    public String getNom() {
        return nom;
    }

    public double getRemise() {
        return remise;
    }

    public int getPointsRequis() {
        return pointsRequis;
    }

    public int getIdPromotion() {
    }
}
