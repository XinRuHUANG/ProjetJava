package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class ContratTest {
    @BeforeEach
    void initDb() throws Exception {
        // Nettoyage des tables dans le bon ordre pour éviter les violations de contraintes
        requete("DELETE FROM definir;");
        requete("DELETE FROM commercer;");
        requete("DELETE FROM contrat;");
        requete("DELETE FROM promotion;");
        requete("DELETE FROM centreDeTri;");
        requete("DELETE FROM commerce;");

        // Réinsertion de données de base
        requete("INSERT INTO centreDeTri (identifiantCentreDeTri, nom, adresse) VALUES (1, 'CentreTest', 'AdresseTest');");
        requete("INSERT INTO commerce (identifiantCommerce, nom) VALUES (1, 'CommerceTest');");
        requete("INSERT INTO promotion (identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (1, 10.0, 5.0);");
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
        Contrat c = new Contrat(1, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 1),
                "cl", null, null, null);
        c.modifierContrat(Map.of("clauses", "cl2"));
        assertEquals("cl2", c.getClauses());
        Contrat d = new Contrat(1, c.getDateDebut(), c.getDateFin(), "cl2", null, null, null);
        assertEquals(c, d);
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        // 1. Ajouter la promotion
        Promotion promo = new Promotion(0, 15f, 3f);
        PromotionDAO.ajouterPromotionBDD(promo);

        // 2. Ajouter le contrat
        Contrat c = new Contrat(0, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31), "clauses", null, null, null);
        ContratDAO.ajouterContratBDD(c);

        // 3. Lier contrat à promotion
        ContratDAO.lierPromotionContrat(c, promo);

        // 4. Tester lecture du contrat
        Contrat lu = ContratDAO.lireContratBDD(c.getIdContrat());
        assertNotNull(lu);
        assertEquals("clauses", lu.getClauses());

        // 5. UPDATE
        c.setClauses("modif");
        ContratDAO.actualiserContratBDD(c, "clauses");
        lu = ContratDAO.lireContratBDD(c.getIdContrat());
        assertEquals("modif", lu.getClauses());

        // 6. DELETE
        ContratDAO.supprimerContratBDD(c);
        assertNull(ContratDAO.lireContratBDD(c.getIdContrat()));
    }
}
