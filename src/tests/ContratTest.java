package tests;

import main.Contrat;

import java.time.LocalDate;

public class ContratTest {
    public static void test() {
        // Création d’un contrat fictif
        Contrat contrat = Contrat.definirContrat(LocalDate.of(2024,1,1), LocalDate.of(2025, 1, 1), "Clause A : Engagement de 1 an.");

        // Affichage pour vérification
        System.out.println("Contrat actif : " + contrat);

        // Appel des méthodes à tester
        contrat.supprimerContrat();

    }
}
