package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class PoubelleIntelligenteTest {

    @BeforeEach
    void initDb() throws Exception {
        // On vide proprement seulement les bonnes tables associées
        requete("DELETE FROM gerer WHERE identifiantPoubelleIntelligente = 1");
        requete("DELETE FROM stocker WHERE identifiantPoubelleIntelligente = 1");
        requete("DELETE FROM jeter WHERE identifiantPoubelleIntelligente = 1");
        requete("DELETE FROM dechet WHERE identifiantDechet = 1"); // on sécurise
        requete("DELETE FROM depot WHERE identifiantDepot = 1");    // au cas où
        requete("DELETE FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = 1");
        requete("DELETE FROM centredetri WHERE identifiantCentreDeTri = 1");

        // Remet un CentreDeTri pour les tests
        requete("INSERT INTO CentreDeTri (identifiantCentreDeTri, nom, adresse) VALUES (1,'C1','Adr1')");
    }

    @Test
    void testBasicAccessors() {
        PoubelleIntelligente p = new PoubelleIntelligente(
                5, "E", 10f, TypeDechetEnum.TypeDechet.carton, 2f,
                null, new HashSet<>(), new HashSet<>()
        );
        assertEquals(5, p.getIdentifiantPoubelle());
        assertEquals("E", p.getEmplacement());
        p.setEmplacement("E2");
        assertEquals("E2", p.getEmplacement());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        PoubelleIntelligente p = PoubelleIntelligente.ajouterPoubelle(
                "E", 10f, TypeDechetEnum.TypeDechet.plastique, 0f,
                new CentreDeTri(1, "", "", new HashSet<>(), new ArrayList<>(), new ArrayList<>()),
                new HashSet<>(), new HashSet<>()
        );

        // CREATE
        PoubelleIntelligenteDAO.ajouterPoubelleIntelligenteBDD(p);
        int id = p.getIdentifiantPoubelle();

        // READ
        PoubelleIntelligente lu = PoubelleIntelligenteDAO.lirePoubelleBDD(id);
        assertNotNull(lu);
        assertEquals("E", lu.getEmplacement());

        // UPDATE
        p.setPoids(5f);
        PoubelleIntelligenteDAO.actualiserPoubelleIntelligenteBDD(p, "poids");
        lu = PoubelleIntelligenteDAO.lirePoubelleBDD(id);
        assertEquals(5f, lu.getPoids());

        // DELETE
        PoubelleIntelligenteDAO.supprimerPoubelleIntelligenteBDD(p);
        assertNull(PoubelleIntelligenteDAO.lirePoubelleBDD(id));
    }
}
