package main;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static main.CommerceDAO.actualiserCommerceBDD;
import static main.ContratDAO.*;

public class Contrat {

    //Attributs
    private int idContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String clauses;
    //Modélisation des associations
    private CentreDeTri commercer;
    private Commerce commerce;
    private Promotion definir;

    // Constructeur
    public Contrat(int idContrat, LocalDate dateDebut, LocalDate dateFin, String clauses, CentreDeTri commercer, Promotion definir, Promotion commerce) {
        this.idContrat = idContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.clauses = clauses;
        this.commercer = commercer;
        this.definir = definir;
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

    public CentreDeTri getCommercer() { return commercer; }
    public void setCommercer(CentreDeTri commercer) { this.commercer = commercer; }

    public Promotion getDefinir() {return definir;}
    public void setDefinir(Promotion definir) {this.definir = definir;}

    public Commerce getCommerce() {return commerce;}
    public void setCommerce(Commerce commerce) {this.commerce = commerce;}

    // Méthodes de classe
    public static Contrat ajouterContrat(LocalDate dateDebut, LocalDate dateFin, String clauses, CentreDeTri commercer, Promotion promotion, Promotion commerce) {
        /*Crée et ajoute un nouveau contrat à la base de données.
         * @param dateDebut La date de début du contrat.
         * @param dateFin La date de fin du contrat.
         * @param clauses Les clauses du contrat sous forme de texte.
         * @param commercer Le centre de tri lié au contrat.
         * @param promotion La promotion proposée par le centre de tri.
         * @param commerce La promotion proposée par le commerce.
         * @return Le contrat nouvellement créé.
         */
        Contrat contrat = new Contrat(0, dateDebut, dateFin, clauses, commercer, promotion, commerce);
        ajouterContratBDD(contrat);
        return contrat;
    }

    public void supprimerContrat() {
        /*Supprime ce contrat de la base de données.
         */
        supprimerContratBDD(this);
    }

    public void modifierContrat(Map<String, Object> modifications) {
        /*Fonction pour modifier le depot, une map "modifications" permet d'informer le programme des attributs qu'on veut modifier, on suppose que cette map est de la forme
         * {ième attribut = ième valeur}*/
        for (Map.Entry<String, Object> entry : modifications.entrySet()) {
            String cle = entry.getKey();
            Object obj = entry.getValue();
            if (cle == "dateDebut") {
                LocalDate date = (LocalDate) obj;
                this.setDateDebut(date);
                actualiserContratBDD(this, cle);
            }
            if (cle == "dateFin") {
                LocalDate date = (LocalDate) obj;
                this.setDateFin(date);
                actualiserContratBDD(this, cle);
            }
            if (cle == "clauses") {
                String clauses = (String) obj;
                this.setClauses(clauses);
                actualiserContratBDD(this, cle);
            }
            if (cle == "commercer") {
                assert modifications.containsKey("commerce");
                CentreDeTri commercer = (CentreDeTri) obj;
                this.setCommercer(commercer);
                actualiserContratBDD(this, cle);
            }
            if (cle == "commerce") {
                assert modifications.containsKey("commercer");
                Commerce commerce = (Commerce) obj;
                this.setCommerce(commerce);
                actualiserContratBDD(this, cle);
            }
            if (cle == "definir") {
                Promotion definir = (Promotion) obj;
                this.setDefinir(definir);
                actualiserContratBDD(this, cle);
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj==null || getClass() != obj.getClass()) return false;
        Contrat contrat = (Contrat) obj;
        return this.idContrat == contrat.idContrat &&
                this.dateDebut.equals(contrat.dateDebut) && this.dateFin.equals(contrat.dateFin)
                && this.clauses == contrat.clauses && this.commercer.equals(contrat.commercer)
                && this.commerce.equals(contrat.commerce)  && this.definir.equals(contrat.definir);
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
