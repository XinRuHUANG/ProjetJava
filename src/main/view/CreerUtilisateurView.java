import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreerUtilisateurView extends Application {

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
            String nom = nomField.getText();
            String prenom = prenomField.getText();

            if (nom.isBlank() || prenom.isBlank()) {
                confirmation.setText("Veuillez remplir tous les champs.");
            } else {
                confirmation.setText("✔ Compte créé pour " + prenom + " " + nom);
                prenomField.clear();
                nomField.clear();
            }
        });

        VBox root = new VBox(10, titre, prenomLabel, prenomField, nomLabel, nomField, creerBtn, confirmation);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Création utilisateur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

