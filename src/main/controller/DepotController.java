package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.backend.fonctions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static main.outils.connexionSQL.requete;

public class DepotController {

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private TextField poidsTextField;

    @FXML
    private TextField pointsTextField;

    @FXML
    private Button deposerButton;
    private Object mainApp;

    private Utilisateur utilisateur;

    @FXML
    private void initialize() {
        // Initialiser la ChoiceBox avec les types de déchets
        typeChoiceBox.getItems().addAll("Plastique", "Carton", "Verre","Metal");

        // Action sur le bouton
        deposerButton.setOnAction(event -> deposer());
    }
    private void deposer() {
        String type = typeChoiceBox.getValue();
        String poidsStr = poidsTextField.getText();

        if (type == null || poidsStr.isEmpty()) {
            pointsTextField.setText("Erreur");
            return;
        }

        if (utilisateur == null) {
            pointsTextField.setText("Utilisateur non initialisé");
            return; // Arrêter l'exécution si utilisateur est nul
        }

        try {
            double poids = Double.parseDouble(poidsStr);
            double points = calculerPoints(type, poids);
            pointsTextField.setText(String.format("%.2f", points));

            // Créer un déchet (supposons que la poubelle intelligente soit déjà définie ailleurs)
            PoubelleIntelligente poubelleIntelligente = new PoubelleIntelligente(); // Créez une instance valide
            Dechet dechet = Dechet.ajouterDechet(TypeDechetEnum.TypeDechet.valueOf(type.toLowerCase()), null, poubelleIntelligente);

            // Créer un dépôt avec l'utilisateur et les déchets associés
            List<Dechet> dechets = List.of(dechet); // Créer une liste de déchets
            Depot depot = Depot.creerDepot(utilisateur, dechets);

            // Modifier le dépôt pour inclure les points calculés
            depot.modifierDepot(Map.of("points", (float) points, "date", LocalDate.now(), "heure", LocalTime.now()));

            // Enregistrer le dépôt dans la base de données (via votre DAO, par exemple)
            DepotDAO.ajouterDepotBDD(depot);

            //On ajoute dans la table posséder
            requete("INSERT INTO posseder(identifiantUtilisateur,identifiantDepot) VALUES(" + utilisateur.getIdUtilisateur() + "," + depot.getIdentifiantDepot() + ");");


            // Actualiser les points de fidélité dans la base de données
            utilisateur.setPointsFidelite(utilisateur.getPointsFidelite() + (int) points);
            UtilisateurDAO.actualiserUtilisateurBDD(utilisateur, "pointsFidelite");

        } catch (NumberFormatException e) {
            pointsTextField.setText("Erreur");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private double calculerPoints(String type, double poids) {
        double facteur = switch (type.toLowerCase()) {
            case "plastique" -> 2.0;
            case "carton" -> 1.5;
            case "verre" -> 1.2;
            case "metal" -> 2.2;
            default -> 1.0;
        };
        return poids * facteur;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}