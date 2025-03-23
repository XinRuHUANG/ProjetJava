
package tests;

import main.Commerce;

import static main.Commerce.ajouterCommerce;

public class CommerceTest {
    public static void test() {
        // Création d’un commerce fictif
        Commerce commerce = ajouterCommerce("ÉcoBoutique");

        // Affichage pour vérification
        System.out.println("Commerce créé : " + commerce);

        commerce.retirerCommerce();


    }
}
