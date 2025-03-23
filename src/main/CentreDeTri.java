package main;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CentreDeTri {
    private int identifiantCentre;
    private String nom;
    private String addresse;
    //Modélisation des associations
    private PoubelleIntelligente gerer;
    private Set<Commerce> commercer;

    public int getIdentifiantCentre() {
        return identifiantCentre;
    }

    public void setIdentifiantCentre(int identifiantCentre) {
        this.identifiantCentre = identifiantCentre;
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

    public PoubelleIntelligente getGerer() {
        return gerer;
    }

    public void setGerer(PoubelleIntelligente gerer) {
        this.gerer = gerer;
    }

    public Set<Commerce> getCommercer() {
        return commercer;
    }

    public void setCommercer(Set<Commerce> commercer) {
        this.commercer = commercer;
    }

    @Override
    public String toString() {
        return "CentreDeTri{" +
                "identifiantCentre=" + identifiantCentre +
                ", nom='" + nom + '\'' +
                ", addresse='" + addresse + '\'' +
                ", gerer=" + gerer +
                ", commercer=" + commercer +
                '}';
    }

    public CentreDeTri(int identifiantCentre, String nom, String addresse, PoubelleIntelligente gerer, Set<Commerce> commercer) {
        this.identifiantCentre = identifiantCentre;
        this.nom = nom;
        this.addresse = addresse;
        this.gerer = gerer;
        this.commercer = commercer;
    }
    public CentreDeTri(int identifiantCentre, String nom, String addresse) {
        this.identifiantCentre = identifiantCentre;
        this.nom = nom;
        this.addresse = addresse;
        this.gerer = new PoubelleIntelligente();
        this.commercer = new HashSet<Commerce>();
    }

    public static CentreDeTri ajouterCentre(String nom, String adresse){

        String requete = "SELECT MAX(identifiantCentre) FROM CentreDeTri;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCentre");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantCentre"));

        CentreDeTri centre = new CentreDeTri(id+1, nom, adresse);
        //Création dans la base de données
        requete = "INSERT INTO CentreDeTri(identifiant, nom, adresse) VALUES ("+Integer.toString(id+1)+","+nom+","+"adresse"+");";
        requete(requete);
        return centre;
    }

    public void retirerCentre(){
        int identifiant = this.identifiantCentre;
        //Suppresion du centre de la base de données
        String requete = "DELETE FROM CentreDeTri WHERE identifiantCentre = " + Integer.toString(identifiant) + ";";
        requete(requete);
    }


}