package main.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Utilisateur;
import main.UtilisateurDAO;

public class CreerUtilisateurView extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titre = new Label("Créer un compte utilisateur");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label prenomLabel = new Label("Prénom :");
        TextField prenomField = new TextField();

        Label nomLabel = new Label("Nom :");
        TextField nomField = new TextField();

        Label confirmation = new Label();

        Button creerBtn = new Button("Créer le compte");
        creerBtn.setOnAction(e -> {
            String prenom = prenomField.getText();
            String nom = nomField.getText();

            if (prenom.isBlank() || nom.isBlank()) {
                confirmation.setText("Veuillez remplir tous les champs.");
            } else {
                try {
                    Utilisateur utilisateur = new Utilisateur(0, nom, prenom, 0.0f);
                    UtilisateurDAO.ajouterUtilisateurBDD(utilisateur);
                    confirmation.setText("✔ Compte créé pour " + prenom + " " + nom);
                    prenomField.clear();
                    nomField.clear();
                } catch (Exception ex) {
                    confirmation.setText("❌ Erreur : " + ex.getMessage());
                }
            }
        });

        Button retourBtn = new Button("Retour au menu");
        retourBtn.setOnAction(e -> primaryStage.close());

        VBox root = new VBox(15, titre, prenomLabel, prenomField, nomLabel, nomField, creerBtn, confirmation, retourBtn);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Création d'un utilisateur");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

