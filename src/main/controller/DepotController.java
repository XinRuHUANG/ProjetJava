package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class DepotController {

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private TextField poidsTextField;

    @FXML
    private TextField pointsTextField;

    @FXML
    private Button deposerButton;

    @FXML
    private void initialize() {
        // Initialiser la ChoiceBox avec les types de déchets
        typeChoiceBox.getItems().addAll("Plastique", "Carton", "Verre");

        // Action sur le bouton
        deposerButton.setOnAction(event -> calculerPoints());
    }

    private void calculerPoints() {
        String type = typeChoiceBox.getValue();
        String poidsStr = poidsTextField.getText();

        if (type == null || poidsStr.isEmpty()) {
            pointsTextField.setText("Erreur");
            return;
        }

        try {
            double poids = Double.parseDouble(poidsStr);
            double points = getPointsPourType(type, poids);
            pointsTextField.setText(String.format("%.2f", points)); // arrondi 2 chiffres après la virgule
        } catch (NumberFormatException e) {
            pointsTextField.setText("Erreur");
        }
    }

    private double getPointsPourType(String type, double poids) {
        double facteur = switch (type.toLowerCase()) {
            case "plastique" -> 2.0;
            case "carton" -> 1.5;
            case "verre" -> 1.2;
            default -> 1.0;
        };

        return poids * facteur;
    }
}