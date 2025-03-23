package tests;

import main.CentreDeTri;

import static main.CentreDeTri.ajouterCentre;

public class CentreDeTriTest {
    public static void test() {
        // Création d’un centre de tri
        CentreDeTri centre = ajouterCentre("Centre Est", "12 rue du recyclage");

        // Affichage pour vérification
        System.out.println("Centre de tri créé : " + centre);

        // Appels des méthodes à tester
        centre.retirerCentre();
    }
}
