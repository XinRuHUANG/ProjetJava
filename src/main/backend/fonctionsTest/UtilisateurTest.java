package main.backend.fonctionsTest;

import main.backend.fonctions.Utilisateur;
import main.backend.fonctions.Promotion;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.UtilisateurDAO.*;

class UtilisateurTest {

    @Test
    void testPointsUtilisation() {
        Utilisateur u = new Utilisateur(1, "Dupond", "Jean", 50f);
        Promotion promo = new Promotion(1, 10f, 20f);
        assertTrue(u.utiliserPoints(promo));
        assertEquals(30f, u.getPointsFidelite());
        assertFalse(u.utiliserPoints(new Promotion(2,5f,100f)));
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Utilisateur u = new Utilisateur(0, "X", "Y", 3f);
        u.setPosseder(new ArrayList<>());
        u.setUtiliser(new HashSet<>());

        ajouterUtilisateurBDD(u);
        int id = u.getIdUtilisateur();

        var rows = requeteAvecAffichage(
                "SELECT nom FROM utilisateur WHERE identifiantUtilisateur = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals("X", rows.get(0).get("nom"));

        u.setPrenom("Z");
        actualiserUtilisateurBDD(u, "prenom");
        rows = requeteAvecAffichage(
                "SELECT prenom FROM utilisateur WHERE identifiantUtilisateur = " + id + ";",
                new ArrayList<>(List.of("prenom"))
        );
        assertEquals("Z", rows.get(0).get("prenom"));

        supprimerUtilisateurBDD(u);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM utilisateur WHERE identifiantUtilisateur = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
