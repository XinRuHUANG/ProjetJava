package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Contrat {
    int id;
    String dateDebut;
    String dateFin;

    public Contrat(int id, String dateDebut, String dateFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Contrat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public static void renouvelerContrat(Connection connection, int id, int idCommerce, int idCentreTri, String dateDebut, String dateFin){
        String query = "INSERT INTO contrat (idCommerce, idCentreTri, dateDebut, dateFin VALUES (?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            statement.setInt(2,idCommerce);
            statement.setInt(3,idCentreTri);
            statement.setString(4,dateDebut);
            statement.setString(5,dateFin);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void lireRègles(){

    }
}
