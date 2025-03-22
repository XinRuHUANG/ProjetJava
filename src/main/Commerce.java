package main;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Commerce {
    private int identifiantCommerce;
    private String nom;

    public int getIdentifiantCommerce() {return identifiantCommerce;}

    public void setIdentifiantCommerce(int identifiantCommerce) {this.identifiantCommerce = identifiantCommerce;}

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    //modélisation des associations
    private Set<CentreDeTri> commercer;
    private Set<CategorieDeProduits> proposer;


    public Set<CentreDeTri> getCommercer() {
        return commercer;
    }

    public void setCommercer(Set<CentreDeTri> commercer) {
        this.commercer = commercer;
    }

    public Set<CategorieDeProduits> getProposer() {
        return proposer;
    }

    public void setProposer(Set<CategorieDeProduits> proposer) {
        this.proposer = proposer;
    }

    public class CentreDeTri {
        private int identifiantCentre;
        private String nom;
        private String addresse;
        //Modélisation des associations
        private PoubelleIntelligente gerer;
        private Set<Commerce> commercer;

        public int getIdentifiantCentre() { return identifiantCentre; }

        public void setIdentifiantCentre(int identifiantCentre) { this.identifiantCentre = identifiantCentre; }

        public String getNom() { return nom; }

        public void setNom(String nom) { this.nom = nom; }

        public String getAddresse() { return addresse; }

        public void setAddresse(String addresse) { this.addresse = addresse; }

        public PoubelleIntelligente getGerer() { return gerer; }

        public void setGerer(PoubelleIntelligente gerer) { this.gerer = gerer; }

        public Set<Commerce> getCommercer() {
            return commercer;
        }

        public void setCommercer(Set<Commerce> commercer) {
            this.commercer = commercer;
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
        }

        public CentreDeTri ajouterCentre(String nom, String adresse) {
            String requete = "SELECT MAX(identifiantCentre) FROM CentreDeTri;";
            ArrayList<String> attributs = new ArrayList<>();
            attributs.add("identifiantCentre");
            List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
            int id = Integer.parseInt(infos.getFirst().get("identifiantCentre"));

            CentreDeTri centre = new CentreDeTri(id, nom, adresse);
            //Création dans la base de données
            requete = "INSERT INTO CentreDeTri(identifiant, nom, adresse) VALUES (" + Integer.toString(id) + "," + nom + "," + "adresse" + ");";
            requete(requete);
            return centre;
        }

        public void retirerCentre() {
            int identifiant = this.identifiantCentre;
            //Suppresion du centre de la base de données
            String requete = "DELETE FROM CentreDeTri WHERE identifiant = " + Integer.toString(identifiant) + ";";
            requete(requete);
        }
    }
}
