package main.backend.fonctionsTest;

import main.backend.fonctions.CentreDeTri;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;
import static main.backend.fonctions.CentreDeTriDAO.*;
import static org.junit.jupiter.api.Assertions.*;

class CentreDeTriTest {

    @BeforeEach
    void initDb() throws Exception {
        // on supprime d'abord toute trace des tests précédents
        // (aucune référence à identifiantCentreDeTri ici)
        requete(
                "DELETE FROM `commercer`;"
        );
        requete(
                "DELETE FROM `CentreDeTri` " +
                        "WHERE `nom` IN ('TEST_CTR','MOD_CTR');"
        );
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        // === CREATE ===
        CentreDeTri ct = new CentreDeTri(
                0,
                "TEST_CTR",
                "ADRESSE_TEST",
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        ajouterCentreDeTriBDD(ct);
        assertTrue(ct.getIdCentreDeTri() >= 1, "L'ID doit être >= 1");

        // === READ direct ===
        var rows = requeteAvecAffichage(
                "SELECT `nom`,`adresse` FROM `CentreDeTri` " +
                        "WHERE `identifiantCentreDeTri` = " + ct.getIdCentreDeTri() + ";",
                new ArrayList<>(List.of("nom","adresse"))
        );
        assertEquals(1, rows.size(), "Doit trouver exactement 1 ligne");
        assertEquals("TEST_CTR", rows.get(0).get("nom"));
        assertEquals("ADRESSE_TEST", rows.get(0).get("adresse"));

        // === UPDATE via DAO ===
        ct.setNom("MOD_CTR");
        ct.setAdresse("ADRESSE_MOD");
        actualiserCentreBDD(ct, "nom", "adresse");

        rows = requeteAvecAffichage(
                "SELECT `nom`,`adresse` FROM `CentreDeTri` " +
                        "WHERE `identifiantCentreDeTri` = " + ct.getIdCentreDeTri() + ";",
                new ArrayList<>(List.of("nom","adresse"))
        );
        assertEquals(1, rows.size());
        assertEquals("MOD_CTR", rows.get(0).get("nom"));
        assertEquals("ADRESSE_MOD", rows.get(0).get("adresse"));

        // === DELETE via DAO ===
        supprimerCentreBDD(ct);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM `CentreDeTri` " +
                        "WHERE `identifiantCentreDeTri` = " + ct.getIdCentreDeTri() + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
