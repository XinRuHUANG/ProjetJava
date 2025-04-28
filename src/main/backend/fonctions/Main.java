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

import main.controller.HistoriqueDepotController;
import main.controller.RootLayoutController;
import main.view.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main extends Application {

    private Utilisateur currentUser;
    private final ObservableList<Depot> depotData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;
    private File currentFile = null;

    private static final String URL = "jdbc:mysql://localhost:3306/projetjava";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        launch(args);
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilisateur user) {
        this.currentUser = user;
    }

    @Override
    public void start(Stage primaryStage) {
        if (primaryStage == null) {
            System.err.println("Erreur : primaryStage n'a pas été initialisé correctement.");
            return;
        }

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TriPlus - Gestion des dépôts");

        initSampleData();
        showAccueilView();
    }

    public void showAccueilView() {
        try {
            AccueilView accueilView = new AccueilView();
            accueilView.setMainApp(this);
            accueilView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSampleData() {
        if(currentUser == null) {
            currentUser = new Utilisateur(1, "Admin", "User", 0);

            Depot depot1 = new Depot(1, LocalDate.now(), LocalTime.now(), 10);
            depot1.setPosseder(currentUser);

            Depot depot2 = new Depot(2, LocalDate.now().minusDays(1), LocalTime.now(), 5);
            depot2.setPosseder(currentUser);

            depotData.addAll(depot1, depot2);
            currentUser.setPosseder(List.of(depot1, depot2));
        }
    }

    public ObservableList<Depot> getDepotsByUser(Utilisateur user) {
        ObservableList<Depot> userDepots = FXCollections.observableArrayList();

        if (user == null) return userDepots;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = """
            SELECT d.identifiantDepot, d.date, d.heure, d.points 
            FROM Depot d
            JOIN posseder p ON d.identifiantDepot = p.identifiantDepot
            WHERE p.identifiantUtilisateur = ?
            ORDER BY d.date DESC, d.heure DESC
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getIdUtilisateur());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Depot depot = new Depot(
                        rs.getInt("identifiantDepot"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("heure").toLocalTime(),
                        rs.getFloat("points")
                );
                depot.setPosseder(user);
                userDepots.add(depot);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des dépôts:");
            e.printStackTrace();
        }

        return userDepots;
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

    public void showHistoriqueDepotView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/HistoriqueDepot.fxml"));
            Parent root = loader.load();

            HistoriqueDepotController controller = loader.getController();
            controller.setMainApp(this);

            Stage historiqueStage = new Stage();
            historiqueStage.setScene(new Scene(root));
            historiqueStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'interface");
        }
    }

    // ... (les autres méthodes restent inchangées)

    public ObservableList<Depot> getDepotData() {
        return depotData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}