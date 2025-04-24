package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.CentreDeTriDAO.supprimerCentreBDD;
import static main.PoubelleIntelligenteDAO.*;
import static main.TypeDechetEnum.*;
import static main.TypeDechetEnum.TypeDechet.*;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class PoubelleIntelligente {
    private int identifiantPoubelle;
    private String emplacement;
    private float capaciteMaximale;
    private TypeDechet type;
    private float poids;

    //modélisation des associations
    private CentreDeTri gerer;
    private Set<Depot> jeter;

    public PoubelleIntelligente(String emplacement, float capaciteMaximale, TypeDechet type, float poids, CentreDeTri gerer, Set<Depot> jeter) {
        this.identifiantPoubelle = 0;
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


    @Override
    public String toString() {
        return "PoubelleIntelligente{" +
                "identifiantPoubelle=" + identifiantPoubelle +
                ", emplacement='" + emplacement + '\'' +
                ", capaciteMaximale=" + capaciteMaximale +
                ", type=" + type +
                ", gerer=" + gerer +
                ", jeter=" + jeter +
                '}';
    }

    public static PoubelleIntelligente ajouterPoubelle(String emplacement, float capaciteMaximale, TypeDechet type, float poids, CentreDeTri gerer, Set<Depot> jeter) {
        PoubelleIntelligente poubelle = new PoubelleIntelligente(emplacement, capaciteMaximale, type, poids, gerer, jeter);
        ajouterPoubelleBDD(poubelle);
        return poubelle;
    }

    public void retirerPoubelle() {
        supprimerPoubelleBDD(this);
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

}

