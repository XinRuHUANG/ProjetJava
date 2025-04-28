package main.backend.fonctionsTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CategorieDeProduitsTest.class,
        CentreDeTriTest.class,
        CommerceTest.class,
        ContratTest.class,
        DechetTest.class,
        DepotTest.class,
        PoubelleIntelligenteTest.class,
        PromotionTest.class,
        UtilisateurTest.class
})
public class MainTest {
    // ne contient rien : sert uniquement à lancer la suite
}
