package main.backend.fonctionsTest;

import main.backend.fonctions.CentreDeTri;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.CentreDeTriDAO.*;

class CentreDeTriTest {

    @Test
    void testConstructorAndGetters() {
        CentreDeTri c = new CentreDeTri(
                7,
                "Centre A",
                "1 rue Test",
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        assertNotNull(c.getPoubelles());
        assertNotNull(c.getCommerce());
        assertNotNull(c.getContrats());
        assertEquals(7, c.getIdCentreDeTri());
        assertEquals("Centre A", c.getNom());
        assertEquals("1 rue Test", c.getAdresse());
    }

    @Test
    void testModifierAndEquals() {
        CentreDeTri c = new CentreDeTri(2,"Old","Addr",
                new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        c.modifierCentre(Map.of("nom","New","adresse","Addr2"));
        assertEquals("New", c.getNom());
        assertEquals("Addr2", c.getAdresse());

        CentreDeTri d = new CentreDeTri(2, "New", "Addr2",
                c.getPoubelles(), c.getCommerce(), c.getContrats());
        assertEquals(c, d);
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        CentreDeTri c = new CentreDeTri(
                0,
                "XYZ",
                "10 rue Test",
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        ajouterCentreBDD(c);
        int id = c.getIdCentreDeTri();

        var cols = new ArrayList<>(List.of("nom","adresse"));
        var rows = requeteAvecAffichage(
                "SELECT nom, adresse FROM centredetri WHERE identifiantCentreDeTri = " + id + ";",
                cols
        );
        assertEquals("XYZ",       rows.get(0).get("nom"));
        assertEquals("10 rue Test", rows.get(0).get("adresse"));

        c.setNom("XYZ2");
        actualiserCentreBDD(c, "nom");
        c.setAdresse("20 rue Test");
        actualiserCentreBDD(c, "adresse");
        rows = requeteAvecAffichage(
                "SELECT nom, adresse FROM centredetri WHERE identifiantCentreDeTri = " + id + ";",
                cols
        );
        assertEquals("XYZ2", rows.get(0).get("nom"));
        assertEquals("20 rue Test", rows.get(0).get("adresse"));

        supprimerCentreBDD(c);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM centredetri WHERE identifiantCentreDeTri = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
