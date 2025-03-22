public class TestPromotion {
    public static void main(String[] args) {
        // Création de quelques promotions
        Promotion promo1 = new Promotion(1, 10.0f, 100.0f); // 10% pour 100 points
        Promotion promo2 = new Promotion(2, 20.0f, 250.0f);

        // Utilisation des promotions
        promo1.utiliser();
        promo2.utiliser();

        // Affichage
        System.out.println("Promotion 1 : " + promo1);
        System.out.println("Promotion 2 : " + promo2);
    }
}
