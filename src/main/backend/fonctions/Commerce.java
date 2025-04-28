package main.backend.fonctions;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Commerce {
    private int identifiantCommerce;
    private String nom;
    private List<CentreDeTri> commercer;
    private List<Contrat> contrats;
    private Set<CategorieDeProduits> proposer;

    public Commerce(int id, String nom,
                    List<CentreDeTri> commercer,
                    List<Contrat> contrats,
                    Set<CategorieDeProduits> proposer) {
        this.identifiantCommerce = id;
        this.nom = nom;
        this.commercer = commercer;
        this.contrats = contrats;
        this.proposer = proposer;
    }

    public int getIdentifiantCommerce() { return identifiantCommerce; }
    public void setIdentifiantCommerce(int id) { this.identifiantCommerce = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<CentreDeTri> getCommercer() { return commercer; }
    public void setCommercer(List<CentreDeTri> c) { this.commercer = c; }

    public List<Contrat> getContrat() { return contrats; }
    public void setContrat(List<Contrat> c) { this.contrats = c; }

    public Set<CategorieDeProduits> getProposer() { return proposer; }
    public void setProposer(Set<CategorieDeProduits> p) { this.proposer = p; }

    public static Commerce ajouterCommerce(String nom,
                                           List<CentreDeTri> commercer,
                                           List<Contrat> contrats,
                                           Set<CategorieDeProduits> proposer) {
        return new Commerce(0, nom, commercer, contrats, proposer);
    }

    public void modifierCommerce(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "nom" -> setNom((String)e.getValue());
                case "commercer" -> setCommercer((List<CentreDeTri>)e.getValue());
                case "contrats" -> setContrat((List<Contrat>)e.getValue());
                case "proposer" -> setProposer((Set<CategorieDeProduits>)e.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public void supprimerCommerce() {
        // plus d'appel DAO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commerce)) return false;
        Commerce that = (Commerce) o;
        return identifiantCommerce == that.identifiantCommerce
                && Objects.equals(nom, that.nom)
                && Objects.equals(commercer, that.commercer)
                && Objects.equals(contrats, that.contrats)
                && Objects.equals(proposer, that.proposer);
    }

    @Override
    public String toString() {
        return "Commerce{" +
                "identifiantCommerce=" + identifiantCommerce +
                ", nom='" + nom + '\'' +
                ", commercer=" + commercer +
                ", contrats=" + contrats +
                ", proposer=" + proposer +
                '}';
    }
}
