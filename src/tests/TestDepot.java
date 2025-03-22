import java.time.LocalDate;
import java.time.LocalTime;

public class TestDepot {
    public static void main(String[] args) {
        // Création d’un dépôt fictif
        Depot depot = new Depot(
            1,
            LocalDate.now(),
            LocalTime.now(),
            0.0f
        );

        // Création de déchets fictifs
        Dechet plastique = new Dechet(1, TypeDechet.plastique, 1.5);
        Dechet carton = new Dechet(2, TypeDechet.carton, 0.8);

        // Ajout des déchets au dépôt
        depot.ajouterDechet(plastique);
        depot.ajouterDechet(carton);

        // Affichage
        System.out.println("Déchets dans le dépôt :");
        depot.listerDechets();

        // Vider le dépôt
        depot.viderDepot();
        System.out.println("Dépôt vidé !");
        depot.listerDechets();
    }
}
