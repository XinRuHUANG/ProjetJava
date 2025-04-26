package main.backend.fonctionsTest;

import main.backend.fonctions.Contrat;
import main.backend.fonctions.CentreDeTri;
import main.backend.fonctions.Promotion;
import main.backend.fonctions.Commerce;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.ContratDAO.*;

class ContratTest {

    @Test
    void testDatesAndClauses() {
        LocalDate d1 = LocalDate.of(2025,1,1);
        LocalDate d2 = LocalDate.of(2025,12,31);
        Contrat c = new Contrat(3, d1, d2, "Clauses", null, null, null);
        assertEquals(d1, c.getDateDebut());
        assertEquals(d2, c.getDateFin());
        assertEquals("Clauses", c.getClauses());

        c.modifierContrat(Map.of("clauses","Nouvelles"));
        assertEquals("Nouvelles", c.getClauses());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        CentreDeTri cen = new CentreDeTri(1,"x","x",new HashSet<>(),new ArrayList<>(),new ArrayList<>());
        Promotion   pto = new Promotion(1,0f,0f);
        Commerce    com = new Commerce(1,"x",new ArrayList<>(),new ArrayList<>(),new HashSet<>());

        Contrat c = new Contrat(0, LocalDate.now(), LocalDate.now().plusDays(1),
                "C1", cen, com, pto);
        ajouterContratBDD(c);
        int id = c.getIdContrat();

        var rows = requeteAvecAffichage(
                "SELECT clauses FROM contrat WHERE identifiantContrat = " + id + ";",
                new ArrayList<>(List.of("clauses"))
        );
        assertEquals("C1", rows.get(0).get("clauses"));

        c.setClauses("C2");
        actualiserContratBDD(c, "clauses");
        rows = requeteAvecAffichage(
                "SELECT clauses FROM contrat WHERE identifiantContrat = " + id + ";",
                new ArrayList<>(List.of("clauses"))
        );
        assertEquals("C2", rows.get(0).get("clauses"));

        supprimerContratBDD(c);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM contrat WHERE identifiantContrat = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
