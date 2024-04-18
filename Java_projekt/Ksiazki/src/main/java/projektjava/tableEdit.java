package projektjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class tableEdit  implements Initializable {

AddKsiazka xd = new AddKsiazka();
    @FXML
    private TableColumn<ksiazka, String> Autor;
    @FXML
    private TextField IDEdit;

    @FXML
    private TextField AutorEdit;

    @FXML
    private TableColumn<ksiazka, Integer> ID;

    @FXML
    private TextField Ilosc_StronEdit;

    @FXML
    private TextField RedakcjaEdit;

    @FXML
    private TextField RodzajEdit;

    @FXML
    private TextField RokEdit;

    @FXML
    private TextField TytulEdit;

    @FXML
    private TableColumn<ksiazka, String> Tytuł;

    @FXML
    private TableColumn<ksiazka, Integer> ilość_stron;

    @FXML
    private TableColumn<ksiazka, String> redakcja;

    @FXML
    private TableColumn<ksiazka, String> rodzaj;

    @FXML
    private TableColumn<ksiazka, Date> rok_wydania;

    @FXML
    private TableView<ksiazka> table;


    int index = -1;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ResultSet resultSet = null;
    ksiazka ksiazki = null;
    ObservableList<ksiazka> ksiaskiList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pobierzDate();
        refresh();



    }
    private void pobierzDate() {

        connection = bazapolaczenie.getCon();
        refresh();

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Tytuł.setCellValueFactory(new PropertyValueFactory<>("Tytuł"));
        ilość_stron.setCellValueFactory(new PropertyValueFactory<>("ilość_stron"));
        Autor.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        redakcja.setCellValueFactory(new PropertyValueFactory<>("redakcja"));
        rok_wydania.setCellValueFactory(new PropertyValueFactory<>("rok_wydania"));
        rodzaj.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));


    }
    @FXML
    void selectTextFields(javafx.scene.input.MouseEvent mouseEvent) {
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        IDEdit.setText(ID.getCellData(index).toString());
        TytulEdit.setText(Tytuł.getCellData(index).toString());
        Ilosc_StronEdit.setText(ilość_stron.getCellData(index).toString());
        AutorEdit.setText(Autor.getCellData(index).toString());
        RedakcjaEdit.setText(redakcja.getCellData(index).toString());
        RokEdit.setText(rok_wydania.getCellData(index).toString());
        RodzajEdit.setText(rodzaj.getCellData(index).toString());
    }
    @FXML
    public void refresh() {

        try {
            ksiaskiList.clear();

            query = "SELECT * FROM `ksiazki`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ksiaskiList.add(new ksiazka(
                        resultSet.getInt("ID"),
                        resultSet.getString("Tytuł"),
                        resultSet.getInt("ilość_stron"),
                        resultSet.getString("Autor"),
                        resultSet.getString("redakcja"),
                        resultSet.getDate("rok_wydania"),
                        resultSet.getString("rodzaj")));
                table.setItems(ksiaskiList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(bazapolaczenie.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void editUser() throws Exception {
        try {

            connection = bazapolaczenie.getCon();

            String value1 = IDEdit.getText();
            String value2 = TytulEdit.getText();
            String value3 = Ilosc_StronEdit.getText();
            String value4 = AutorEdit.getText();
            String value5 = RedakcjaEdit.getText();
            String value6 = RokEdit.getText();
            String value7 = RodzajEdit.getText();



            int ilosc_stron = Integer.parseInt(value3);

            if (value2.isEmpty() ||  value3.isEmpty() || value4.isEmpty()  || value5.isEmpty()
                    || value6.isEmpty()  || value7.isEmpty()  ||
        isLetter(value2) == false || isLetter(value4) == false || isLetter(value5) == false || isLetter(value7) == false
                    || ilosc_stron < 0 || !xd.isValidDate(value6) ){
                throw new Exception();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("zedytowano");
            alert.showAndWait();


            String sql = "UPDATE ksiazki SET Tytuł='" + value2 + "', ilość_stron=" + value3 +
                    ", Autor='" + value4 + "', redakcja='" + value5 + "', rok_wydania='" + value6 + "', rodzaj='" +
                    value7 + "' WHERE ID=" + value1;
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();


            refresh();
            IDEdit.setText("");
            TytulEdit.setText("");
            Ilosc_StronEdit.setText("");
            AutorEdit.setText("");
            RedakcjaEdit.setText("");
            RokEdit.setText("");
            RodzajEdit.setText("");


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("prosze poprawnie wypełnić dane");
            alert.showAndWait();
        }
        connection.close();
    }
    public static boolean isLetter(String input) {
        // Sprawdzamy, czy każdy znak w wartości to litera
        for (int i = 0; i < input.length(); i++) {
            char spacja = input.charAt(i);
            if (!Character.isLetter(spacja) && !Character.isWhitespace(spacja)) {
                return false;
            }
        }
        return true;
    }

}
