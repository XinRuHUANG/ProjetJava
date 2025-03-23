import java.time.LocalDate;

public class TestContrat {
    public static void main(String[] args) {
        // Création d’un contrat fictif
        Contrat contrat = new Contrat(
            1,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2025, 1, 1),
            "Clause A : Engagement de 1 an."
        );

        // Appel des méthodes à tester
        contrat.definirContrat();
        contrat.renouvelerContrat(LocalDate.of(2026, 1, 1));

        // Affichage pour vérification
        System.out.println("Contrat actif : " + contrat);
    }
}
