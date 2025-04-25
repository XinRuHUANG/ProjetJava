package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static main.CentreDeTriDAO.actualiserCentreBDD;
import static main.DechetDAO.ajouterDechetBDD;
import static main.DepotDAO.*;
import static main.outils.connexionSQL.requete;
import static main.TypeDechetEnum.TypeDechet;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Depot {
    private int identifiantDepot;
    private static LocalDate date;
    private LocalTime heure;
    private float points;

    //modélisation des associations
    private Set<PoubelleIntelligente> jeter; //un dépot peut être répartie entre plusieurs poubelles (cf. tri)
    private Utilisateur posseder;
    private List<Dechet> contenir;

    public Depot(int identifiantDepot, LocalDate date, LocalTime heure, float points) {
        this.identifiantDepot = identifiantDepot;
        this.date = date;
        this.heure = heure;
        this.points = points;
    }

    public int getIdentifiantDepot() {
        return identifiantDepot;
    }
    public void setIdentifiantDepot(int identifiantDepot) {
        this.identifiantDepot = identifiantDepot;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }
    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public float getPoints() {
        return points;
    }
    public void setPoints(float points) {
        this.points = points;
    }

    public Set<PoubelleIntelligente> getJeter() {
        return jeter;
    }
    public void setJeter(Set<PoubelleIntelligente> jeter) {
        this.jeter = jeter;
    }

    public Utilisateur getPosseder() {
        return posseder;
    }
    public void setPosseder(Utilisateur posseder) {
        this.posseder = posseder;
    }

    public List<Dechet> getContenir() {
        return contenir;
    }
    public void setContenir(List<Dechet> contenir) {
        this.contenir = contenir;
    }

    public static Depot creerDepot(Utilisateur util, List<Dechet> dechets){
        LocalDate Date = LocalDate.now();
        LocalTime heure = LocalTime.now();
        Depot depot = new Depot(0, Date, heure, 0);
        //Création dans la base de données
        creerDepotBDD(depot);
        return depot;
    }

    public void supprimerDepot(){
        supprimerDepotBDD(this);
    }

    public void modifierDepot(Map<String, Object> modifications) {
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="date"){
                LocalDate date = (LocalDate) obj;
                this.setDate(date);
                actualiserDepotBDD(this, cle);
            }
            if (cle=="heure"){
                LocalTime heure = (LocalTime) obj;
                this.setHeure(heure);
                actualiserDepotBDD(this, cle);
            }
            if (cle=="points"){
                float points = (float) obj;
                this.setPoints(points);
                actualiserDepotBDD(this, cle);
            }
            if (cle=="jeter"){
                Set<PoubelleIntelligente> jeter = (Set<PoubelleIntelligente>) obj;
                this.setJeter(jeter);
                actualiserDepotBDD(this, cle);
            }
            if (cle=="posseder"){
                Utilisateur posseder = (Utilisateur) obj;
                this.setPosseder(posseder);
                actualiserDepotBDD(this, cle);
            }
            if (cle=="contenir"){
                List<Dechet> contenir = (List<Dechet>) obj;
                this.setContenir(contenir);
                actualiserDepotBDD(this, cle);
            }
        }
    }


    public void ajouterDechetDepot(Dechet dechet){
        //ajout du déchet dans java
        this.contenir.add(dechet);
        //ajout du déchet dans la BDD
        ajouterDechetDepotBDD(this, dechet);
    }

    public void retirerDechetDepot(Dechet dechet){
        //suppression du déchet dans java
        this.contenir.remove(dechet);
        //suppresion du déchet dans la BDD
        supprimerDechetDepotBDD(this, dechet);
    }

    /*Dans le prochain programme, il faut fournir une liste de poubelles "poubelles "et une liste de listes d'indices "indices_dechets".
    La présence de l'indice i dans la jème liste de "indices_déchets" signifie que le ième déchet de "this.contenir" est jeté dans la jème poubelle de la liste de poubelles.
    */

    public static boolean verifierNature(Dechet dechet, PoubelleIntelligente poubelle){
        return dechet.getType() == poubelle.getType();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Depot depot = (Depot) obj;
        return this.identifiantDepot == depot.identifiantDepot &&
                this.date.equals(depot.date) && this.heure.equals(depot.heure)
                && this.points == depot.points && this.jeter.equals(depot.jeter)
                && this.posseder.equals(depot.posseder) && this.contenir.equals(depot.contenir);
    }

    @Override
    public String toString() {
        return "Depot{" +
                "identifiantDepot=" + identifiantDepot +
                ", heure=" + heure +
                ", points=" + points +
                ", jeter=" + jeter +
                ", posseder=" + posseder +
                ", contenir=" + contenir +
                '}';
    }
}
