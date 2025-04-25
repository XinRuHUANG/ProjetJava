package main;

import java.util.Map;

import static main.DechetDAO.*;
import static main.TypeDechetEnum.*;

public class Dechet {

    //attributs de la classe Dechet
    private int identifiantDechet;
    private TypeDechet type;

    //modélisation des associations
    private Depot contenir;

    //Getter et Setter
    public int getIdentifiantDechet() {
        return identifiantDechet;
    }
    public void setIdentifiantDechet(int identifiantDechet) {
        this.identifiantDechet = identifiantDechet;
    }

    public TypeDechet getType() {
        return type;
    }
    public void setType(TypeDechet type) {
        this.type = type;
    }

    public Depot getContenir() {
        return contenir;
    }
    public void setContenir(Depot contenir) {
        this.contenir = contenir;
    }

    //Constructeur
    public Dechet(int idDechet, TypeDechet type, Depot contenir) {
        this.identifiantDechet = idDechet;
        this.type = type;
        this.contenir = contenir;
    }

    //Methodes de Classes
    public static Dechet ajouterDechet(TypeDechet type, Depot contenir) {
        Dechet dechet = new Dechet(0, type, contenir);
        ajouterDechetBDD(dechet);
        return dechet;
    }

    public void supprimerDechet() {
        supprimerDechetBDD(this);
    }
    public void modifierDechet(Map<String, Object> modifications){
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="type"){
                TypeDechet type = (TypeDechet) obj;
                this.setType(type);
                actualiserDechetBDD(this, cle);
            }
            if (cle=="contenir"){
                Depot depot = (Depot) obj;
                this.setContenir(depot);
                actualiserDechetBDD(this, cle);
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Dechet dechet = (Dechet) obj;
        return this.identifiantDechet == dechet.identifiantDechet &&
                this.type == dechet.type && this.contenir.equals(dechet.contenir);
    }

    @Override
    public String toString() {
        return "Dechet{" +
                "identifiantDechet=" + identifiantDechet +
                ", type=" + type +
                ", contenir=" + contenir +
                '}';
    }
}
