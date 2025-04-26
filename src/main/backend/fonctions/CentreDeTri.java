package main.backend.fonctions;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CentreDeTri {
    private int idCentreDeTri;
    private String nom;
    private String adresse;
    private Set<PoubelleIntelligente> poubelles;
    private List<Commerce> commerce;
    private List<Contrat> contrats;

    public CentreDeTri(int id, String nom, String adresse,
                       Set<PoubelleIntelligente> poubelles,
                       List<Commerce> commerce,
                       List<Contrat> contrats) {
        this.idCentreDeTri = id;
        this.nom = nom;
        this.adresse = adresse;
        this.poubelles = poubelles;
        this.commerce = commerce;
        this.contrats = contrats;
    }

    public int getIdCentreDeTri() { return idCentreDeTri; }
    public void setIdCentreDeTri(int id) { this.idCentreDeTri = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Set<PoubelleIntelligente> getPoubelles() { return poubelles; }
    public void setPoubelles(Set<PoubelleIntelligente> poubelles) { this.poubelles = poubelles; }

    public List<Commerce> getCommerce() { return commerce; }
    public void setCommerce(List<Commerce> commerce) { this.commerce = commerce; }

    public List<Contrat> getContrats() { return contrats; }
    public void setContrats(List<Contrat> contrats) { this.contrats = contrats; }

    public static CentreDeTri ajouterCentre(String nom, String adresse,
                                            Set<PoubelleIntelligente> poubelles,
                                            List<Commerce> commerce,
                                            List<Contrat> contrats) {
        return new CentreDeTri(0, nom, adresse, poubelles, commerce, contrats);
    }

    public void modifierCentre(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "nom" -> setNom((String)e.getValue());
                case "adresse" -> setAdresse((String)e.getValue());
                case "poubelles" -> setPoubelles((Set<PoubelleIntelligente>)e.getValue());
                case "commerces" -> setCommerce((List<Commerce>)e.getValue());
                case "contrats" -> setContrats((List<Contrat>)e.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void supprimerCentre() {
        // plus d'appel DAO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CentreDeTri)) return false;
        CentreDeTri that = (CentreDeTri) o;
        return idCentreDeTri == that.idCentreDeTri
                && Objects.equals(nom, that.nom)
                && Objects.equals(adresse, that.adresse)
                && Objects.equals(poubelles, that.poubelles)
                && Objects.equals(commerce, that.commerce)
                && Objects.equals(contrats, that.contrats);
    }

    @Override
    public String toString() {
        return "CentreDeTri{" +
                "idCentreDeTri=" + idCentreDeTri +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", poubelles=" + poubelles +
                ", commerce=" + commerce +
                ", contrats=" + contrats +
                '}';
    }
}
