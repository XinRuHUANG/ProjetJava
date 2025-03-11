package main;

public class CentreDeTri {
    private String nom;
    private String addresse;

    public CentreDeTri(String nom, String adresse) {
        this.nom = nom;
        this.addresse = adresse;
    }

    public String getNom() {
        return nom;
    }
    public String getAddresse() {
        return addresse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }
}