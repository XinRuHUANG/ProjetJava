// src/test/java/main/backend/fonctionsTest/DepotTest.java
package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class DepotTest {
    @BeforeEach
    void initDb() throws Exception {
        requete("DELETE FROM contenir WHERE identifiantDepot = 1");
        requete("DELETE FROM utiliser WHERE identifiantPromotion = 1");
        requete("DELETE FROM depot WHERE identifiantDepot = 1");
        requete("DELETE FROM utilisateur WHERE identifiantUtilisateur = 1");
        requete("DELETE FROM promotion WHERE identifiantPromotion = 1");
        requete("INSERT INTO utilisateur (identifiantUtilisateur, nom, prenom, pointsFidelite) VALUES (1,'N','P',0)");
        requete("INSERT INTO promotion  (identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (1,0,0)");
    }

    @Test
    void testBasicAccessors() {
        Depot d = new Depot(5, LocalDate.now(), LocalTime.now(), 3f);
        assertEquals(5, d.getIdentifiantDepot());
        assertEquals(3f, d.getPoints());
        d.setPoints(7f);
        assertEquals(7f, d.getPoints());
        assertNotNull(d.getJeter());
        assertNotNull(d.getContenir());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Depot d = Depot.creerDepot(
                new Utilisateur(1,"N","P",0f),
                new ArrayList<>()
        );

        // CREATE
        DepotDAO.ajouterDepotBDD(d);
        int id = d.getIdentifiantDepot();

        // READ
        Depot lu = DepotDAO.lireDepotBDD(id);
        assertNotNull(lu);
        assertNotNull(lu.getDate());

        // UPDATE
        d.setPoints(9f);
        DepotDAO.actualiserDepotBDD(d, "points");
        lu = DepotDAO.lireDepotBDD(id);
        assertEquals(9f, lu.getPoints());

        // DELETE
        DepotDAO.supprimerDepotBDD(d);
        assertNull(DepotDAO.lireDepotBDD(id));
    }
}
