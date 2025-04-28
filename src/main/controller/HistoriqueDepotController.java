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
import main.backend.fonctions.Utilisateur;

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
        System.out.println("Début de updateTableData");

        try {
            if (mainApp == null || mainApp.getCurrentUser() == null) {
                System.err.println("MainApp ou utilisateur non initialisé");
                depotTable.setItems(FXCollections.emptyObservableList());
                messageLabel.setText("Veuillez vous connecter");
                return;
            }

            System.out.println("Récupération des dépôts pour user ID: " + mainApp.getCurrentUser().getIdentifiantUtilisateur());
            ObservableList<Depot> userDepots = mainApp.getDepotsByUser(mainApp.getCurrentUser());
            System.out.println("Nombre de dépôts trouvés: " + userDepots.size());

            if (userDepots.isEmpty()) {
                System.out.println("Aucun dépôt trouvé pour cet utilisateur");
                depotTable.setItems(FXCollections.emptyObservableList());
                messageLabel.setText("Aucun dépôt trouvé");
                return;
            }

            depotTable.setItems(userDepots);
            messageLabel.setText("Dépôts trouvés: " + userDepots.size());

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des dépôts:");
            e.printStackTrace();
            depotTable.setItems(FXCollections.emptyObservableList());
            messageLabel.setText("Erreur de chargement");
        }
    }

    @FXML
    private void handleRetourMenu() {
        Stage currentStage = (Stage) retourMenuButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void handleAfficherPlus() {
        if (mainApp != null) {
            ObservableList<Depot> fullList = mainApp.getDepotsByUser(mainApp.getCurrentUser());

            // Créer une nouvelle fenêtre pour afficher l'historique complet
            Stage newStage = new Stage();
            newStage.setTitle("Historique Complet des Dépôts");

            // Créer un nouveau TableView pour afficher l'historique complet
            TableView<Depot> newDepotTable = new TableView<>();
            TableColumn<Depot, Integer> idColumn = new TableColumn<>("ID");
            TableColumn<Depot, LocalDate> dateColumn = new TableColumn<>("Date");
            TableColumn<Depot, LocalTime> heureColumn = new TableColumn<>("Heure");
            TableColumn<Depot, Number> pointsColumn = new TableColumn<>("Points");

            newDepotTable.getColumns().addAll(idColumn, dateColumn, heureColumn, pointsColumn);

            // Remplir les colonnes avec les bonnes propriétés
            idColumn.setCellValueFactory(new PropertyValueFactory<>("identifiantDepot"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

            // Ajouter les données au nouveau TableView
            newDepotTable.setItems(fullList);

            // Créer un layout pour le nouveau TableView
            VBox vbox = new VBox();
            vbox.getChildren().add(newDepotTable);

            // Créer une nouvelle scène avec le VBox et définir cette scène pour le nouveau Stage
            Scene scene = new Scene(vbox);
            newStage.setScene(scene);

            // Afficher la nouvelle fenêtre
            newStage.show();
        }
    }
}
