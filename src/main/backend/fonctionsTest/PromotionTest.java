package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @BeforeEach
    void initDb() throws Exception {
        // Supprimer les dépendances sur la bonne clé primaire uniquement
        requete("DELETE FROM concerner WHERE identifiantCategorieDeProduits = 1");
        requete("DELETE FROM utiliser WHERE identifiantPromotion = 1");
        requete("DELETE FROM definir WHERE identifiantPromotion = 1");
        requete("DELETE FROM promotion WHERE identifiantPromotion = 1");
        requete("DELETE FROM categoriedeproduits WHERE identifiantCategorieDeProduits = 1");
        requete("DELETE FROM contrat WHERE identifiantContrat = 1");

        // Insérer correctement
        requete("INSERT INTO categoriedeproduits (identifiantCategorieDeProduits, nom) VALUES (1,'Cat1')");
        requete("INSERT INTO contrat (identifiantContrat, dateDebut, dateFin, clauses) " +
                "VALUES (1,'2025-01-01','2025-12-31','Clauses test')");
    }

    @Test
    void testBasicAccessors() {
        Promotion p = new Promotion(5, 10f, 2f);
        assertEquals(5, p.getIdPromotion());
        assertEquals(10f, p.getPourcentageRemise());
        p.setPourcentageRemise(20f);
        assertEquals(20f, p.getPourcentageRemise());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        // Correction ici : new Promotion
        Promotion p = new Promotion(0, 15f, 3f);

        // CREATE
        PromotionDAO.ajouterPromotionBDD(p);
        int id = p.getIdPromotion();

        // READ
        Promotion lu = PromotionDAO.lirePromotionBDD(id);
        assertNotNull(lu);
        assertEquals(15f, lu.getPourcentageRemise());

        // UPDATE
        p.setPointsRequis(5f);
        PromotionDAO.actualiserPromotionBDD(p, "pointsRequis");
        lu = PromotionDAO.lirePromotionBDD(id);
        assertEquals(5f, lu.getPointsRequis());

        // DELETE
        PromotionDAO.supprimerPromotionBDD(p);
        assertNull(PromotionDAO.lirePromotionBDD(id));
    }

}
