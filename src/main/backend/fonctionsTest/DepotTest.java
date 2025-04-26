package main.backend.fonctionsTest;

import main.backend.fonctions.Depot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.DepotDAO.*;

class DepotTest {

    @Test
    void testCreationAndVerif() {
        Depot d = new Depot(0, LocalDate.now(), LocalTime.now(), 0f);
        assertNotNull(d.getDate());
        assertNotNull(d.getHeure());
        assertEquals(0f, d.getPoints());
        assertNotNull(d.getContenir());
        assertNotNull(d.getJeter());
        assertNotNull(d.getPosseder());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Depot d = new Depot(0, LocalDate.now(), LocalTime.now(), 0f);
        d.setContenir(new ArrayList<>());
        d.setJeter(new HashSet<>());
        d.setPosseder(null);

        creerDepotBDD(d);
        int id = d.getIdentifiantDepot();

        var rows = requeteAvecAffichage(
                "SELECT points FROM depot WHERE identifiantDepot = " + id + ";",
                new ArrayList<>(List.of("points"))
        );
        assertEquals("0.0", rows.get(0).get("points"));

        d.setPoints(5f);
        actualiserDepotBDD(d, "points");
        rows = requeteAvecAffichage(
                "SELECT points FROM depot WHERE identifiantDepot = " + id + ";",
                new ArrayList<>(List.of("points"))
        );
        assertEquals("5.0", rows.get(0).get("points"));

        supprimerDepotBDD(d);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM depot WHERE identifiantDepot = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
