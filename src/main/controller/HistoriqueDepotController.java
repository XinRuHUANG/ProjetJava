package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.*;

import java.time.LocalDate;

public class HistoriqueDepotController {

    @FXML
    private TableView<Depot> depotTable;
    @FXML
    private TableColumn<Depot, LocalDate> dateColumn;
    @FXML
    private TableColumn<Depot, String> typeColumn;
    @FXML
    private TableColumn<Depot, Float> poidsColumn;
    @FXML
    private TableColumn<Depot, Integer> pointsColumn;

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
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    }
}
