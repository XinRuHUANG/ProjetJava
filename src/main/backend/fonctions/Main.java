package main.backend.fonctions;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import main.backend.fonctions.Depot;
import main.controller.HistoriqueDepotController;
import main.controller.RootLayoutController;

import main.view.AccueilView;
import main.view.CreerUtilisateurView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {

    private final ObservableList<Depot> depotData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;
    private File currentFile = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TriPlus - Gestion des dépôts");

        // Initialisation des données exemple (optionnel)
        initSampleData();

        // Afficher l'écran d'accueil
        showAccueilView();
    }

    public void showAccueilView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-alignment: center;");

        Label titre = new Label("Bienvenue sur TriPlus");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button creerUtilisateurBtn = new Button("Créer un compte utilisateur");
        creerUtilisateurBtn.setOnAction(e -> new CreerUtilisateurView().start(new Stage()));

        Button gestionDepotsBtn = new Button("Gérer les dépôts");
        gestionDepotsBtn.setOnAction(e -> showHistoriqueDepotView());

        root.getChildren().addAll(titre, creerUtilisateurBtn, gestionDepotsBtn);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/main/view/RootLayout.fxml"));
            rootLayout = loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSampleData() {
        depotData.add(new Depot(1, LocalDate.of(2025,4,25), LocalTime.of(20,19), 5.0f));
        depotData.add(new Depot(2, LocalDate.of(2025,2,25), LocalTime.of(20,19), 5.0f));
        depotData.add(new Depot(3, LocalDate.of(2025,3,25), LocalTime.of(20,19), 5.0f));
        depotData.add(new Depot(4, LocalDate.of(2025,8,25), LocalTime.of(20,19), 5.0f));
        depotData.add(new Depot(5, LocalDate.of(2025,7,25), LocalTime.of(20,19), 5.0f));
        depotData.add(new Depot(6, LocalDate.of(2025,9,25), LocalTime.of(20,19), 5.0f));
    }

    private void showHistoriqueDepotView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/HistoriqueDepot.fxml"));
            Parent root = loader.load();

            HistoriqueDepotController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'interface");
        }
    }

    public void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            currentFile = file;
            saveFile();
        }
    }

    public void handleSave() {
        if (currentFile != null) {
            saveFile();
        } else {
            handleSaveAs();
        }
    }

    private void saveFile() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sauvegarde réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été sauvegardées dans:\n" + currentFile.getPath());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de sauvegarder");
            alert.setContentText("Une erreur est survenue lors de la sauvegarde:\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            currentFile = file;
            loadFile();
        }
    }

    private void loadFile() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chargement réussi");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été chargées depuis:\n" + currentFile.getPath());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de charger");
            alert.setContentText("Une erreur est survenue lors du chargement:\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleNew() {
        depotData.clear();
        currentFile = null;
        initSampleData();
    }

    public void handleExit() {
        System.exit(0);
    }

    public ObservableList<Depot> getDepotData() {
        return depotData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}