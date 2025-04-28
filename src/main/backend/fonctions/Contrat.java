package main.backend.fonctions;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class Contrat {
    private int idContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String clauses;
    private CentreDeTri commercer;
    private Commerce commerce;
    private Promotion definir;

    public Contrat(int id, LocalDate d1, LocalDate d2,
                   String clauses,
                   CentreDeTri ct,
                   Commerce cm,
                   Promotion def) {
        this.idContrat = id;
        this.dateDebut = d1;
        this.dateFin = d2;
        this.clauses = clauses;
        this.commercer = ct;
        this.commerce = cm;
        this.definir = def;
    }

    public int getIdContrat() { return idContrat; }
    public void setIdContrat(int id) { this.idContrat = id; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate d) { this.dateDebut = d; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate d) { this.dateFin = d; }

    public String getClauses() { return clauses; }
    public void setClauses(String c) { this.clauses = c; }

    public CentreDeTri getCommercer() { return commercer; }
    public void setCommercer(CentreDeTri ct) { this.commercer = ct; }

    public Commerce getCommerce() { return commerce; }
    public void setCommerce(Commerce cm) { this.commerce = cm; }

    public Promotion getDefinir() { return definir; }
    public void setDefinir(Promotion p) { this.definir = p; }

    public static Contrat ajouterContrat(LocalDate d1, LocalDate d2,
                                         String clauses,
                                         CentreDeTri ct,
                                         Commerce cm,
                                         Promotion def) {
        return new Contrat(0, d1, d2, clauses, ct, cm, def);
    }

    public void modifierContrat(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "dateDebut" -> setDateDebut((LocalDate)e.getValue());
                case "dateFin"    -> setDateFin((LocalDate)e.getValue());
                case "clauses"    -> setClauses((String)e.getValue());
                case "commercer"  -> setCommercer((CentreDeTri)e.getValue());
                case "commerce"   -> setCommerce((Commerce)e.getValue());
                case "definir"    -> setDefinir((Promotion)e.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void supprimerContrat() {
        // plus d'appel DAO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contrat)) return false;
        Contrat that = (Contrat) o;
        return idContrat == that.idContrat
                && Objects.equals(dateDebut, that.dateDebut)
                && Objects.equals(dateFin, that.dateFin)
                && Objects.equals(clauses, that.clauses)
                && Objects.equals(commercer, that.commercer)
                && Objects.equals(commerce, that.commerce)
                && Objects.equals(definir, that.definir);
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", clauses='" + clauses + '\'' +
                ", commercer=" + commercer +
                ", commerce=" + commerce +
                ", definir=" + definir +
                '}';
    }
}
