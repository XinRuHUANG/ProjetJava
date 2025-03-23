package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Contrat {
    private int identifiantContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String clauses;
    //Modélisation des associations
    private Set<Promotion> definir;

    public Contrat(int identifiantContrat, LocalDate dateDebut, LocalDate dateFin, String clauses) {
        this.identifiantContrat = identifiantContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.clauses = clauses;
    }
    public Contrat(){}

    public int getIdentifiantContrat() {
        return identifiantContrat;
    }

    public void setIdentifiantContrat(int identifiantContrat) {
        this.identifiantContrat = identifiantContrat;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getClauses() {
        return clauses;
    }

    public void setClauses(String clauses) {
        this.clauses = clauses;
    }

    public Set<Promotion> getDefinir() {
        return definir;
    }

    public void setDefinir(Set<Promotion> definir) {
        this.definir = definir;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "identifiantContrat=" + identifiantContrat +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", clauses='" + clauses + '\'' +
                ", definir=" + definir +
                '}';
    }

    public static Contrat definirContrat(LocalDate dateDebut, LocalDate dateFin, String clauses){

        //récupération du dernier identifiant dans la BDD
        String requete = "SELECT MAX(identifiantContrat) FROM Contrat;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantContrat");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);
        int identifiantContrat = Integer.parseInt(infos.getFirst().get("identifiantContrat")) + 1;
        Contrat contrat = new Contrat(identifiantContrat, dateDebut, dateFin, clauses);
        //Création dans la base de données
        requete = "INSERT INTO Contrat(identifiantContrat, dateDebut, dateFin, clauses) VALUES ("+Integer.toString(identifiantContrat)+","+dateDebut.toString()+","+dateFin.toString()+","+clauses+");";
        requete(requete);

        return contrat;
    }
    public void supprimerContrat(){
        int identifiantContrat = this.identifiantContrat;
        //Suppresion le contrat de la base de données
        String requete = "DELETE FROM Contrat WHERE identifiantContrat = " + Integer.toString(identifiantContrat) + ";";
        requete(requete);
    }
}
