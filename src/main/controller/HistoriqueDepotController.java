package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.*;
import main.util.DateUtil;
import main.Depot.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistoriqueDepotController {

    @FXML
    private TableView<Depot> depotTable;
    @FXML
    private TableColumn<Depot, Integer> idColumn;
    @FXML
    private TableColumn<Depot, LocalDate> dateColumn;
    @FXML
    private TableColumn<Depot, LocalTime> heureColumn;
    @FXML
    private TableColumn<Depot, String> typeColumn;
    @FXML
    private TableColumn<Depot, Number> poidsColumn;
    @FXML
    private TableColumn<Depot, Number> pointsColumn;

    // Reference to the main application.
    private Main mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public HistoriqueDepotController() {
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        idColumn.setCellValueFactory((cellData -> cellData.getValue().idProperty().asObject()));
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        heureColumn.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        poidsColumn.setCellValueFactory(cellData -> cellData.getValue().pointsProperty());
        pointsColumn.setCellValueFactory(cellData -> cellData.getValue().pointsProperty());

        // Formatage de la date (optionnel)
        dateColumn.setCellFactory(column -> new TableCell<Depot, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? null : DateUtil.format(date));
            }
        });

        // Formatage des nombres
        poidsColumn.setCellFactory(column -> new TableCell<Depot, Number>() {
            @Override
            protected void updateItem(Number poids, boolean empty) {
                super.updateItem(poids, empty);
                setText(empty || poids == null ? "" : String.format("%.2f kg", poids.floatValue()));
            }
        });

        pointsColumn.setCellFactory(column -> new TableCell<Depot, Number>() {
            @Override
            protected void updateItem(Number points, boolean empty) {
                super.updateItem(points, empty);
                setText(empty || points == null ? "" : String.format("%.0f pts", points.floatValue()));
            }
        });
    }

    /**
     * Appelé par l'application main pour donner une référence à elle-même.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        updateTableData();
    }

    private void updateTableData() {
        try {
            if (mainApp == null) {
                System.err.println("MainApp n'est pas initialisé");
                return;
            }

            ObservableList<Depot> fullList = mainApp.getDepotData();
            if (fullList == null) {
                System.err.println("La liste des dépôts est null");
                depotTable.setItems(FXCollections.emptyObservableList());
                return;
            }

            int limit = Math.min(5, fullList.size());
            ObservableList<Depot> limitedList = FXCollections.observableArrayList(
                    fullList.subList(0, limit)
            );
            depotTable.setItems(limitedList);

        } catch (Exception e) {
            System.err.println("Erreur critique: " + e.getMessage());
            e.printStackTrace();
            depotTable.setItems(FXCollections.emptyObservableList());
        }
    }
}
