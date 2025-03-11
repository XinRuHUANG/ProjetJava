package main;

import static main.outils.connexionSQL.requete;

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

    public static void ajouterPoubelle(String type, String emplacement, int capaciteMaximale){
        String requete = "INSERT INTO <Poubelle> (type, emplacement, capacitéMaximale) VALUES ("+type+","+emplacement+","+Integer.toString(capaciteMaximale)+");";
        requete(requete);
    }
    public static void retirerPoubelle(int id){
        String requete = "DELETE FROM <Poubelle> WHERE <idPoubelle> = " + Integer.toString(id) + ";";
        requete(requete);
    }
    //Normalement il faudrait récupérer le contenu de la poubelle puis changer la base de données mais on va juste changer
    public static void collecterDechets(int id){
        String requete = "UPDATE <Poubelle> SET volume = 0 WHERE idPoubelle = " + Integer.toString(id) + ";";
    }
    public static void statistiquerDechets(){}
}