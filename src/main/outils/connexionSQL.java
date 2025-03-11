package main.outils;

import java.sql.*;

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
}
