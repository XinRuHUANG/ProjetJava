package Main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CreerUtilisateurView extends Application {

    private static final String URL = "jdbc:mysql://localhost:3306/projet_java";  // Nom de ta base
    private static final String USER = "root"; // Ton utilisateur MySQL
    private static final String PASSWORD = ""; // Ton mot de passe MySQL (vide si Wamp par défaut)

    @Override
    public void start(Stage primaryStage) {
        Label titre = new Label("Créer un compte utilisateur");

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
                // Insertion dans la BDD
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "INSERT INTO utilisateur (nom, prenom, pointsFidelite) VALUES (?, ?, 0)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nom);
                    stmt.setString(2, prenom);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        confirmation.setText("✔ Compte créé pour " + prenom + " " + nom);
                        prenomField.clear();
                        nomField.clear();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    confirmation.setText("Erreur lors de la création.");
                }
            }
        });

        VBox root = new VBox(10, titre, prenomLabel, prenomField, nomLabel, nomField, creerBtn, confirmation);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Création utilisateur");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
