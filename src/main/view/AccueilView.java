import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccueilView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Titre
        Label titre = new Label("Bienvenue sur TriPlus");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Boutons du menu
        Button creerUtilisateurBtn = new Button("Créer un compte utilisateur");
        Button deposerDechetsBtn = new Button("Déposer des déchets");
        Button voirPointsBtn = new Button("Voir mes points fidélité");
        Button historiqueBtn = new Button("Voir l’historique des dépôts");
        Button promosBtn = new Button("Consulter les promotions");

        // ➤ Pour l'instant, les boutons n'ont pas d'action → on les branche plus tard
        creerUtilisateurBtn.setOnAction(e -> System.out.println("Créer un compte utilisateur"));
        deposerDechetsBtn.setOnAction(e -> System.out.println("Déposer des déchets"));
        voirPointsBtn.setOnAction(e -> System.out.println("Voir mes points fidélité"));
        historiqueBtn.setOnAction(e -> System.out.println("Historique des dépôts"));
        promosBtn.setOnAction(e -> System.out.println("Voir promotions"));

        // Layout vertical
        VBox root = new VBox(15, titre, creerUtilisateurBtn, deposerDechetsBtn, voirPointsBtn, historiqueBtn, promosBtn);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-alignment: center;");

        // Création de la scène
        Scene scene = new Scene(root, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu principal");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
