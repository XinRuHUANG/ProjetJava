public class Utilisateur {
    private int identifiant;
    private String nom;
    private String prenom;
    private float pointsFidelite;

    public Utilisateur(int identifiant, String nom, String prenom, float pointsFidelite) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.pointsFidelite = pointsFidelite;
    }

    public void creerCompte() {
        System.out.println("Compte créé pour " + prenom + " " + nom);
    }

    public void utiliserPoints(float points) {
        if (points <= pointsFidelite) {
            pointsFidelite -= points;
            System.out.println(points + " points utilisés. Points restants : " + pointsFidelite);
        } else {
            System.out.println("Points insuffisants.");
        }
    }

    public void consulterHistorique() {
        System.out.println("Historique (simulé) pour " + nom + " : [dépôt 1, dépôt 2...]");
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (ID: " + identifiant + ", Points: " + pointsFidelite + ")";
    }
}
