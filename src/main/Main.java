package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.controller.HistoriqueDepotController;
import main.controller.RootLayoutController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {  // Étendre Application

    private final ObservableList<Depot> depotData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;
    private File currentFile = null;

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello JavaFX!");
        Scene scene = new Scene(new StackPane(label), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initialise le layout racine (menu principal)
     */
    public void initRootLayout() {
        try {
            // Charge le layout racine depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Donne au contrôleur l'accès à mainApp
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // Affiche la scène contenant le layout racine
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSampleData() {
        depotData.add(new Depot(1, LocalDate.of(2025,4,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(2, LocalDate.of(2025,2,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(3, LocalDate.of(2025,3,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(4, LocalDate.of(2025,8,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(5, LocalDate.of(2025,7,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(6, LocalDate.of(2025,9,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
    }

    private void showHistoriqueDepotView() {
        try {
            // Charge HistoriqueDepot.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/HistoriqueDepot.fxml"));
            Parent root = loader.load();

            // Donne au contrôleur l'accès à mainApp
            HistoriqueDepotController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'interface");
        }
    }


    /**
     * Ouvre une boîte de dialogue pour sauvegarder le fichier.
     */
    public void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Définit l'extension de filtre
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Montre la boîte de dialogue d'enregistrement
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // S'assure qu'il a l'extension correcte
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            currentFile = file;
            saveFile();
        }
    }

    /**
     * Sauvegarde les données dans le fichier courant.
     * S'il n'y a pas de fichier courant, la méthode "save as" est appelée.
     */
    public void handleSave() {
        if (currentFile != null) {
            saveFile();
        } else {
            handleSaveAs();
        }
    }

    private void saveFile() {
        try {
            // Ici vous devriez implémenter la logique pour sauvegarder vos données
            // Par exemple, sérialiser depotData dans le fichier currentFile
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

    /**
     * Ouvre une boîte de dialogue pour charger un fichier.
     */
    public void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Définit l'extension de filtre
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Montre la boîte de dialogue d'ouverture
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            currentFile = file;
            loadFile();
        }
    }

    private void loadFile() {
        try {
            // Ici vous devriez implémenter la logique pour charger vos données
            // Par exemple, désérialiser le fichier currentFile dans depotData
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

    /**
     * Crée un nouveau fichier.
     */
    public void handleNew() {
        depotData.clear();
        currentFile = null;
        initSampleData(); // Ou vous pourriez vouloir commencer avec une liste vide
    }

    /**
     * Ferme l'application.
     */
    public void handleExit() {
        // Vous pourriez vouloir ajouter une vérification pour les modifications non sauvegardées
        System.exit(0);
    }

    public ObservableList<Depot> getDepotData() {
        return depotData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}