package main.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.backend.fonctions.Main;
import main.backend.fonctions.Utilisateur; // Ajout de l'import
import main.view.MenuUtilisateurView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnexionView extends Application {

    private static final String URL = "jdbc:mysql://localhost:3306/projetjava";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";

    private Main mainApp;

    @Override
    public void start(Stage primaryStage) {
        Label titre = new Label("Connexion");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label prenomLabel = new Label("Prénom :");
        TextField prenomField = new TextField();

        Label nomLabel = new Label("Nom :");
        TextField nomField = new TextField();

        Label messageErreur = new Label();

        Button connexionBtn = new Button("Se connecter");

        connexionBtn.setOnAction(e -> {
            String prenom = prenomField.getText();
            String nom = nomField.getText();

            if (prenom.isBlank() || nom.isBlank()) {
                messageErreur.setText("Veuillez remplir tous les champs.");
            } else {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "SELECT * FROM Utilisateur WHERE nom = ? AND prenom = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nom);
                    stmt.setString(2, prenom);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        // Création de l'objet Utilisateur avec les données de la BDD
                        Utilisateur utilisateurConnecte = new Utilisateur(
                                rs.getInt("identifiantUtilisateur"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getInt("pointsFidelite")
                        );

                        // Stockage de l'utilisateur dans Main
                        if (mainApp != null) {
                            mainApp.setCurrentUser(utilisateurConnecte);
                        }

                        // Accès au menu
                        MenuUtilisateurView menu = new MenuUtilisateurView(nom, prenom);
                        menu.setMainApp(mainApp); // Passage de la référence à Main
                        menu.start(new Stage());
                        primaryStage.close();
                    } else {
                        messageErreur.setText("Nom ou prénom incorrect.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    messageErreur.setText("Erreur de connexion à la base de données.");
                }
            }
        });

        VBox root = new VBox(15, titre, prenomLabel, prenomField, nomLabel, nomField, connexionBtn, messageErreur);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connexion");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}