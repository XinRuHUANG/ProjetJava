
package tests;

import main.PoubelleIntelligente;
import main.TypeDéchetEnum;

public class PoubelleIntelligenteTest {
    public static void test() {
        // Création d’une poubelle intelligente
        PoubelleIntelligente poubelle = PoubelleIntelligente.ajouterPoubelle(TypeDéchetEnum.TypeDechet.plastique,"Rue des Lilas", 50.0f);

        //Affichage
        System.out.println("poubelle : " + poubelle);

        // Appel de quelques méthodes à tester
        PoubelleIntelligente.collecterDechets(poubelle.getIdentifiantPoubelle());
        poubelle.retirerPoubelle();
    }
}
