package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Utilisateur {
    private int identifiantUtilisateur;
    private String nom;
    private String prenom;
    private float pointsFidelite;
    //modélisation des associations
    private Set<Depot> posseder;
    private Set<Promotion> utiliser;

    public Utilisateur(int identifiantUtilisateur, String nom, String prenom, float pointsFidelite) {
        this.identifiantUtilisateur = identifiantUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.pointsFidelite = pointsFidelite;
    }

    public int getIdentifiantUtilisateur() {
        return identifiantUtilisateur;
    }

    public void setIdentifiantUtilisateur(int identifiantUtilisateur) {
        this.identifiantUtilisateur = identifiantUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public float getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(float pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public Set<Depot> getPosseder() {
        return posseder;
    }

    public void setPosseder(Set<Depot> posseder) {
        this.posseder = posseder;
    }

    public Set<Promotion> getUtiliser() {
        return utiliser;
    }

    public void setUtiliser(Set<Promotion> utiliser) {
        this.utiliser = utiliser;
    }

    public static Utilisateur creerCompte(String nom, String prenom, float pointsFidelite){
        String requete = "SELECT MAX(identifiantUtilisateur) FROM Utilisateur;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantUtilisateur");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantUtilisateur"));

        Utilisateur util = new Utilisateur(id, nom, prenom, pointsFidelite);
        //Création dans la base de données
        requete = "INSERT INTO Utilisateur(identifiantUtilisateur, nom, prenom, pointsFidelite) VALUES ("+Integer.toString(id)+","+nom+","+prenom+","+ Float.toString(pointsFidelite)+");";
        requete(requete);
        return util;
    }
}
