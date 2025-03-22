package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.TypeDéchetEnum.*;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligente {
    private int identifiantPoubelle;
    private String emplacement;
    private float capaciteMaximale;
    private TypeDechet type;
    //modélisation des associations
    private Set<CentreDeTri> gerer;
    private Set<Depot> jeter;

    public PoubelleIntelligente(int identifiantPoubelle, String emplacement, float capaciteMaximale, TypeDechet type) {
        this.identifiantPoubelle = identifiantPoubelle;
        this.emplacement = emplacement;
        this.capaciteMaximale = capaciteMaximale;
        this.type = type;
    }

    public PoubelleIntelligente() {
    }

    public int getIdentifiantPoubelle() {
        return identifiantPoubelle;
    }

    public void setIdentifiantPoubelle(int identifiantPoubelle) {
        this.identifiantPoubelle = identifiantPoubelle;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public float getCapaciteMaximale() {
        return capaciteMaximale;
    }

    public void setCapaciteMaximale(float capaciteMaximale) {
        this.capaciteMaximale = capaciteMaximale;
    }

    public TypeDechet getType() {
        return type;
    }

    public void setType(TypeDechet type) {
        this.type = type;
    }

    public Set<CentreDeTri> getGerer() {
        return gerer;
    }

    public void setGerer(Set<CentreDeTri> gerer) {
        this.gerer = gerer;
    }

    public Set<Depot> getJeter() {
        return jeter;
    }

    public void setJeter(Set<Depot> jeter) {
        this.jeter = jeter;
    }

    public static PoubelleIntelligente ajouterPoubelle(TypeDechet type, String emplacement, float capaciteMaximale) {
        //récupération du dernier identifiant utilisé
        String requete = "SELECT MAX(identifiantPoubelle) FROM PoubelleIntelligente;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPoubelle");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantPoubelle"));

        PoubelleIntelligente poubelle = new PoubelleIntelligente(id + 1, emplacement, capaciteMaximale, type);
        requete = "INSERT INTO PoubelleIntelligente(identifiant, type, emplacement, capacitéMaximale) VALUES (" + Integer.toString(id + 1) + "," + type.toString() + "," + emplacement + "," + Float.toString(capaciteMaximale) + ");";
        requete(requete);
        return poubelle;
    }

    public static void retirerPoubelle(PoubelleIntelligente poubelle) {
        int id = poubelle.identifiantPoubelle;
        String requete = "DELETE FROM PoubelleIntelligente WHERE identifiant =" + Integer.toString(id) + ";";
        requete(requete);
    }

    //On va juste vider la poubelle "fictivement"
    public static void collecterDechets(int id) {
        String requete = "DELETE FROM stocker WHERE identifiantPoubelle = " + Integer.toString(id) + ";";
    }

    //méthodes non traitées
    public static void statistiquerDechets() {
    }
    /*
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

    //méthodes non implémentées
    public void mesurerPoids(){}
*/
}

