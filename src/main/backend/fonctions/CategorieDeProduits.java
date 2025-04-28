package main.backend.fonctions;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CategorieDeProduits {
    private int identifiantCategorieDeProduits;
    private String nom;
    private Set<Promotion> concerner;
    private Set<Commerce> proposer;

    public CategorieDeProduits(int id, String nom, Set<Promotion> concerner, Set<Commerce> proposer) {
        this.identifiantCategorieDeProduits = id;
        this.nom = nom;
        this.concerner = concerner;
        this.proposer = proposer;
    }

    public int getIdentifiantCategorie() { return identifiantCategorieDeProduits; }
    public void setIdentifiantCategorie(int id) { this.identifiantCategorieDeProduits = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Set<Promotion> getConcerner() { return concerner; }
    public void setConcerner(Set<Promotion> concerner) { this.concerner = concerner; }

    public Set<Commerce> getProposer() { return proposer; }
    public void setProposer(Set<Commerce> proposer) { this.proposer = proposer; }

    public static CategorieDeProduits creerCategorieDeProduits(String nom, Set<Promotion> concerner, Set<Commerce> proposer) {
        // Ne modifie que l'état de l'objet
        return new CategorieDeProduits(0, nom, concerner, proposer);
    }

    public void supprimerCategorieDeProduits() {
        // plus d'appel DAO automatique ici
    }

    public void modifierCategorieDeProduits(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "nom" -> setNom((String)e.getValue());
                case "concerner" -> setConcerner((Set<Promotion>)e.getValue());
                case "proposer" -> setProposer((Set<Commerce>)e.getValue());
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorieDeProduits)) return false;
        CategorieDeProduits that = (CategorieDeProduits) o;
        return identifiantCategorieDeProduits == that.identifiantCategorieDeProduits
                && Objects.equals(nom, that.nom)
                && Objects.equals(concerner, that.concerner)
                && Objects.equals(proposer, that.proposer);
    }

    @Override
    public String toString() {
        return "CategorieDeProduits{" +
                "identifiantCategorieDeProduits=" + identifiantCategorieDeProduits +
                ", nom='" + nom + '\'' +
                ", concerner=" + concerner +
                ", proposer=" + proposer +
                '}';
    }
}
