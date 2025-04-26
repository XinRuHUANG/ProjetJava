package main.backend.fonctionsTest;

import main.backend.fonctions.CategorieDeProduits;
import main.backend.fonctions.Commerce;
import main.backend.fonctions.Promotion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.List;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.CategorieDeProduitsDAO.*;

class CategorieDeProduitsTest {

    @Test
    void testGettersSettersEqualsToString() {
        Set<Promotion> promos      = new HashSet<>();
        Set<Commerce>   commerces   = new HashSet<>();
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
        CategorieDeProduits cat = new CategorieDeProduits(0,
                "TEST_CAT",
                new HashSet<>(),
                new HashSet<>());
        ajouterCategorieDeProduitsBDD(cat);
        int id = cat.getIdentifiantCategorie();

        // READ
        List<HashMap<String, String>> rows = requeteAvecAffichage(
                "SELECT nom FROM categoriedeproduits WHERE identifiantCategorieDeProduits = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals("TEST_CAT", rows.get(0).get("nom"));

        // UPDATE
        cat.setNom("MOD_CAT");
        actualiserCategorieDeProduitsBDD(cat, "nom");
        rows = requeteAvecAffichage(
                "SELECT nom FROM categoriedeproduits WHERE identifiantCategorieDeProduits = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals("MOD_CAT", rows.get(0).get("nom"));

        // DELETE
        supprimerCategorieDeProduitsBDD(cat);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM categoriedeproduits WHERE identifiantCategorieDeProduits = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
