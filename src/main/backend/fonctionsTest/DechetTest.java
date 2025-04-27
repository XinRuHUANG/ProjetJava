package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class DechetTest {

    @BeforeEach
    void initDb() throws Exception {
        // Supprimer uniquement les données utilisées, sans vider toute la table
        requete("DELETE FROM contenir WHERE identifiantDepot = 1;");
        requete("DELETE FROM jeter WHERE identifiantPoubelleIntelligente = 1 AND identifiantDechet = 1;");
        requete("DELETE FROM dechet WHERE identifiantDechet = 1;");
        requete("DELETE FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = 1;");
        requete("DELETE FROM depot WHERE identifiantDepot = 1;");

        // Ajouter uniquement ce qui est nécessaire au test
        requete("INSERT INTO depot (identifiantDepot, date, heure, points) VALUES (1,'2025-01-01','12:00:00',0);");
        requete("INSERT INTO poubelleintelligente (identifiantPoubelleIntelligente, emplacement, capaciteMaximale, typeDechet, poids) " +
                "VALUES (1,'E',10,'plastique',0);");
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
        // Crée une poubelle minimale (le CentreDeTri est null ici car non lié directement dans la table)
        PoubelleIntelligente poubelle = new PoubelleIntelligente(
                1,
                "E",
                10f,
                TypeDechetEnum.TypeDechet.plastique,
                0f,
                null, // pas de centre directement en BDD
                new HashSet<>(),
                new HashSet<>()
        );

        // Crée un dépôt fictif minimal
        Depot depot = new Depot(1, LocalDate.of(2025, 1, 1), LocalTime.of(12, 0), 0f);

        // Ajoute un déchet
        Dechet d = Dechet.ajouterDechet(TypeDechetEnum.TypeDechet.plastique, depot, poubelle);

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
