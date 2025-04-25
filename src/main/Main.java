package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.controller.HistoriqueDepotController;
import main.controller.RootLayoutController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {  // Étendre Application

    private final ObservableList<Depot> depotData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;

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