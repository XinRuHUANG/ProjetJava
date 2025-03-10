package ProjetJava;

public class CentreDeTri {
    private String nom;
    private String addresse;

    public CentreDeTri() {
    }

    public CentreDeTri(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }
}