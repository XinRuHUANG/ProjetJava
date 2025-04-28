package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccueilView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu principal");

        Button creerUtilisateurBtn = new Button("Créer un compte utilisateur");
        Button connexionBtn = new Button("Connexion");

        creerUtilisateurBtn.setOnAction(e -> {
            Stage creerStage = new Stage();
            new CreerUtilisateurView().start(creerStage);
        });

        connexionBtn.setOnAction(e -> {
            Stage connexionStage = new Stage();
            new ConnexionView().start(connexionStage);
        });

        VBox root = new VBox(20, creerUtilisateurBtn, connexionBtn);
        root.setStyle("-fx-alignment: center; -fx-padding: 30;");
        Scene scene = new Scene(root, 400, 350);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
