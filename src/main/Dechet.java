package main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static main.CentreDeTriDAO.actualiserCentreBDD;
import static main.DechetDAO.*;
import static main.TypeDéchetEnum.TypeDechet;

public class Dechet {

    //attributs de la classe Dechet
    private int identifiantDechet;
    private TypeDechetEnum type;

    //modélisation des associations
    private Depot contenir;

    //Getter et Setter
    public int getIdentifiantDechet() {
        return identifiantDechet;
    }
    public void setIdentifiantDechet(int identifiantDechet) {
        this.identifiantDechet = identifiantDechet;
    }

    public TypeDechetEnum getType() {
        return type;
    }
    public void setType(TypeDechetEnum type) {
        this.type = type;
    }

    public Depot getContenir() {
        return contenir;
    }
    public void setContenir(Depot contenir) {
        this.contenir = contenir;
    }

    //Constructeur
    public Dechet(int idDechet, TypeDechetEnum type, Depot contenir) {
        this.identifiantDechet = idDechet;
        this.type = type;
        this.contenir = contenir;
    }

    //Methodes de Classes
    public static Dechet ajouterDechet(TypeDechetEnum type, Depot contenir) {
        Dechet dechet = new Dechet(0, type, contenir);
        ajouterDechetBDD(dechet);
        return dechet;
    }

    public void retirerDechet() {
        retirerDechetBDD(this);
    }
    public void modifierDechet(Dechet dechet, Map<String, Object> modifications){
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for(Map.Entry<String, Object> entry: modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle=="type"){
                TypeDechetEnum type = (TypeDechetEnum) obj;
                dechet.setType(type);
                actualiserDechetBDD(dechet, cle);
            }
            if (cle=="contenir"){
                Depot depot = (Depot) obj;
                dechet.setContenir(depot);
                actualiserDechetBDD(dechet, cle);
            }
        }
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
