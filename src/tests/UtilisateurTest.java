package tests;

import main.Promotion;
import main.Utilisateur;

public class UtilisateurTest {
    public static void test() {
        // Création d’un utilisateur fictif
        Utilisateur user = Utilisateur.creerCompte("Dupont", "Lucie", 150.0f);


        // Affichage
        System.out.println("Infos utilisateur : " + user);

        //test d'utilisation d'une promotion
        Promotion promo = Promotion.ajouterPromotion(0.5f, 20.0f);
        user.utiliserPoints(promo);
        System.out.println("Infos utilisateur : " + user);

    }
}
