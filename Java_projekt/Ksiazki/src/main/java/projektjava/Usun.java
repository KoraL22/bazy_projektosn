package projektjava;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Usun {
    @FXML
    private TextField BookDelete;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ResultSet resultSet = null;
    ksiazka ksiazki = null;

    @FXML
    void Usuń(MouseEvent event) {
        try {

            String delete = BookDelete.getText();

            if(delete.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Proszę podać ID");
                alert.showAndWait();
            }



        query = "DELETE FROM `ksiazki` WHERE ID = ?";
        connection = bazapolaczenie.getCon();

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,delete);
        preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Usun.class.getName()).log(Level.SEVERE, null, ex);
        }


    }




}

