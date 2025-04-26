package main.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class AccueilView {
    public void show(Stage stage) {
        Label titre = new Label("Bienvenue sur TriPlus");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button creerUtilisateurBtn = new Button("Créer un compte utilisateur");
        creerUtilisateurBtn.setOnAction(e -> {
            Stage stageCreer = new Stage();
            try {
                new CreerUtilisateurView().start(stageCreer);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(20, titre, creerUtilisateurBtn);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 300);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu principal");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

