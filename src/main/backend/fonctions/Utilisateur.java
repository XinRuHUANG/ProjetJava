package main.backend.fonctions;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Utilisateur {
    private int identifiantUtilisateur;
    private String nom;
    private String prenom;
    private float pointsFidelite;
    private List<Depot> posseder;
    private Set<Promotion> utiliser;

    public Utilisateur(int id, String nom, String prenom, float pts) {
        this.identifiantUtilisateur = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pointsFidelite = pts;
    }

    // getters / setters…


    public int getIdentifiantUtilisateur() {return identifiantUtilisateur;}
    public void setIdentifiantUtilisateur(int identifiantUtilisateur) {this.identifiantUtilisateur = identifiantUtilisateur;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    public float getPointsFidelite() {return pointsFidelite;}

    public void setPointsFidelite(float pointsFidelite) {this.pointsFidelite = pointsFidelite;}

    public List<Depot> getPosseder() {return posseder;}
    public void setPosseder(List<Depot> posseder) {this.posseder = posseder;}

    public Set<Promotion> getUtiliser() {return utiliser;}
    public void setUtiliser(Set<Promotion> utiliser) {this.utiliser = utiliser;}

    public static Utilisateur ajouterUtilisateur(String nom, String prenom, float pts) {return new Utilisateur(0, nom, prenom, pts);}

    public void modifierUtilisateur(Map<String, Object> modifications) {
        for (var e : modifications.entrySet()) {
            switch (e.getKey()) {
                case "nom"            -> this.nom = (String)e.getValue();
                case "prenom"         -> this.prenom = (String)e.getValue();
                case "pointsFidelite" -> this.pointsFidelite = (float)e.getValue();
                case "posseder"       -> this.posseder = (List<Depot>)e.getValue();
                case "utiliser"       -> this.utiliser = (Set<Promotion>)e.getValue();
                default -> throw new IllegalArgumentException("Clé inconnue : " + e.getKey());
            }
        }
    }

    public boolean utiliserPoints(Promotion promo) {
        if (pointsFidelite >= promo.getPointsRequis()) {
            pointsFidelite -= promo.getPointsRequis();
            return true;
        }
        return false;
    }

    public String consulterHistorique() {
        StringBuilder sb = new StringBuilder(
                "Historique de " + identifiantUtilisateur + " :\n");
        posseder.stream()
                .sorted(Comparator.comparing(Depot::getDate).reversed()
                        .thenComparing(Depot::getHeure).reversed())
                .forEach(d -> {
                    sb.append("Dépôt n°").append(d.getIdentifiantDepot()).append(" contenant :\n");
                    d.getContenir().forEach(dec ->
                            sb.append("  - Déchet n°")
                                    .append(dec.getIdentifiantDechet())
                                    .append(" de type ").append(dec.getType()).append("\n"));
                });
        return sb.toString();
    }

    public void supprimerUtilisateur() {
        // plus d'appel DAO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilisateur)) return false;
        Utilisateur that = (Utilisateur) o;
        return identifiantUtilisateur == that.identifiantUtilisateur
                && Float.compare(that.pointsFidelite, pointsFidelite) == 0
                && Objects.equals(nom, that.nom)
                && Objects.equals(prenom, that.prenom)
                && Objects.equals(posseder, that.posseder)
                && Objects.equals(utiliser, that.utiliser);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + identifiantUtilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", pointsFidelite=" + pointsFidelite +
                ", posseder=" + posseder +
                ", utiliser=" + utiliser +
                '}';
    }
}
