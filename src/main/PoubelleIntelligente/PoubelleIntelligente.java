package main.PoubelleIntelligente;

public class PoubelleIntelligente {
    private int identifiant;
    private String emplacement;
    private int capaciteMaximale;

    public PoubelleIntelligente(int identifiant, String emplacement, int capaciteMaximale){
        this.identifiant = identifiant;
        this.emplacement = emplacement;
        this.capaciteMaximale = capaciteMaximale;
    }

    public int getIdentifiant(){return this.identifiant;}
    public String getEmplacement(){return this.emplacement;}
    public int getCapaciteMaximale(){return this.capaciteMaximale;}

    public void setIdentifiant(int id){this.identifiant = id;}
    public void setEmplacement(String adresse){this.emplacement = adresse;}
    public void setCapaciteMaximale(int CapaciteMax){this.capaciteMaximale = CapaciteMax;}

    public static void identifier(){}
    public static void mesurerVolume(){}
    public static void mesurerPoids(){}
    public static void verifierNature(){}
    public static void calculerPenalite(){}
    public static void attribuerPoints(){}
    public static void retirerPoints(){}
    public static void notifierCentre(){}

}
