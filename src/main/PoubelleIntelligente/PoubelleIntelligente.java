package main.PoubelleIntelligente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligente {
    private int identifiant;
    private String emplacement;
    private int capaciteMaximale;

    //Constantes de poids (évoquées dans le sujet)
    public static final int pp = 14;
    public static final int pv = 16;
    public static final int pc = 19;
    public static final int pm = 32;

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
    public static int calculerPoids(int idPoubelle){
        int somme = 0;
        ArrayList<String> attributs = new ArrayList<>();

        attributs.add("id");
        attributs.add("plastique");
        attributs.add("verre");
        attributs.add("carton");
        attributs.add("metaux");

        //requête
        String requete = "SELECT x.idPoubelleIntelligente as id," +
                "c.qtePlastique as plastique, c.qteVerre as verre, c.qteCarton as carton, c.qteMetaux as metaux FROM " +
                "(SELECT p.idPoubelleIntelligente FROM PoubelleIntelligente as p JOIN historiqueDepot " +
                "as h ON h.idPoubelleIntelligente = p.idPoubelleIntelligente) as x JOIN x.idCorbeille = c.idCorbeille WHERE x.idPoubelleIntelligente = "
                + Integer.toString(idPoubelle) + ";";
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);

        //traitement des données et somme
        for(HashMap<String,String> ligne: infos){
            String idString = ligne.get("id");
            if (Integer.parseInt(idString) == idPoubelle){
                String plastiqueString = ligne.get("plastique");
                String verreString = ligne.get("verre");
                String cartonString = ligne.get("carton");
                String metauxString = ligne.get("metaux");
                somme = somme + Integer.parseInt(plastiqueString)*pp + Integer.parseInt(verreString)*pv + Integer.parseInt(cartonString)*pc + Integer.parseInt(metauxString)*pm;
            }
        }
        return somme;
    }
    public boolean verifierNature(String typeDechet, String typePoubelle){
        return typeDechet.equals(typePoubelle);
    }
    public int ajouterPoints(int idCompte, int quantite){
        return quantite * 10;
    }
    public int retirerPoints(int idCompte, int quantite){
        return -quantite * 5;
    }
    public int calculerPenalite(int idCompte, int quantite){
        return ajouterPoints(idCompte, quantite) + retirerPoints(idCompte, quantite);
    }
    public void attribuerPoints(int idCompte, int points){
        String requete = "UPDATE Compte SET pointsFidélité = pointsFidélité + " + Integer.toString(points) + " WHERE idCompte = " + idCompte + ";";
        requete(requete);
    }
    public static boolean notifierCentre(int idPoubelle){
        //récupération du poidsMax
        String requete = "SELECT poidsMax FROM PoubelleIntelligente WHERE idPoubelleIntelligente = " + Integer.toString(idPoubelle) + ";";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("poidsMax");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);

        String poidsMaxString = (infos.get(0)).get(0);
        int poidsTotal = calculerPoids(idPoubelle);
        return (Integer.parseInt(poidsMaxString) < poidsTotal);
    }
}
