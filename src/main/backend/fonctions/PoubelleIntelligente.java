package main.backend.fonctions;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PoubelleIntelligente {
    private int identifiantPoubelle;
    private String emplacement;
    private float capaciteMaximale;
    private TypeDechetEnum.TypeDechet type;
    private float poids;
    private CentreDeTri gerer;
    private Set<Depot> jeter;
    private Set<Dechet> stocker;

    public PoubelleIntelligente(int id,
                                String emplacement,
                                float capaciteMaximale,
                                TypeDechetEnum.TypeDechet type,
                                float poids,
                                CentreDeTri gerer,
                                Set<Depot> jeter,
                                Set<Dechet> stocker) {
        this.identifiantPoubelle = id;
        this.emplacement = emplacement;
        this.capaciteMaximale = capaciteMaximale;
        this.type = type;
        this.poids = poids;
        this.gerer = gerer;
        this.jeter = jeter;
        this.stocker = stocker;
    }

    public PoubelleIntelligente() {

    }

    // getters / setters…


    public int getIdentifiantPoubelle() {return identifiantPoubelle;}
    public void setIdentifiantPoubelle(int identifiantPoubelle) {this.identifiantPoubelle = identifiantPoubelle;}

    public String getEmplacement() {return emplacement;}
    public void setEmplacement(String emplacement) {this.emplacement = emplacement;}

    public float getCapaciteMaximale() {return capaciteMaximale;}
    public void setCapaciteMaximale(float capaciteMaximale) {this.capaciteMaximale = capaciteMaximale;}

    public TypeDechetEnum.TypeDechet getType() {return type;}
    public void setType(TypeDechetEnum.TypeDechet type) {this.type = type;}

    public float getPoids() {return poids;}
    public void setPoids(float poids) {this.poids = poids;}

    public CentreDeTri getGerer() {return gerer;}
    public void setGerer(CentreDeTri gerer) {this.gerer = gerer;}

    public Set<Depot> getJeter() {return jeter;}
    public void setJeter(Set<Depot> jeter) {this.jeter = jeter;}

    public Set<Dechet> getStocker() {return stocker;}
    public void setStocker(Set<Dechet> stocker) {this.stocker = stocker;}

    public boolean notifierCentre() {return poids > capaciteMaximale;}

    public float ajouterPoids(Set<TypeDechetEnum.TypeDechet> dechets) {
        float ajout = 0f;
        for (var t : dechets) {
            ajout += switch (t) {
                case verre    -> TypeDechetEnum.pv;
                case carton   -> TypeDechetEnum.pc;
                case plastique-> TypeDechetEnum.pp;
                case metal    -> TypeDechetEnum.pm;
            };
        }
        this.poids += ajout;
        return ajout;
    }

    public static PoubelleIntelligente ajouterPoubelle(
            String emplacement,
            float capaciteMaximale,
            TypeDechetEnum.TypeDechet type,
            float poids,
            CentreDeTri gerer,
            Set<Depot> jeter,
            Set<Dechet> stocker
    ) {
        return new PoubelleIntelligente(
                0,
                emplacement,
                capaciteMaximale,
                type,
                poids,
                gerer,
                jeter,
                stocker
        );
    }

    public void modifierPoubelle(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "emplacement"      -> this.emplacement = (String)e.getValue();
                case "capaciteMaximale" -> this.capaciteMaximale = (float)e.getValue();
                case "type"             -> this.type = (TypeDechetEnum.TypeDechet)e.getValue();
                case "poids"            -> this.poids = (float)e.getValue();
                case "gerer"            -> this.gerer = (CentreDeTri)e.getValue();
                case "jeter"            -> this.jeter = (Set<Depot>)e.getValue();
                case "stocker"          -> this.stocker = (Set<Dechet>)e.getValue();
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void collecterDechets() {
        this.poids = 0f;
        this.stocker.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoubelleIntelligente)) return false;
        PoubelleIntelligente that = (PoubelleIntelligente) o;
        return identifiantPoubelle == that.identifiantPoubelle
                && Float.compare(that.capaciteMaximale, capaciteMaximale) == 0
                && Float.compare(that.poids, poids) == 0
                && Objects.equals(emplacement, that.emplacement)
                && type == that.type
                && Objects.equals(gerer, that.gerer)
                && Objects.equals(jeter, that.jeter)
                && Objects.equals(stocker, that.stocker);
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
                ", stocker=" + stocker +
                '}';
    }
}
