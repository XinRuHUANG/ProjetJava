public class TestCategorieDeProduits {
    public static void main(String[] args) {
        // Création de plusieurs catégories
        CategorieDeProduits alimentaire = new CategorieDeProduits(1, "Alimentaire");
        CategorieDeProduits cosmetique = new CategorieDeProduits(2, "Cosmétique");
        CategorieDeProduits electronique = new CategorieDeProduits(3, "Électronique");

        // Affichage pour vérification
        System.out.println("Catégorie 1 : " + alimentaire);
        System.out.println("Catégorie 2 : " + cosmetique);
        System.out.println("Catégorie 3 : " + electronique);
    }
}
