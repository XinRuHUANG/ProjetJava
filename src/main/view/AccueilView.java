package Main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Import pour ouvrir CreerUtilisateurView
import Main.CreerUtilisateurView;

public class AccueilView extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titre = new Label("Bienvenue sur TriPlus");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Tous les boutons
        Button creerUtilisateurBtn = new Button("Créer un compte utilisateur");
        Button deposerDechetsBtn = new Button("Déposer des déchets");
        Button voirPointsBtn = new Button("Voir mes points fidélité");
        Button historiqueBtn = new Button("Voir l'historique des dépôts");
        Button promosBtn = new Button("Consulter les promotions");

        // Action pour créer un compte utilisateur
        creerUtilisateurBtn.setOnAction(e -> {
            Stage stageCreer = new Stage();
            try {
                new CreerUtilisateurView().start(stageCreer);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Actions provisoires pour les autres boutons (à compléter plus tard)
        deposerDechetsBtn.setOnAction(e -> System.out.println("Déposer des déchets"));
        voirPointsBtn.setOnAction(e -> System.out.println("Voir mes points fidélité"));
        historiqueBtn.setOnAction(e -> System.out.println("Voir l'historique des dépôts"));
        promosBtn.setOnAction(e -> System.out.println("Voir promotions"));

        // Layout
        VBox root = new VBox(15, titre, creerUtilisateurBtn, deposerDechetsBtn, voirPointsBtn, historiqueBtn, promosBtn);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu principal");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
