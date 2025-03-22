public class PoubelleIntelligenteTest {
    public static void main(String[] args) {
        // Création d’une poubelle intelligente
        PoubelleIntelligente poubelle = new PoubelleIntelligente(
            1,
            "Rue des Lilas",
            50.0f,
            TypeDechet.plastique
        );

        // Appel de quelques méthodes à tester
        poubelle.ajouterPoubelle();
        poubelle.mesurerVolume();
        poubelle.collecterDechets();
        poubelle.statistiquerDechets();
    }
}
