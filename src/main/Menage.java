package main;

public class Menage {
    private String nomRepresentant;
    private String prenomRepresentant;

    public Menage(String nomRepresentant, String prenomRepresentant) {
        this.nomRepresentant = nomRepresentant;
        this.prenomRepresentant = prenomRepresentant;
    }

    public String getNomRepresentant() {
        return nomRepresentant;
    }

    public String getPrenomRepresentant() {
        return prenomRepresentant;
    }

    public void setNomRepresentant(String nomRepresentant) {
        this.nomRepresentant = nomRepresentant;
    }

    public void setPrenomRepresentant(String prenomRepresentant) {
        this.prenomRepresentant = prenomRepresentant;
    }
    public static void creerCompte(){}
    public static void consulterCompte(){}
    public static void convertirPoints(){}
    public static void consulterHistorique(){}

}
