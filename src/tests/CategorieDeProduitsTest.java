package tests;

import main.CategorieDeProduits;

public class CategorieDeProduitsTest {
    public static void test() {
        // Création d’un centre de tri
        CategorieDeProduits alimentaire = CategorieDeProduits.creerCategorie("Alimentaire");
        CategorieDeProduits cosmetique = CategorieDeProduits.creerCategorie("Cosmétique");
        CategorieDeProduits electronique = CategorieDeProduits.creerCategorie("Électronique");

        // Affichage pour vérification
        System.out.println("Catégorie 1 : " + alimentaire);
        System.out.println("Catégorie 2 : " + cosmetique);
        System.out.println("Catégorie 3 : " + electronique);

        alimentaire.supprimerCategorie();
        cosmetique.supprimerCategorie();
        electronique.supprimerCategorie();
    }
}
