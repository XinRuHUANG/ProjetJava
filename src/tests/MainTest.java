package tests;

import java.sql.*;

public class MainTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/ma_base";
            String user = "root";
            String password = "ton_mot_de_passe";
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();

            String sql = "SELECT nom, email FROM utilisateurs";
            ResultSet resultSet = statement.executeQuery(sql);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
