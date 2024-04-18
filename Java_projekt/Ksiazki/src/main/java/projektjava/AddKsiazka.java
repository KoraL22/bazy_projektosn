package projektjava;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddKsiazka {
    Tabela xd = new Tabela();

    @FXML
    private TextField AutorAdd;

    @FXML
    private TextField TytułAdd;

    @FXML
    private TextField ilość_stronAdd;

    @FXML
    private TextField redakcjaAdd;

    @FXML
    private TextField rok_wydaniaAdd;
    @FXML
    private TextField rodzajAdd;


    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ResultSet resultSet = null;
    ksiazka ksiazki = null;

    @FXML
    void Czyść(MouseEvent event) {
        TytułAdd.setText("");
        ilość_stronAdd.setText("");
        AutorAdd.setText("");
        redakcjaAdd.setText("");
        rok_wydaniaAdd.setText("");
        rodzajAdd.setText("");
    }


    @FXML
    void zapisz(MouseEvent event) throws Exception{
try {
    connection = bazapolaczenie.getCon();
    String Tytuł = TytułAdd.getText();
    String ilść_stron = ilość_stronAdd.getText();
    String Autor = AutorAdd.getText();
    String redakcja = redakcjaAdd.getText();
    String rok_wydania = rok_wydaniaAdd.getText();
    String rodzaj = rodzajAdd.getText();

    if (ilść_stron.isEmpty()) {
        throw new Exception();
    }

    int ilosc = Integer.parseInt(ilść_stron);

    if (Tytuł.isEmpty() || Autor.isEmpty() || redakcja.isEmpty() || rok_wydania.isEmpty()
            || rodzaj.isEmpty() || isLetterr(ilść_stron) == true || ilosc < 0
            || isLetterr(Autor) == false || isLetterr(redakcja) == false || isLetterr(rodzaj) == false || !isValidDate(rok_wydania)) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Proszę wypełnić poprawnie wszystkie dane");
        alert.showAndWait();
    } else {
        zapytanie();
        dodaj();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("dodano do bazy");
        alert.showAndWait();


    }
} catch (Exception e){
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setHeaderText(null);
    alert.setContentText("Proszę wypełnić poprawnie wszystkie dane");
    alert.showAndWait();
}
    }

    public static boolean isLetterr(String input) {
        // Sprawdzamy, czy każdy znak w wartości to litera lub spacja
        for (int i = 0; i < input.length(); i++) {
            char spacja = input.charAt(i);
            if (!Character.isLetter(spacja) && !Character.isWhitespace(spacja)){
                return false;
            }
        }
        return true;
    }
    public boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        try {
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Poprawny format: rrrr-mm-dd");
            alert.showAndWait();
            return false;
        }
    }

    private void zapytanie() {
        query = "INSERT INTO `ksiazki`(`Tytuł`, `ilość_stron`, `Autor`, `redakcja`, `rok_wydania`, `rodzaj`) VALUES (?,?,?,?,?,?)";
    }

    private void dodaj() {
        try {
            String ilść_stron = ilość_stronAdd.getText();
            int strony = Integer.parseInt(ilść_stron);
            if (strony < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("nie moze byc ujemna ilość stron");
                alert.showAndWait();
            }

            else {

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, TytułAdd.getText());
                preparedStatement.setInt(2, strony);
                preparedStatement.setString(3, AutorAdd.getText());
                preparedStatement.setString(4, redakcjaAdd.getText());
                preparedStatement.setString(5, rok_wydaniaAdd.getText());
                preparedStatement.setString(6, rodzajAdd.getText());
                preparedStatement.execute();

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddKsiazka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


