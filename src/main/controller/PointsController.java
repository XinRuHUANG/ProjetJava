package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.backend.fonctions.Main;
import main.backend.fonctions.Utilisateur;

public class PointsController {

    @FXML
    private Text pointsText;

    @FXML
    private Button menuButton;

    private Main mainApp;
    private Utilisateur currentUser;

    @FXML
    public void initialize() {
        menuButton.setOnAction(event -> {
            if (mainApp != null) {
                mainApp.showMenuUtilisateurView(currentUser);
            } else {
                System.out.println("Retour au menu (mainApp est null)");
            }
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setUtilisateur(Utilisateur currentUser) {
        this.currentUser = currentUser;
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        if (currentUser != null) {
            float points = currentUser.getPointsFidelite(); // Supposé : la méthode getPointsFidelite() existe dans Utilisateur
            pointsText.setText(String.valueOf(points));
        } else {
            pointsText.setText("0"); // ou afficher une erreur
        }
    }
}
