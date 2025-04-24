package main;

import java.time.LocalDate;
import static main.ContratDAO.ajouterContratBDD;
import static main.ContratDAO.supprimerContratBDD;
import static main.ContratDAO.modifierContratBDD;

public class Contrat {

    //Attributs
    private int idContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String clauses;
    private CentreDeTri centre;

    // Constructeur
    public Contrat(int idContrat, LocalDate dateDebut, LocalDate dateFin, String clauses, CentreDeTri centre) {
        this.idContrat = idContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.clauses = clauses;
        this.centre = centre;
    }

    // Getters et Setters
    public int getIdContrat() { return idContrat; }
    public void setIdContrat(int idContrat) { this.idContrat = idContrat; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getClauses() { return clauses; }
    public void setClauses(String clauses) { this.clauses = clauses; }

    public CentreDeTri getCentre() { return centre; }
    public void setCentre(CentreDeTri centre) { this.centre = centre; }

    // Méthodes de classe
    public static Contrat definirContrat(LocalDate dateDebut, LocalDate dateFin, String clauses, CentreDeTri centre) {
        Contrat contrat = new Contrat(0, dateDebut, dateFin, clauses, centre);
        ajouterContratBDD(contrat);
        return contrat;
    }

    public static void supprimerContrat(Contrat contrat) {
        supprimerContratBDD(contrat);
    }

    public static Contrat modifierContrat(Contrat contrat) {
        modifierContratBDD(contrat);
        return contrat;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + idContrat +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", clauses='" + clauses + '\'' +
                ", centre=" + centre +
                '}';
    }
}
