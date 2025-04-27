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
    private List<Commerce> commerces;
    private List<Contrat> contrats;

    public CentreDeTri(int id,
                       String nom,
                       String adresse,
                       Set<PoubelleIntelligente> poubelles,
                       List<Commerce> commerces,
                       List<Contrat> contrats) {
        this.idCentreDeTri = id;
        this.nom = nom;
        this.adresse = adresse;
        this.poubelles = poubelles;
        this.commerces = commerces;
        this.contrats = contrats;
    }

    public int getIdCentreDeTri() {
        return idCentreDeTri;
    }

    public void setIdCentreDeTri(int idCentreDeTri) {
        this.idCentreDeTri = idCentreDeTri;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<PoubelleIntelligente> getPoubelles() {
        return poubelles;
    }

    public void setPoubelles(Set<PoubelleIntelligente> poubelles) {
        this.poubelles = poubelles;
    }

    public List<Commerce> getCommerces() {
        return commerces;
    }

    public void setCommerces(List<Commerce> commerces) {
        this.commerces = commerces;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }

    /**
     * Créé un nouveau CentreDeTri (à utiliser avant appel au DAO pour persistance).
     */
    public static CentreDeTri ajouterCentre(String nom,
                                            String adresse,
                                            Set<PoubelleIntelligente> poubelles,
                                            List<Commerce> commerces,
                                            List<Contrat> contrats) {
        return new CentreDeTri(0, nom, adresse, poubelles, commerces, contrats);
    }

    /**
     * Modifie les champs du CentreDeTri en mémoire.
     * @param modifications map champ→nouvelle valeur. Clefs attendues : "nom", "adresse",
     *                      "poubelles", "commerces", "contrats".
     */
    public void modifierCentre(Map<String, Object> modifications) {
        for (var entry : modifications.entrySet()) {
            switch (entry.getKey()) {
                case "nom" -> setNom((String) entry.getValue());
                case "adresse" -> setAdresse((String) entry.getValue());
                case "poubelles" -> setPoubelles((Set<PoubelleIntelligente>) entry.getValue());
                case "commerces" -> setCommerces((List<Commerce>) entry.getValue());
                case "contrats" -> setContrats((List<Contrat>) entry.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + entry.getKey());
            }
        }
    }

    /**
     * Prépare la suppression du centre (appel au DAO uniquement).
     */
    public void supprimerCentre() {
        // plus d'appel DAO automatique ici
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
                && Objects.equals(commerces, that.commerces)
                && Objects.equals(contrats, that.contrats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCentreDeTri, nom, adresse, poubelles, commerces, contrats);
    }

    @Override
    public String toString() {
        return "CentreDeTri{" +
                "idCentreDeTri=" + idCentreDeTri +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", poubelles=" + poubelles +
                ", commerces=" + commerces +
                ", contrats=" + contrats +
                '}';
    }
}
