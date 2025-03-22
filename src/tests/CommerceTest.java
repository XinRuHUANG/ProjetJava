public class TestCommerce {
    public static void main(String[] args) {
        // Création d’un commerce fictif
        Commerce commerce = new Commerce(1, "ÉcoBoutique");

        // Appel des méthodes à tester
        commerce.ajouterCommerce();
        commerce.retirerCommerce();

        // Affichage pour vérification
        System.out.println("Commerce créé : " + commerce);
    }
}

