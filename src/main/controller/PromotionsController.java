package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.backend.fonctions.Main;
import main.backend.fonctions.Promotion;
import main.backend.fonctions.Utilisateur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PromotionsController {

    @FXML
    private TableView<Promotion> promotionsTable;

    @FXML
    private TableColumn<Promotion, Integer> idColumn;

    @FXML
    private TableColumn<Promotion, Float> remiseColumn;

    @FXML
    private TableColumn<Promotion, Float> pointsColumn;

    private ObservableList<Promotion> promotionsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Lier les colonnes aux attributs de Promotion
        idColumn.setCellValueFactory(new PropertyValueFactory<>("identifiantPromotion"));
        remiseColumn.setCellValueFactory(new PropertyValueFactory<>("pourcentageRemise"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("pointsRequis"));

        loadPromotionsFromDatabase();
    }

    private void loadPromotionsFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", ""); // adapte les infos
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Promotion");

            while (rs.next()) {
                Promotion promo = new Promotion(
                        rs.getInt("identifiantPromotion"),
                        rs.getFloat("pourcentageRemise"),
                        rs.getFloat("pointsRequis")
                );
                promotionsList.add(promo);
            }

            promotionsTable.setItems(promotionsList);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(Main main) {
    }

    public void setUtilisateur(Utilisateur currentUser) {
    }
}
