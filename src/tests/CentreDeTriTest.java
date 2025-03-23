public class TestCentreDeTri {
    public static void main(String[] args) {
        // Création d’un centre de tri
        CentreDeTri centre = new CentreDeTri(
            1,
            "Centre Est",
            "12 rue du recyclage"
        );

        // Appels des méthodes à tester
        centre.ajouterCentre();
        centre.retirerCentre();

        // Affichage pour vérification
        System.out.println("Centre de tri créé : " + centre);
    }
}

