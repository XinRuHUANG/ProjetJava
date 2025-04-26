package main.backend.fonctionsTest;

import main.backend.fonctions.PoubelleIntelligente;
import main.backend.fonctions.CentreDeTri;
import main.backend.fonctions.TypeDechetEnum.TypeDechet;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.PoubelleIntelligenteDAO.*;

class PoubelleIntelligenteTest {

    @Test
    void testNotifEtPoids() {
        PoubelleIntelligente p = new PoubelleIntelligente(
                2, "Rue", 100f, TypeDechet.verre, 50f,
                null, new HashSet<>(), new HashSet<>()
        );
        assertFalse(p.notifierCentre());
        float added = p.ajouterPoids(Set.of(TypeDechet.verre, TypeDechet.carton));
        assertTrue(added > 0);
        assertFalse(p.notifierCentre());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        CentreDeTri cen = new CentreDeTri(1,"x","x",new HashSet<>(),new ArrayList<>(),new ArrayList<>());
        PoubelleIntelligente p = new PoubelleIntelligente(
                0, "Lieu", 10f, TypeDechet.carton, 0f,
                cen, new HashSet<>(), new HashSet<>()
        );
        ajouterPoubelleBDD(p);
        int id = p.getIdentifiantPoubelle();

        var rows = requeteAvecAffichage(
                "SELECT emplacement FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = " + id + ";",
                new ArrayList<>(List.of("emplacement"))
        );
        assertEquals("Lieu", rows.get(0).get("emplacement"));

        p.setEmplacement("Lieu2");
        actualiserPoubelleIntelligenteBDD(p, "emplacement");
        rows = requeteAvecAffichage(
                "SELECT emplacement FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = " + id + ";",
                new ArrayList<>(List.of("emplacement"))
        );
        assertEquals("Lieu2", rows.get(0).get("emplacement"));

        supprimerPoubelleBDD(p);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM poubelleintelligente WHERE identifiantPoubelleIntelligente = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
