package tests;

import main.Promotion;

public class PromotionTest {
    public static void test() {
        // Création de quelques promotions
        Promotion promo1 = Promotion.ajouterPromotion(10.0f, 150.0f);
        Promotion promo2 = Promotion.ajouterPromotion(20.0f, 250.0f);

        // Affichage
        System.out.println("Promotion 1 : " + promo1);
        System.out.println("Promotion 2 : " + promo2);

        //autres méthodes
        promo1.retirerPromotion();
        promo2.retirerPromotion();
    }
}
