// src/test/java/main/backend/fonctionsTest/UtilisateurTest.java
package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {
    @BeforeEach
    void initDb() throws Exception {
        requete("DELETE FROM utiliser     WHERE identifiantPromotion = 1");
        requete("DELETE FROM depot       WHERE identifiantDepot = 1");
        requete("DELETE FROM utilisateur WHERE identifiantUtilisateur = 1");
        requete("INSERT INTO promotion     (identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (1,0,0)");
    }

    @Test
    void testBasicAccessors() {
        Utilisateur u = new Utilisateur(5,"N","P", 4f);
        assertEquals(5, u.getIdUtilisateur());
        assertEquals("N", u.getNom());
        u.setPrenom("X");
        assertEquals("X", u.getPrenom());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Utilisateur u = Utilisateur.ajouterUtilisateur("N","P",2f);

        // CREATE
        UtilisateurDAO.ajouterUtilisateurBDD(u);
        int id = u.getIdUtilisateur();

        // READ
        Utilisateur lu = UtilisateurDAO.lireUtilisateurBDD(id);
        assertNotNull(lu);
        assertEquals("N", lu.getNom());

        // UPDATE
        u.setPointsFidelite(5f);
        UtilisateurDAO.actualiserUtilisateurBDD(u, "pointsFidelite");
        lu = UtilisateurDAO.lireUtilisateurBDD(id);
        assertEquals(5f, lu.getPointsFidelite());

        // DELETE
        UtilisateurDAO.supprimerUtilisateurBDD(u);
        assertNull(UtilisateurDAO.lireUtilisateurBDD(id));
    }
}
