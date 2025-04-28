package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class PromotionView {

    private VBox vbox;

    public PromotionView() {
        // Crée un VBox pour afficher les promotions
        vbox = new VBox();
    }

    // Méthode pour afficher dynamiquement les promotions
    public void displayPromotions(List<String> promotions) {
        // Vider le VBox avant d'ajouter les nouvelles promotions
        vbox.getChildren().clear();

        // Ajouter un titre pour la liste des promotions
        Label title = new Label("Liste des Promotions");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        vbox.getChildren().add(title);

        // Ajouter chaque promotion sous forme de Label dans le VBox
        for (String promotion : promotions) {
            Label promotionLabel = new Label(promotion);
            vbox.getChildren().add(promotionLabel);
        }
    }

    // Méthode pour afficher la scène dans un Stage
    public void show(Stage stage) {
        Scene scene = new Scene(vbox, 400, 300);  // Crée une scène avec le VBox
        stage.setTitle("Promotions");  // Titre de la fenêtre
        stage.setScene(scene);  // Associe la scène au stage
        stage.show();  // Affiche la fenêtre
    }
}
