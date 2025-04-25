package main;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static main.ContratDAO.actualiserContratBDD;
import static main.PoubelleIntelligenteDAO.*;
import static main.TypeDechetEnum.*;
import static main.TypeDechetEnum.TypeDechet.*;

public class PoubelleIntelligente {
    private int identifiantPoubelle;
    private String emplacement;
    private float capaciteMaximale;
    private TypeDechet type;
    private float poids;

    //modélisation des associations
    private CentreDeTri gerer;
    private Set<Depot> jeter;

    public PoubelleIntelligente(int identifiantPoubelle, String emplacement, float capaciteMaximale, TypeDechet type, float poids, CentreDeTri gerer, Set<Depot> jeter) {
        this.identifiantPoubelle = identifiantPoubelle;
        this.emplacement = emplacement;
        this.capaciteMaximale = capaciteMaximale;
        this.type = type;
        this.poids = poids;
        this.gerer = gerer;
        this.jeter = jeter;
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

    public CentreDeTri getGerer() {return gerer;}
    public void setGerer(CentreDeTri gerer) {this.gerer = gerer;}

    public Set<Depot> getJeter() {
        return jeter;
    }
    public void setJeter(Set<Depot> jeter) {
        this.jeter = jeter;
    }

    public float getPoids() {return poids;}
    public void setPoids(float poids) {this.poids = poids;}

    public static PoubelleIntelligente ajouterPoubelle(String emplacement, float capaciteMaximale, TypeDechet type, float poids, CentreDeTri gerer, Set<Depot> jeter) {
        PoubelleIntelligente poubelle = new PoubelleIntelligente(0,emplacement, capaciteMaximale, type, poids, gerer, jeter);
        ajouterPoubelleBDD(poubelle);
        return poubelle;
    }

    public void retirerPoubelle() {
        supprimerPoubelleBDD(this);
    }

    public void modifierPoubelle(Map<String, Object> modifications) {
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for (Map.Entry<String, Object> entry : modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle == "emplacement") {
                String emplacement = (String) obj;
                this.setEmplacement(emplacement);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
            if (cle == "capaciteMaximale") {
                float capaciteMaximale = (float) obj;
                this.setCapaciteMaximale(capaciteMaximale);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
            if (cle == "type") {
                TypeDechet type = (TypeDechet) obj;
                this.setType(type);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
            if (cle == "poids") {
                float poids = (float) obj;
                this.setPoids(poids);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
            if (cle == "gerer") {
                CentreDeTri gerer = (CentreDeTri) obj;
                this.setGerer(gerer);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
            if (cle == "jeter") {
                Set<Depot> jeter = (Set<Depot>) obj;
                this.setJeter(jeter);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
        }
    }

    //On va juste vider la poubelle "fictivement"
    public void collecterDechets() {
        //vidage de la poubelle côté JAVA
        this.poids = 0;
        //vidage de la poubelle côté SQL
        collecterDechetsBDD(this);
    }

    //méthode non traitée
    public void statistiquerDechets() {
    }

    public boolean notifierCentre(PoubelleIntelligente poubelleIntelligente){
        return (this.poids > this.capaciteMaximale);
    }

    public float ajouterPoids(Set<TypeDechetEnum> dechets){
        float poids = 0;
        for (TypeDechetEnum type: dechets){
            if (type.equals(verre)){
                poids += pv;
            } else if (type.equals(carton)){
                poids += pc;
            } else if (type.equals(plastique)){
                poids += pp;
            } else if (type.equals(metal)){
                poids += pm;
            }
        }
        this.poids += poids;
        actualiserPoidsBDD(this);
        return poids;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        PoubelleIntelligente poubelleIntelligente = (PoubelleIntelligente) obj;
        return this.identifiantPoubelle == poubelleIntelligente.identifiantPoubelle
                && this.emplacement == poubelleIntelligente.emplacement && this.capaciteMaximale == poubelleIntelligente.capaciteMaximale
                && this.type == poubelleIntelligente.type && this.poids == poubelleIntelligente.poids &&
                this.gerer.equals(poubelleIntelligente.gerer) && this.jeter.equals(poubelleIntelligente.jeter);
    }

    @Override
    public String toString() {
        return "PoubelleIntelligente{" +
                "identifiantPoubelle=" + identifiantPoubelle +
                ", emplacement='" + emplacement + '\'' +
                ", capaciteMaximale=" + capaciteMaximale +
                ", type=" + type +
                ", poids=" + poids +
                ", gerer=" + gerer +
                ", jeter=" + jeter +
                '}';
    }
}

