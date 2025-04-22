package main;

import java.util.List;
import java.util.Set;
import static main.CentreDeTriDAO.ajouterCentreBDD;

public class CentreDeTri {

    //Attributs de la classe CentreDeTri
    private int idCentreDeTri;
    private String nom;
    private String adresse;

    //Modélisation des liaisons
    private Set<PoubelleIntelligente> poubelles;
    private List<Commerce> commerce;
    private List<Contrat> contrats;


    //Getter et Setter
    public int getIdCentreDeTri() {return idCentreDeTri;}
    public void setIdCentreDeTri(int idCentreDeTri) {this.idCentreDeTri = idCentreDeTri;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getAdresse() {return adresse;}
    public void setAdresse(String adresse) {this.adresse = adresse;}

    public Set<PoubelleIntelligente> getPoubelles() {return poubelles;}
    public void setPoubelles(Set<PoubelleIntelligente> poubelles) {this.poubelles = poubelles;}

    public List<Commerce> getCommerce() {return commerce;}
    public void setCommerce(List<Commerce> commerce) {this.commerce = commerce;}

    public List<Contrat> getContrats() {return contrats;}
    public void setContrats(List<Contrat> contrats) {this.contrats = contrats;}

    //Constructeur
    public CentreDeTri(int idCentreDeTri, String nom, String adresse, Set<PoubelleIntelligente> poubelleIntelligente, List<Commerce> commerce, List<Contrat> contrats) {
        this.idCentreDeTri = idCentreDeTri;
        this.nom = nom;
        this.adresse = adresse;
        this.poubelles = poubelleIntelligente;
        this.commerce = commerce;
        this.contrats = contrats;
    }

    //Méthodes de classes
    public static CentreDeTri ajouterCentre(int idCentreDeTri, String nom, String adresse, Set<PoubelleIntelligente> poubelleIntelligente, List<Commerce> commerce, List<Contrat> contrats){
        CentreDeTri centreDeTri = new CentreDeTri(idCentreDeTri, nom, adresse, poubelleIntelligente, commerce, contrats);
        ajouterCentreBDD(centreDeTri);
        return centreDeTri;
    }


    public void ajouterContrat(Contrat contrat){
        contrats.add(contrat);
    }

    @Override
    public String toString() {
        return "CentreDeTri{" +
                "idCentreDeTri=" + idCentreDeTri +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", poubelleIntelligente=" + poubelles +
                ", commerce=" + commerce +
                '}';
    }
}