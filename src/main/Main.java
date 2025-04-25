package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {  // Étendre Application

    private final ObservableList<Depot> depotData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Main() {
        // Add some sample data
        depotData.add(new Depot(1, LocalDate.of(2025,4,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(2, LocalDate.of(2025,2,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(3, LocalDate.of(2025,3,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(4, LocalDate.of(2025,8,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(5, LocalDate.of(2025,7,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));
        depotData.add(new Depot(6, LocalDate.of(2025,9,25), LocalTime.of(20,19), 5.0f, "verre", 3.0f));

    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello JavaFX!");
        Scene scene = new Scene(new StackPane(label), 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();  // Lancer l'application JavaFX
    }

    public ObservableList<Depot> getDepotData() {
        return depotData;
    }
}