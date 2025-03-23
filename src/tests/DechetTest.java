package tests;

import main.Dechet;
import main.TypeDéchetEnum;

import static main.Dechet.ajouterDechet;

public class DechetTest {
    public static void test() {
        // Création de quelques déchets
        Dechet d1 = ajouterDechet(TypeDéchetEnum.TypeDechet.verre);
        Dechet d2 = ajouterDechet(TypeDéchetEnum.TypeDechet.plastique);

        // Affichage
        System.out.println("Déchet 1 : " + d1);
        System.out.println("Déchet 2 : " + d2);

    }
}
