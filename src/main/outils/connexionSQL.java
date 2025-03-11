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

    public static List<HashMap<String, String>> requeteAvecAffichage(String requete, ArrayList<String> attributs){
        List<HashMap<String, String>> infos = new ArrayList<>();
        try {
            // Etape 1 : Etablir la connexion
            Connection connection = DriverManager.getConnection(url, user, password);

            // Etape 2 : Créer un Statement
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(requete);

            int k = 0;
            HashMap<String, String> HashmapPivot = new HashMap<>();
            while (resultSet.next()) {
                String attribut = attributs.get(k);
                HashmapPivot.put(attribut,resultSet.getString(attribut));
                k ++;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infos;
    }

}
