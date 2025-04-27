// src/test/java/main/backend/fonctionsTest/DechetTest.java
package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class DechetTest {
    @BeforeEach
    void initDb() throws Exception {
        requete("DELETE FROM contenir WHERE identifiantDechet = 1");
        requete("DELETE FROM dechet WHERE identifiantDechet = 1");
        requete("DELETE FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = 1");
        requete("DELETE FROM depot WHERE identifiantDepot = 1");
        requete("INSERT INTO depot  (identifiantDepot, date, heure, points) VALUES (1,'2025-01-01','12:00:00',0)");
        requete("INSERT INTO poubelleintelligente (identifiantPoubelleIntelligente, emplacement, capaciteMaximale, typeDechet, poids, identifiantCentreDeTri) VALUES (1,'E',10,'plastique',0,1)");
    }

    @Test
    void testBasicAccessors() {
        Dechet d = new Dechet(5, TypeDechetEnum.TypeDechet.plastique, null, null);
        assertEquals(5, d.getIdentifiantDechet());
        assertEquals(TypeDechetEnum.TypeDechet.plastique, d.getType());
        d.setType(TypeDechetEnum.TypeDechet.verre);
        assertEquals(TypeDechetEnum.TypeDechet.verre, d.getType());
    }

    @Test
    void testModifierEquals() {
        Dechet d = new Dechet(1, TypeDechetEnum.TypeDechet.metal, null, null);
        d.modifierDechet(Map.of("type", TypeDechetEnum.TypeDechet.carton));
        assertEquals(TypeDechetEnum.TypeDechet.carton, d.getType());
        Dechet e = new Dechet(1, TypeDechetEnum.TypeDechet.carton, null, null);
        assertEquals(d, e);
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Dechet d = Dechet.ajouterDechet(
                TypeDechetEnum.TypeDechet.plastique,
                new Depot(1,null,null,0f),
                new PoubelleIntelligente(1,"",10f,TypeDechetEnum.TypeDechet.plastique,0f,null,new HashSet<>(),new HashSet<>())
        );

        // CREATE
        DechetDAO.ajouterDechetBDD(d);
        int id = d.getIdentifiantDechet();

        // READ
        Dechet lu = DechetDAO.lireDechetBDD(id);
        assertNotNull(lu);
        assertEquals(TypeDechetEnum.TypeDechet.plastique, lu.getType());

        // UPDATE
        d.setType(TypeDechetEnum.TypeDechet.verre);
        DechetDAO.actualiserDechetBDD(d, "typeDechet");
        lu = DechetDAO.lireDechetBDD(id);
        assertEquals(TypeDechetEnum.TypeDechet.verre, lu.getType());

        // DELETE
        DechetDAO.supprimerDechetBDD(d);
        assertNull(DechetDAO.lireDechetBDD(id));
    }
}
