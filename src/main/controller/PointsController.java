package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PointsController {

    @FXML
    private Text pointsText;

    @FXML
    private Button menuButton;

    private int points;

    public void initialize() {
        // Optionnel : mettre des actions sur le bouton Menu si besoin
        menuButton.setOnAction(event -> {
            // Exemple d'action quand on clique sur "Menu"
            System.out.println("Retour au menu");
        });
    }

    public void setPoints(int points) {
        this.points = points;
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        pointsText.setText("Vous avez " + points + " points.");
    }
}