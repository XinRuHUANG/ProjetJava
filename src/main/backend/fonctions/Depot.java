package main.backend.fonctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Depot {
    private int identifiantDepot;
    private LocalDate date;
    private LocalTime heure;
    private float points;
    private Set<PoubelleIntelligente> jeter;
    private Utilisateur posseder;
    private List<Dechet> contenir;

    public Depot(int id, LocalDate date, LocalTime heure, float points) {
        this.identifiantDepot = id;
        this.date = date;
        this.heure = heure;
        this.points = points;
        this.jeter = new HashSet<>();     // Correction importante : éviter le null
        this.contenir = new ArrayList<>(); // Correction importante : éviter le null
    }

    public int getIdentifiantDepot() { return identifiantDepot; }
    public void setIdentifiantDepot(int id) { this.identifiantDepot = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate d) { this.date = d; }

    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime h) { this.heure = h; }

    public float getPoints() { return points; }
    public void setPoints(float p) { this.points = p; }

    public Set<PoubelleIntelligente> getJeter() { return jeter; }
    public void setJeter(Set<PoubelleIntelligente> j) { this.jeter = j; }

    public Utilisateur getPosseder() { return posseder; }
    public void setPosseder(Utilisateur u) { this.posseder = u; }

    public List<Dechet> getContenir() { return contenir; }
    public void setContenir(List<Dechet> c) { this.contenir = c; }

    public static Depot creerDepot(Utilisateur u, List<Dechet> dechets) {
        Depot depot = new Depot(0, LocalDate.now(), LocalTime.now(), 0f);
        depot.setPosseder(u);
        depot.setContenir(dechets);
        return depot;
    }

    public void modifierDepot(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "date"    -> setDate((LocalDate)e.getValue());
                case "heure"   -> setHeure((LocalTime)e.getValue());
                case "points"  -> setPoints((float)e.getValue());
                case "jeter"   -> setJeter((Set<PoubelleIntelligente>)e.getValue());
                case "posseder"-> setPosseder((Utilisateur)e.getValue());
                case "contenir"-> setContenir((List<Dechet>)e.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void supprimerDepot() {
        // rien
    }

    public void ajouterDechetDepot(Dechet d) {
        this.contenir.add(d);
    }

    public void retirerDechetDepot(Dechet d) {
        this.contenir.remove(d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Depot)) return false;
        Depot that = (Depot) o;
        return identifiantDepot == that.identifiantDepot
                && Float.compare(that.points, points) == 0
                && Objects.equals(date, that.date)
                && Objects.equals(heure, that.heure)
                && Objects.equals(jeter, that.jeter)
                && Objects.equals(posseder, that.posseder)
                && Objects.equals(contenir, that.contenir);
    }

    @Override
    public String toString() {
        return "Depot{" +
                "identifiantDepot=" + identifiantDepot +
                ", date=" + date +
                ", heure=" + heure +
                ", points=" + points +
                ", jeter=" + jeter +
                ", posseder=" + posseder +
                ", contenir=" + contenir +
                '}';
    }
}
