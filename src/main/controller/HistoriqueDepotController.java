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
    private TableColumn<Depot, Float> pointsColumn;

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
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        poidsColumn.setCellValueFactory(new PropertyValueFactory<>("poids"));

        pointsColumn.setCellValueFactory(cellData -> cellData.getValue().pointsProperty().asObject());

        pointsColumn.setCellValueFactory(cellData -> cellData.getValue().pointsProperty().asObject());


        // Formatage de la date (optionnel)
        dateColumn.setCellFactory(column -> new TableCell<Depot, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? null : DateUtil.format(date));
            }
        });
    }

    /**
     * Appelé par l'application main pour donner une référence à elle-même.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Ajoute les données de la liste à la table
        depotTable.setItems(mainApp.getDepotData());

        // Limite à 5 éléments
        ObservableList<Depot> limitedList = FXCollections.observableArrayList();
        limitedList.addAll(mainApp.getDepotData().subList(0, Math.min(5, mainApp.getDepotData().size())));
        depotTable.setItems(limitedList);
    }
}
