package main.PoubelleIntelligente;
import static main.outils.connexionSQL.requete;

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
    public boolean verifierNature(String typeDechet, String typePoubelle){
        return typeDechet.equals(typePoubelle);
    }
    public int attribuerPoints(int idCompte, int quantite){
        return quantite * 10;
    }
    public int retirerPoints(int idCompte, int quantite){
        return -quantite * 5;
    }
    public int calculerPenalite(int idCompte, int quantite){
        return attribuerPoints(idCompte, quantite) + retirerPoints(idCompte, quantite);
    }
    public void attribuerPoints(int idCompte, int points){
        String requete = "UPDATE Compte SET pointsFidélité = pointsFidélité + " + Integer.toString(points) + " WHERE idCompte = " + idCompte + ";";
        requete(requete);
    }

    public static void notifierCentre(){}

}
