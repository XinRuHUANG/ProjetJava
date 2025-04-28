package main.backend.fonctionsTest;

import main.backend.fonctions.CategorieDeProduits;
import main.backend.fonctions.Commerce;
import main.backend.fonctions.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.CategorieDeProduitsDAO.*;

class CategorieDeProduitsTest {

    @BeforeEach
    void initDb() throws Exception {
        // Supprime d'abord les éventuels enregistrements de test
        requete(
                "DELETE FROM categoriedeproduits " +
                        "WHERE nom IN ('TEST_CAT','MOD_CAT');"
        );
    }

    @Test
    void testGettersSettersEqualsToString() {
        var promos    = new HashSet<Promotion>();
        var commerces = new HashSet<Commerce>();
        CategorieDeProduits c = new CategorieDeProduits(42, "Alimentaire", promos, commerces);

        assertNotNull(c.getConcerner());
        assertNotNull(c.getProposer());
        assertEquals(42, c.getIdentifiantCategorie());
        assertEquals("Alimentaire", c.getNom());

        c.setNom("Boissons");
        assertEquals("Boissons", c.getNom());

        CategorieDeProduits d = new CategorieDeProduits(42, "Boissons", promos, commerces);
        assertEquals(c, d);

        String s = c.toString();
        assertTrue(s.contains("identifiantCategorieDeProduits=42"));
        assertTrue(s.contains("nom='Boissons'"));
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        // === CREATE ===
        CategorieDeProduits cat = new CategorieDeProduits(
                0, "TEST_CAT", new HashSet<>(), new HashSet<>()
        );
        ajouterCategorieDeProduitsBDD(cat);
        assertTrue(cat.getIdentifiantCategorie() >= 1,
                "L'ID généré doit être >= 1");

        // === READ direct ===
        var rows = requeteAvecAffichage(
                "SELECT `nom` FROM `CategorieDeProduits` " +
                        "WHERE `identifiantCategorieDeProduits` = "
                        + cat.getIdentifiantCategorie() + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals(1, rows.size(), "On doit trouver exactement 1 ligne");
        assertEquals("TEST_CAT", rows.get(0).get("nom"));

        // === UPDATE via DAO ===
        cat.setNom("MOD_CAT");
        actualiserCategorieDeproduitsBDD(cat, "nom");
        rows = requeteAvecAffichage(
                "SELECT `nom` FROM `CategorieDeProduits` " +
                        "WHERE `identifiantCategorieDeProduits` = "
                        + cat.getIdentifiantCategorie() + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals(1, rows.size(), "On doit toujours trouver 1 ligne");
        assertEquals("MOD_CAT", rows.get(0).get("nom"));

        // === DELETE via DAO ===
        supprimerCategorieDeproduitsBDD(cat);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM `CategorieDeProduits` " +
                        "WHERE `identifiantCategorieDeProduits` = "
                        + cat.getIdentifiantCategorie() + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"),
                "Après suppression, le count doit valoir 0");
    }
}
