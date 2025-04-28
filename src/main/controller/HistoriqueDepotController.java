package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.backend.fonctions.Main;
import main.backend.fonctions.Depot;
import main.util.DateUtil;

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
    private TableColumn<Depot, Number> pointsColumn;

    @FXML
    private Label messageLabel;

    @FXML
    private Button retourMenuButton;
    @FXML
    private Button afficherPlusButton;

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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("identifiantDepot"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        // Formatage de la date (optionnel)
        dateColumn.setCellFactory(column -> new TableCell<Depot, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? null : DateUtil.format(date));
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
            if (depotTable == null) {
                System.err.println("Le TableView 'depotTable' est null !");
                return;
            }

            if (mainApp == null) {
                System.err.println("MainApp n'est pas initialisé");
                return;
            }

            ObservableList<Depot> fullList = mainApp.getDepotData();
            if (fullList == null || fullList.isEmpty()) {
                System.err.println("La liste des dépôts est vide");
                depotTable.setItems(FXCollections.emptyObservableList());

                // Afficher le message si la liste est vide
                messageLabel.setVisible(true);
            } else {
                depotTable.setItems(fullList);

                // Cacher le message si la liste contient des éléments
                messageLabel.setVisible(false);
            }

        } catch (Exception e) {
            System.err.println("Erreur critique: " + e.getMessage());
            e.printStackTrace();
            depotTable.setItems(FXCollections.emptyObservableList());
        }
    }

    @FXML
    private void handleRetourMenu() {
        if (mainApp != null) {
            mainApp.showAccueilView();
        }
    }

    @FXML
    private void handleAfficherPlus() {
        if (mainApp != null) {
            ObservableList<Depot> fullList = mainApp.getDepotData();

            // Créer une nouvelle fenêtre (Stage)
            Stage newStage = new Stage();
            newStage.setTitle("Historique Complet des Dépôts");

            // Créer un nouveau TableView et ses colonnes
            TableView<Depot> newDepotTable = new TableView<>();
            TableColumn<Depot, Integer> idColumn = new TableColumn<>("ID");
            TableColumn<Depot, LocalDate> dateColumn = new TableColumn<>("Date");
            TableColumn<Depot, LocalTime> heureColumn = new TableColumn<>("Heure");
            TableColumn<Depot, Number> pointsColumn = new TableColumn<>("Points");

            // Ajouter des colonnes au TableView
            newDepotTable.getColumns().addAll(idColumn, dateColumn, heureColumn, pointsColumn);

            // Remplir les colonnes avec les bonnes propriétés
            idColumn.setCellValueFactory(new PropertyValueFactory<>("identifiantDepot"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

            // Formatage de la date
            dateColumn.setCellFactory(column -> new TableCell<Depot, LocalDate>() {
                @Override
                protected void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setText(empty || date == null ? null : DateUtil.format(date));
                }
            });

            // Formatage des points
            pointsColumn.setCellFactory(column -> new TableCell<Depot, Number>() {
                @Override
                protected void updateItem(Number points, boolean empty) {
                    super.updateItem(points, empty);
                    setText(empty || points == null ? "" : String.format("%.0f pts", points.floatValue()));
                }
            });

            // Ajouter les données à ce nouveau TableView
            newDepotTable.setItems(fullList);

            // Créer un Layout (par exemple, un VBox) et y ajouter le TableView
            VBox vbox = new VBox();
            vbox.getChildren().add(newDepotTable);

            // Créer une nouvelle Scene avec le VBox et définir cette scène pour le nouveau Stage
            Scene scene = new Scene(vbox);
            newStage.setScene(scene);

            // Afficher la nouvelle fenêtre
            newStage.show();
        }
    }

}
