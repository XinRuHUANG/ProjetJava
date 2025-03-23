package tests;

import main.Dechet;
import main.Depot;
import main.TypeDéchetEnum;
import main.Utilisateur;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static main.Dechet.ajouterDechet;

public class DepotTest {
    public static void test() {
        // Création d’un dépôt fictif
        Utilisateur user = Utilisateur.creerCompte("Dupont", "Lucie", 150.0f);
        List<Dechet> dechets = new ArrayList<>();
        Dechet d1 = ajouterDechet(TypeDéchetEnum.TypeDechet.verre);
        Dechet d2 = ajouterDechet(TypeDéchetEnum.TypeDechet.plastique);
        dechets.add(d1);
        dechets.add(d2);
        Depot depot = Depot.creerDepot(user, dechets);


        // Affichage
        System.out.println("Depôt: " + depot);
        //Affichage de son contenu en particulier
        System.out.println("Historique: " + depot.getContenir());

    }
}
