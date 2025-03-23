package tests;

import main.CategorieDeProduits;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST DEPOT ===");
        DepotTest.test();

        System.out.println("\n=== TEST DECHET ===");
        DechetTest.test();

        System.out.println("\n=== TEST CATEGORIE DE PRODUITS ===");
        CategorieDeProduitsTest.test();

        System.out.println("\n=== TEST COMMERCE ===");
        CommerceTest.test();

        System.out.println("\n=== TEST CONTRAT ===");
        ContratTest.test();

        System.out.println("\n=== TEST CENTRE DE TRI ===");
        CentreDeTriTest.test();

        System.out.println("\n=== TEST PROMOTION ===");
        PromotionTest.test();

        System.out.println("\n=== TEST POUBELLE INTELLIGENTE ===");
        PoubelleIntelligenteTest.test();

        System.out.println("\n=== TEST UTILISATEUR ===");
        UtilisateurTest.test();
    }
}
