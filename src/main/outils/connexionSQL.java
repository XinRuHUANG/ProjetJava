package main.outils;

import java.sql.*;
import java.util.*;

public class connexionSQL {
    //Constantes
    public static final String url = "jdbc:mysql://localhost:3306/ma_base";
    public static final String user = "root";
    public static final String password = "ton_mot_de_passe";

    public connexionSQL() {
    }
    public static void requete(String requete){

        try {
            // Etape 1 : Etablir la connexion
            Connection connection = DriverManager.getConnection(url, user, password);

            // Etape 2 : Créer un Statement
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(requete);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<HashMap<String, String>> requeteAvecAffichage(String requete, ArrayList<String> attributs) {
        List<HashMap<String, String>> infos = new ArrayList<>();

        try {
            // Étape 1 : Établir la connexion
            Connection connection = DriverManager.getConnection(url, user, password);

            // Étape 2 : Créer un Statement
            Statement statement = connection.createStatement();

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery(requete);

            // Parcourir les résultats
            while (resultSet.next()) {
                HashMap<String, String> ligne = new HashMap<>();

                for (String attribut : attributs) {
                    ligne.put(attribut, resultSet.getString(attribut)); // Récupération des valeurs
                }

                infos.add(ligne); // Ajouter la ligne complète à la liste
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return infos;
    }

}
