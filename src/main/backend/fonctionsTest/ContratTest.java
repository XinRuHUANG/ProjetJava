// src/test/java/main/backend/fonctionsTest/ContratTest.java
package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class ContratTest {
    @BeforeEach
    void initDb() throws Exception {
        requete("DELETE FROM definir WHERE identifiantPromotion = 1");
        requete("DELETE FROM contrat WHERE identifiantContrat = 1");
        requete("DELETE FROM centredetri WHERE identifiantCentreDeTri = 1");
        requete("DELETE FROM commerce WHERE identifiantCommerce = 1");
        requete("DELETE FROM promotion WHERE identifiantPromotion = 1");
        requete("INSERT INTO centredetri (identifiantCentreDeTri, nom, adresse) VALUES (1,'C1','Adr1')");
        requete("INSERT INTO commerce      (identifiantCommerce,       nom) VALUES (1,'M1')");
        requete("INSERT INTO promotion     (identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (1,10.0,5.0)");
    }

    @Test
    void testBasicAccessors() {
        Contrat c = new Contrat(5, LocalDate.now(), LocalDate.now().plusDays(1), "cl", null, null, null);
        assertEquals(5, c.getIdContrat());
        assertEquals("cl", c.getClauses());
        c.setClauses("X");
        assertEquals("X", c.getClauses());
    }

    @Test
    void testModifierEquals() {
        Contrat c = new Contrat(1, LocalDate.of(2020,1,1), LocalDate.of(2020,2,1),
                "cl", null, null, null);
        c.modifierContrat(Map.of("clauses", "cl2"));
        assertEquals("cl2", c.getClauses());
        Contrat d = new Contrat(1, c.getDateDebut(), c.getDateFin(), "cl2", null, null, null);
        assertEquals(c, d);
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Contrat c = Contrat.ajouterContrat(
                LocalDate.of(2021,1,1),
                LocalDate.of(2021,12,31),
                "clauses",
                new CentreDeTri(1,"","",new HashSet<>(),new ArrayList<>(),new ArrayList<>()),
                new Commerce(1,"",new ArrayList<>(),new ArrayList<>(),new HashSet<>()),
                new Promotion(1,0f,0f)
        );

        // CREATE
        ContratDAO.ajouterContratBDD(c);
        int id = c.getIdContrat();

        // READ
        Contrat lu = ContratDAO.lireContratBDD(id);
        assertNotNull(lu);
        assertEquals("clauses", lu.getClauses());

        // UPDATE
        c.setClauses("cl2");
        ContratDAO.actualiserContratBDD(c, "clauses");
        lu = ContratDAO.lireContratBDD(id);
        assertEquals("cl2", lu.getClauses());

        // DELETE
        ContratDAO.supprimerContratBDD(c);
        assertNull(ContratDAO.lireContratBDD(id));
    }
}
