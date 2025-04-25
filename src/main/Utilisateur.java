package main;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static main.UtilisateurDAO.*;

public class Utilisateur {
    //Attributs
    private int idUtilisateur;
    private String nom;
    private String prenom;
    private float pointsFidelite;

    //Modélisation des associations
    private List<Depot> posseder;
    private Set<Promotion> utiliser;

    //Constructeur
    public Utilisateur(int idUtilisateur, String nom, String prenom, float pointsFidelite) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.pointsFidelite = pointsFidelite;
    }

    //Getter et Setter
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
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

    public Set<Promotion> getUtiliser() {return utiliser;}

    public void setUtiliser(Set<Promotion> utiliser) {this.utiliser = utiliser;}

    public List<Depot> getPosseder() {return posseder;}

    public void setPosseder(List<Depot> posseder) {this.posseder = posseder;}

    //Méthodes de classe
    public static Utilisateur ajouterUtilisateur(String nom, String prenom, float pointsFidelite){
        Utilisateur utilisateur = new Utilisateur(0, nom, prenom, pointsFidelite);
        ajouterUtilisateurBDD(utilisateur);
        return utilisateur;
    }

    public void supprimerUtilisateur(Utilisateur utilisateur){
        supprimerUtilisateurBDD(utilisateur);
    }

    public void modifierUtilisateur(Map<String, Object> modifications){
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="nom"){
                String nom = (String) obj;
                this.setNom(nom);
                actualiserUtilisateurBDD(this, cle);
            }
            if (cle=="prenom"){
                String prenom = (String) obj;
                this.setPrenom(prenom);
                actualiserUtilisateurBDD(this, cle);
            }
            if (cle=="pointsFidelite"){
                float pointsFidelite = (float) obj;
                this.setPointsFidelite(pointsFidelite);
                actualiserUtilisateurBDD(this, cle);
            }
            if (cle=="posseder"){
                List<Depot> posseder = (List<Depot>) obj;
                this.setPosseder(posseder);
                actualiserUtilisateurBDD(this, cle);
            }
            if (cle=="utiliser"){
                Set<Promotion> utiliser = (Set<Promotion>) obj;
                this.setUtiliser(utiliser);
                actualiserUtilisateurBDD(this, cle);
            }
        }
    }

    public boolean utiliserPoints(Utilisateur utilisateur, Promotion promotion){
        /*Renvoie vrai si la promotion est utilisable et l'utilise le cas échéant sinon renvoie faux ! */
        if (utilisateur.getPointsFidelite() > promotion.getPointsRequis()){
            float points = utilisateur.getPointsFidelite();
            utilisateur.setPointsFidelite(points - promotion.getPointsRequis()); //on considère que l'utilisateur a reçu le produit de la promotion et on retire les points
            actualiserUtilisateurBDD(utilisateur, "pointsFidelite");
            return true;
        } else {
            return false;
        }
    }

    public String consulterHistorique(Utilisateur utilisateur){
        String Historique = "Voici l'historique de l'utilisateur d'identifiant " + utilisateur.getIdUtilisateur() + ":\n";
        List<Depot> depots = utilisateur.getPosseder();
        depots.sort(Comparator
                .comparing(Depot::getDate).reversed()
                .thenComparing(Depot::getHeure).reversed());
        int n = depots.size();
        for(int k = 0; k < n; k++){
            List<Dechet> Dechets = depots.get(k).getContenir();
            Historique.concat("Dépôt n°"+ depots.get(k).getIdentifiantDepot() + " qui contient: \n");
            int m = Dechets.size();
            for (int l=0; l < m; l++){
                Historique.concat("  - Déchet n°"+ Dechets.get(l).getIdentifiantDechet() + " de type " + Dechets.get(l).getType());
            }
        }
        return Historique;
    }


    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Utilisateur utilisateur = (Utilisateur) obj;
        return this.idUtilisateur == utilisateur.idUtilisateur
                && this.nom == utilisateur.nom && this.prenom == utilisateur.prenom
                && this.pointsFidelite == utilisateur.pointsFidelite && this.posseder.equals(utilisateur.posseder)
                && this.utiliser.equals(utilisateur.utiliser);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", pointsFidelite=" + pointsFidelite +
                ", posseder=" + posseder +
                ", utiliser=" + utiliser +
                '}';
    }
}
