package main.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.backend.fonctions.Promotion;  // Assurez-vous que Promotion est correctement importée
import main.backend.fonctions.PromotionService;  // Service pour récupérer les promotions

public class PromotionController {

    @FXML
    private VBox vbox;  // VBox lié à l'ID dans le fichier FXML

    private PromotionService promotionService;  // Service qui gère les données de promotions

    // Constructeur
    public PromotionController() {
        this.promotionService = new PromotionService();  // Initialiser le service pour récupérer les promotions
    }

    // Cette méthode est appelée pour charger et afficher les promotions
    public void loadPromotions() {
        try {
            // Récupérer les promotions depuis le service (base de données ou autre source)
            var promotions = promotionService.getAllPromotions();

            if (promotions != null && !promotions.isEmpty()) {
                // Ajouter chaque promotion à la VBox sous forme de Labels
                for (Promotion promo : promotions) {
                    Label promoLabel = new Label("Promotion: " + promo.getNom() + " - " + promo.getRemise() + "% de remise");
                    vbox.getChildren().add(promoLabel);
                }
            } else {
                // Afficher un message si aucune promotion n'est trouvée
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Aucune promotion");
                alert.setHeaderText(null);
                alert.setContentText("Aucune promotion disponible.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Gestion des erreurs
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Problème lors du chargement des promotions");
            alert.setContentText("Une erreur est survenue lors du chargement des promotions.");
            alert.showAndWait();
        }
    }

    // Cette méthode est appelée si le bouton "Voir promotions" est cliqué
    @FXML
    public void displayPromotions() {
        loadPromotions();
    }
}
