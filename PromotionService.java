package main.backend.fonctions;

import java.util.List;

public class PromotionService {

    public List<Promotion> getAllPromotions() {
        // Simuler la récupération des promotions depuis une base de données ou autre source
        return List.of(
            new Promotion("Promotion 1", 10.0, 100),
            new Promotion("Promotion 2", 15.0, 150)
        );
    }
}
