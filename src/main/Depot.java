package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javafx.beans.property.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import main.util.LocalDateAdapter;

import static main.DepotDAO.*;

public class Depot {
    private final IntegerProperty identifiantDepot = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> heure = new SimpleObjectProperty();
    private final FloatProperty points = new SimpleFloatProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final FloatProperty poids = new SimpleFloatProperty();

    //modélisation des associations
    private Set<PoubelleIntelligente> jeter; //un dépot peut être répartie entre plusieurs poubelles (cf. tri)
    private Utilisateur posseder;
    private List<Dechet> contenir;

    public Depot(int identifiantDepot, LocalDate date, LocalTime heure, float points, String type, float poids) {
        this.identifiantDepot.set(identifiantDepot);
        this.date.set(date);
        this.heure.set(heure);
        this.points.set(points);
        this.type.set(type);
        this.poids.set(poids);
    }

    //Getters et Setters
    public int getIdentifiantDepot() {
        return identifiantDepot.get();
    }
    public void setIdentifiantDepot(int identifiantDepot) {
        this.identifiantDepot.set(identifiantDepot);
    }

    public IntegerProperty idProperty(){ return identifiantDepot; }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date.get();
    }
    public void setDate(LocalDate date) {
        this.date.set(date);
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public LocalTime getHeure() {
        return heure.get();
    }
    public void setHeure(LocalTime heure) {
        this.heure.set(heure);
    }
    public ObjectProperty<LocalTime> heureProperty(){ return heure; }

    public float getPoints() {
        return points.get();
    }

    public void setPoints(float points) {
        this.points.set(points);
    }

    public FloatProperty pointsProperty() {
        return points;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty(){ return type; }

    public float getPoids() {
        return poids.get();
    }

    public void setPoids(float poids) {
        this.poids.set(poids);
    }

    public FloatProperty poidsProperty(){ return poids; }

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
        LocalDate dateProperty = LocalDate.now();
        LocalTime heure = LocalTime.now();
        Depot depot = new Depot(0, dateProperty, heure, 0.0f, "verre", 0.5f);
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
