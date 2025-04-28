package main.backend.fonctionsTest;

import main.backend.fonctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static org.junit.jupiter.api.Assertions.*;

class CommerceTest {

    @BeforeEach
    void initDb() throws Exception {
        // On supprime juste les anciens commerces de test
        requete("DELETE FROM proposer WHERE identifiantCommerce = 1;");
        requete("DELETE FROM commercer WHERE identifiantCommerce = 1;");
        requete("DELETE FROM commerce WHERE identifiantCommerce = 1;");

        // Et on s'assure que les autres prérequis existent
        requete("INSERT IGNORE INTO CentreDeTri (identifiantCentreDeTri, nom, adresse) VALUES (1, 'CentreStub', 'AdresseStub');");
        requete("INSERT IGNORE INTO Contrat (identifiantContrat, dateDebut, dateFin, clauses) VALUES (1, '2020-01-01', '2020-12-31', 'ClausesStub');");
        requete("INSERT IGNORE INTO CategorieDeProduits (identifiantCategorieDeProduits, nom) VALUES (1, 'CatStub');");
    }

    @Test
    void testBasicAccessors() {
        Commerce c = new Commerce(5, "Magasin",
                new ArrayList<>(), new ArrayList<>(), new HashSet<>()
        );
        assertEquals(5, c.getIdentifiantCommerce());
        assertEquals("Magasin", c.getNom());
        c.setNom("Boutique");
        assertEquals("Boutique", c.getNom());
        assertNotNull(c.getCommercer());
        assertNotNull(c.getContrat());
        assertNotNull(c.getProposer());
    }

    @Test
    void testModifierEquals() {
        Commerce c = new Commerce(1, "A",
                new ArrayList<>(), new ArrayList<>(), new HashSet<>()
        );
        c.modifierCommerce(
                new java.util.HashMap<String, Object>() {{
                    put("nom", "B");
                }}
        );
        assertEquals("B", c.getNom());
        Commerce d = new Commerce(1, "B",
                c.getCommercer(), c.getContrat(), c.getProposer()
        );
        assertEquals(c, d);
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        List<CentreDeTri> centres = new ArrayList<>(
                Arrays.asList(
                        new CentreDeTri(1, "CentreStub", "AdresseStub",
                                new HashSet<>(), new ArrayList<>(), new ArrayList<>())
                )
        );
        List<Contrat> contrats = new ArrayList<>(
                Arrays.asList(
                        new Contrat(1,
                                java.time.LocalDate.parse("2020-01-01"),
                                java.time.LocalDate.parse("2020-12-31"),
                                "ClausesStub",
                                null, null, null)
                )
        );
        Set<CategorieDeProduits> cats = new HashSet<>(
                Arrays.asList(
                        new CategorieDeProduits(1, "CatStub", new HashSet<>(), new HashSet<>())
                )
        );

        // Ici on crée le commerce
        Commerce c = Commerce.ajouterCommerce("TSTCOM", centres, contrats, cats);

        // CREATE
        CommerceDAO.ajouterCommerceBDD(c);
        int id = c.getIdentifiantCommerce();
        assertTrue(id > 0, "L'ID généré doit être > 0");

        // READ
        Commerce lu = CommerceDAO.lireCommerceBDD(id);
        assertNotNull(lu, "Le commerce doit exister");
        assertEquals("TSTCOM", lu.getNom());

        // UPDATE
        c.setNom("TST2");
        CommerceDAO.actualiserCommerceBDD(c, "nom");
        lu = CommerceDAO.lireCommerceBDD(id);
        assertEquals("TST2", lu.getNom());

        // DELETE
        CommerceDAO.supprimerCommerceBDD(c);
        assertNull(CommerceDAO.lireCommerceBDD(id),
                "Après suppression, on ne doit plus lire le commerce");
    }
}
