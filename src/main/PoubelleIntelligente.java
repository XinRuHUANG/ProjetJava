package main;

import java.util.Map;
import java.util.Set;

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
    private Set<Dechet> stocker;

    public PoubelleIntelligente(int identifiantPoubelle, String emplacement, float capaciteMaximale, TypeDechet type, float poids, CentreDeTri gerer, Set<Depot> jeter, Set<Dechet> stocker) {
        this.identifiantPoubelle = identifiantPoubelle;
        this.emplacement = emplacement;
        this.capaciteMaximale = capaciteMaximale;
        this.type = type;
        this.poids = poids;
        this.gerer = gerer;
        this.jeter = jeter;
        this.stocker = stocker;
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

    public Set<Dechet> getStocker() {return stocker;}

    public void setStocker(Set<Dechet> stocker) {this.stocker = stocker;}

    //Methode de classe
    public static PoubelleIntelligente ajouterPoubelle(String emplacement, float capaciteMaximale, TypeDechet type, float poids, CentreDeTri gerer, Set<Depot> jeter, Set<Dechet> stocker) {
        /*Crée une nouvelle poubelle intelligente et l'ajoute à la base de données.
         *
         * @param emplacement L'emplacement de la poubelle.
         * @param capaciteMaximale La capacité maximale de la poubelle (en kg).
         * @param type Le type de déchet accepté par la poubelle.
         * @param poids Le poids actuel des déchets dans la poubelle.
         * @param gerer Le centre de tri responsable de cette poubelle.
         * @param jeter Les dépôts liés à cette poubelle.
         * @param stocker Les déchets actuellement stockés dans la poubelle.
         * @return La poubelle nouvellement créée.
         */
        PoubelleIntelligente poubelle = new PoubelleIntelligente(0, emplacement, capaciteMaximale, type, poids, gerer, jeter, stocker);
        ajouterPoubelleBDD(poubelle);
        return poubelle;
    }

    public void retirerPoubelle() {
        /*Supprime cette poubelle de la base de données.
         */
        supprimerPoubelleBDD(this);
    }

    public void modifierPoubelle(Map<String, Object> modifications) {
        /* Modifie les attributs de la poubelle selon les valeurs spécifiées dans la map.
         *
         * @param modifications Une map contenant les attributs à modifier :
         *                      {"emplacement", "capaciteMaximale", "type", "poids", "gerer", "jeter", "stocker"}.
         */
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
            if (cle == "stocker") {
                Set<Dechet> stocker = (Set<Dechet>) obj;
                this.setStocker(stocker);
                actualiserPoubelleIntelligenteBDD(this, cle);
            }
        }
    }

    public void collecterDechets() {
        /*Vide la poubelle de manière logique (réinitialise le poids et efface les déchets stockés).
        */
        this.poids = 0;
        this.stocker.clear();
        collecterDechetsBDD(this);
    }

    /**
     * Méthode non implémentée — prévue pour générer des statistiques sur les déchets.
     */
    public void statistiquerDechets() {
        // À implémenter
    }

    public boolean notifierCentre(PoubelleIntelligente poubelleIntelligente) {
        /*Notifie le centre de tri si la poubelle dépasse sa capacité maximale.
         *
         * @param poubelleIntelligente La poubelle à évaluer.
         * @return true si la capacité est dépassée, false sinon.
         */
        return (this.poids > this.capaciteMaximale);
    }

    public float ajouterPoids(Set<TypeDechetEnum> dechets) {
        /*Ajoute le poids des types de déchets spécifiés et met à jour le poids total de la poubelle.
         *
         * @param dechets Un ensemble de types de déchets à ajouter.
         * @return Le poids total ajouté.
         */
        float poids = 0;
        for (TypeDechetEnum type : dechets) {
            if (type.equals(verre)) {
                poids += pv;
            } else if (type.equals(carton)) {
                poids += pc;
            } else if (type.equals(plastique)) {
                poids += pp;
            } else if (type.equals(metal)) {
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

