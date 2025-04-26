package main.backend.fonctionsTest;

import main.backend.fonctions.Dechet;
import main.backend.fonctions.Depot;
import main.backend.fonctions.PoubelleIntelligente;
import main.backend.fonctions.TypeDechetEnum.TypeDechet;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.DechetDAO.*;

class DechetTest {

    @Test
    void testTypeAndEquals() {
        Dechet d1 = new Dechet(10, TypeDechet.verre, null, null);
        Dechet d2 = new Dechet(10, TypeDechet.verre, null, null);
        assertEquals(TypeDechet.verre, d1.getType());
        assertEquals(d1, d2);

        d1.modifierDechet(Map.of("type", TypeDechet.carton));
        assertEquals(TypeDechet.carton, d1.getType());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        Depot dep = new Depot(1, null, null, 0f);
        dep.setContenir(new ArrayList<>());
        dep.setPosseder(null);
        dep.setJeter(null);

        PoubelleIntelligente poub = new PoubelleIntelligente(
                1,"x",10f,TypeDechet.verre,0f,null,new HashSet<>(),new HashSet<>());

        Dechet d = new Dechet(0, TypeDechet.plastique, dep, poub);
        ajouterDechetBDD(d);
        int id = d.getIdentifiantDechet();

        var rows = requeteAvecAffichage(
                "SELECT type FROM dechet WHERE identifiantDechet = " + id + ";",
                new ArrayList<>(List.of("type"))
        );
        assertEquals("plastique", rows.get(0).get("type"));

        d.setType(TypeDechet.carton);
        actualiserDechetBDD(d, "type");
        rows = requeteAvecAffichage(
                "SELECT type FROM dechet WHERE identifiantDechet = " + id + ";",
                new ArrayList<>(List.of("type"))
        );
        assertEquals("carton", rows.get(0).get("type"));

        supprimerDechetBDD(d);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM dechet WHERE identifiantDechet = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
