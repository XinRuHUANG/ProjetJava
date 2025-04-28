package main.backend.fonctions;

import java.util.Map;
import java.util.Objects;

public class Dechet {
    private int identifiantDechet;
    private TypeDechetEnum.TypeDechet type;
    private Depot contenir;
    private PoubelleIntelligente stocker;

    public Dechet(int id,
                  TypeDechetEnum.TypeDechet type,
                  Depot contenir,
                  PoubelleIntelligente stocker) {
        this.identifiantDechet = id;
        this.type = type;
        this.contenir = contenir;
        this.stocker = stocker;
    }

    public int getIdentifiantDechet() { return identifiantDechet; }
    public void setIdentifiantDechet(int id) { this.identifiantDechet = id; }

    public TypeDechetEnum.TypeDechet getType() { return type; }
    public void setType(TypeDechetEnum.TypeDechet t) { this.type = t; }

    public Depot getContenir() { return contenir; }
    public void setContenir(Depot d) { this.contenir = d; }

    public PoubelleIntelligente getStocker() { return stocker; }
    public void setStocker(PoubelleIntelligente p) { this.stocker = p; }

    public static Dechet ajouterDechet(TypeDechetEnum.TypeDechet type,
                                       Depot d,
                                       PoubelleIntelligente p) {
        return new Dechet(0, type, d, p);
    }

    public void modifierDechet(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "type"     -> setType((TypeDechetEnum.TypeDechet)e.getValue());
                case "contenir" -> setContenir((Depot)e.getValue());
                case "stocker"  -> setStocker((PoubelleIntelligente)e.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void supprimerDechet() {
        // plus d'appel DAO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dechet)) return false;
        Dechet that = (Dechet) o;
        return identifiantDechet == that.identifiantDechet
                && type == that.type
                && Objects.equals(contenir, that.contenir)
                && Objects.equals(stocker, that.stocker);
    }

    @Override
    public String toString() {
        return "Dechet{" +
                "identifiantDechet=" + identifiantDechet +
                ", type=" + type +
                ", contenir=" + contenir +
                ", stocker=" + stocker +
                '}';
    }
}
