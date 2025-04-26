package main.backend.fonctionsTest;

import main.backend.fonctions.Commerce;
import main.backend.fonctions.CentreDeTri;
import main.backend.fonctions.Contrat;
import main.backend.fonctions.CategorieDeProduits;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.CommerceDAO.*;

class CommerceTest {

    @Test
    void testBasicAccessors() {
        Commerce c = new Commerce(
                5,
                "Magasin",
                new ArrayList<>(),
                new ArrayList<>(),
                new HashSet<>()
        );
        assertNotNull(c.getCommercer());
        assertNotNull(c.getContrat());
        assertNotNull(c.getProposer());
        assertEquals(5, c.getIdentifiantCommerce());
        assertEquals("Magasin", c.getNom());
        c.setNom("Boutique");
        assertEquals("Boutique", c.getNom());
    }

    @Test
    void testModifierEquals() {
        Commerce c = new Commerce(1, "A",
                new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        c.modifierCommerce(Map.of("nom", "B"));
        assertEquals("B", c.getNom());
        Commerce d = new Commerce(1, "B",
                c.getCommercer(), c.getContrat(), c.getProposer());
        assertEquals(c, d);
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        List<CentreDeTri> centres = List.of(new CentreDeTri(1,"x","x",new HashSet<>(),new ArrayList<>(),new ArrayList<>()));
        List<Contrat>    contrats = List.of(new Contrat(1,null,null,"",null,null,null));
        Set<CategorieDeProduits> cats = Set.of(new CategorieDeProduits(1,"x",new HashSet<>(),new HashSet<>()));
        Commerce c = new Commerce(0, "TSTCOM", centres, contrats, cats);
        ajouterCommerceBDD(c);
        int id = c.getIdentifiantCommerce();

        var rows = requeteAvecAffichage(
                "SELECT nom FROM commerce WHERE identifiantCommerce = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals("TSTCOM", rows.get(0).get("nom"));

        c.setNom("TST2");
        actualiserCommerceBDD(c, "nom");
        rows = requeteAvecAffichage(
                "SELECT nom FROM commerce WHERE identifiantCommerce = " + id + ";",
                new ArrayList<>(List.of("nom"))
        );
        assertEquals("TST2", rows.get(0).get("nom"));

        supprimerCommerceBDD(c);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM commerce WHERE identifiantCommerce = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
