package main.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import main.backend.fonctions.Main;
import main.controller.HistoriqueDepotController.*;

import java.io.IOException;

import static main.backend.fonctions.Main.*;

public class MenuUtilisateurView extends Application {

    private Stage stage;
    private Main mainApp;

    private static String nomUtilisateur;
    private static String prenomUtilisateur;

    public MenuUtilisateurView() {
        // Constructeur vide pour JavaFX
    }

    public MenuUtilisateurView(String nom, String prenom) {
        nomUtilisateur = nom;
        prenomUtilisateur = prenom;
    }

    @Override
    public void start(Stage primaryStage) {
        Label bienvenue = new Label("Bienvenue " + prenomUtilisateur + " " + nomUtilisateur);
        bienvenue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button deposerBtn = new Button("Déposer des déchets");
        Button voirPointsBtn = new Button("Voir mes points fidélité");
        Button historiqueBtn = new Button("Voir l'historique des dépôts");
        Button promosBtn = new Button("Consulter les promotions");

        // Gestionnaire d'événements pour l'historique des dépôts
        historiqueBtn.setOnAction(e -> showHistoriqueDepot());
        deposerBtn.setOnAction(e -> showDepot());
        voirPointsBtn.setOnAction(e -> showPoints());
        promosBtn.setOnAction(e -> {
            try {
                showPromotions();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox root = new VBox(15, bienvenue, deposerBtn, voirPointsBtn, historiqueBtn, promosBtn);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu principal");
        primaryStage.show();
    }

    private void showPromotions() throws IOException {
        if(mainApp != null) {
            mainApp.showPromotionsView();
        } else {
            System.err.println("Erreur: mainApp est null");
        }
    }

    private void showDepot() {
        if(mainApp != null) {
            mainApp.showDepotView();
        } else {
            System.err.println("Erreur: mainApp est null");
        }
    }

    private void showHistoriqueDepot() {
        if(mainApp != null) {
            mainApp.showHistoriqueDepotView();
        } else {
            System.err.println("Erreur: mainApp est null");
        }
    }

    private void showPoints(){
        if(mainApp != null) {
            mainApp.showPointsView();
        } else {
            System.err.println("Erreur: mainApp est null");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}