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
        try {
            System.out.println("Début de updateTableData"); // Debug
            if (mainApp == null) {
                System.err.println("MainApp n'est pas initialisé");
                return;
            }

            ObservableList<Depot> fullList = mainApp.getDepotData();
            Utilisateur currentUser = mainApp.getCurrentUser();

            System.out.println("Nombre total de dépôts: " + (fullList != null ? fullList.size() : "null")); // Debug
            System.out.println("Utilisateur courant: " + (currentUser != null ? currentUser.toString() : "null"));

            if (fullList == null || currentUser == null) {
                System.err.println("Liste ou utilisateur null");
                depotTable.setItems(FXCollections.emptyObservableList());
                messageLabel.setText("Aucun dépôt disponible.");
                return;
            }

            // Filtrer les dépôts pour n'afficher que ceux de l'utilisateur actuel
            ObservableList<Depot> filteredList = FXCollections.observableArrayList();
            for (Depot depot : fullList) {
                if (depot.getPosseder() != null && depot.getPosseder().equals(currentUser)) {
                    filteredList.add(depot);
                }
            }

            // Si aucune donnée n'a été trouvée après filtrage, afficher un message.
            if (filteredList.isEmpty()) {
                depotTable.setItems(FXCollections.emptyObservableList());
                messageLabel.setText("Aucun dépôt trouvé pour cet utilisateur.");
            } else {
                // Trier les dépôts par date (et heure si besoin) dans l'ordre décroissant
                filteredList.sort((depot1, depot2) -> {
                    // Comparaison des dates, puis des heures (si les dates sont identiques)
                    int dateComparison = depot2.getDate().compareTo(depot1.getDate());
                    if (dateComparison == 0) {
                        return depot2.getHeure().compareTo(depot1.getHeure());
                    }
                    return dateComparison;
                });

                // Récupérer les 5 premiers dépôts (ou moins si moins de 5)
                int limit = Math.min(5, filteredList.size());
                ObservableList<Depot> limitedList = FXCollections.observableArrayList(
                        filteredList.subList(0, limit)
                );

                // Mettre à jour le tableau avec les 5 dépôts les plus récents
                depotTable.setItems(limitedList);
                messageLabel.setText("Dépôts trouvés : " + limitedList.size());
            }

        } catch (Exception e) {
            System.err.println("Erreur critique: " + e.getMessage());
            e.printStackTrace();
            depotTable.setItems(FXCollections.emptyObservableList());
            messageLabel.setText("Erreur lors de la récupération des dépôts.");
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
            ObservableList<Depot> fullList = mainApp.getDepotData();

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
