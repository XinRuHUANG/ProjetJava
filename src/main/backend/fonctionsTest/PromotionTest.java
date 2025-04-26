package main.backend.fonctionsTest;

import main.backend.fonctions.Promotion;
import main.backend.fonctions.CategorieDeProduits;
import main.backend.fonctions.Contrat;
import main.backend.fonctions.Utilisateur;
import org.junit.jupiter.api.Test;

import java.util.*;

import static main.outils.connexionSQL.requeteAvecAffichage;
import static org.junit.jupiter.api.Assertions.*;
import static main.backend.fonctions.PromotionDAO.*;

class PromotionTest {

    @Test
    void testRemiseEtPointsSetters() {
        Promotion p = new Promotion(5, 20f, 10f);
        assertEquals(20f, p.getPourcentageRemise());
        assertEquals(10f, p.getPointsRequis());
        p.setPourcentageRemise(25f);
        assertEquals(25f, p.getPourcentageRemise());
    }

    @Test
    void testDAO_CreateReadUpdateDelete() throws Exception {
        CategorieDeProduits cat = new CategorieDeProduits(1,"x",new HashSet<>(),new HashSet<>());
        Contrat ctr = new Contrat(1,null,null,"",null,null,null);
        Utilisateur u = new Utilisateur(1,"x","x",0f);

        Promotion p = new Promotion(0, 5f, 2f);
        p.setConcerner(cat);
        p.setDefinir(ctr);
        p.setUtiliser(new HashSet<>(List.of(u)));
        ajouterPromotionBDD(p);
        int id = p.getIdPromotion();

        var rows = requeteAvecAffichage(
                "SELECT pourcentageRemise FROM promotion WHERE identifiantPromotion = " + id + ";",
                new ArrayList<>(List.of("pourcentageRemise"))
        );
        assertEquals("5.0", rows.get(0).get("pourcentageRemise"));

        p.setPourcentageRemise(8f);
        actualiserPromotionBDD(p, "pourcentageRemise");
        rows = requeteAvecAffichage(
                "SELECT pourcentageRemise FROM promotion WHERE identifiantPromotion = " + id + ";",
                new ArrayList<>(List.of("pourcentageRemise"))
        );
        assertEquals("8.0", rows.get(0).get("pourcentageRemise"));

        supprimerPromotionBDD(p);
        rows = requeteAvecAffichage(
                "SELECT COUNT(*) AS cnt FROM promotion WHERE identifiantPromotion = " + id + ";",
                new ArrayList<>(List.of("cnt"))
        );
        assertEquals("0", rows.get(0).get("cnt"));
    }
}
