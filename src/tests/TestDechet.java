public class TestDechet {
    public static void main(String[] args) {
        // Création de quelques déchets
        Dechet d1 = new Dechet(1, TypeDechet.verre, 1.2);
        Dechet d2 = new Dechet(2, TypeDechet.plastique, 0.8);

        // Affichage
        System.out.println("Déchet 1 : " + d1);
        System.out.println("Déchet 2 : " + d2);

        // Appel des méthodes de test
        d1.ajouterDechet();
        d2.retirerDechet();
    }
}
