package projektjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tabela implements Initializable {
    public Tabela() {
    }

    @FXML
    private TableView<ksiazka> table;
    @FXML
    private TableColumn<ksiazka, Integer> ID;

    @FXML
    private TableColumn<ksiazka, String> Autor;

    @FXML
    private TableColumn<ksiazka, Date> rok_wydania;

    @FXML
    private TableColumn<ksiazka, String> ilość_stron;

    @FXML
    private TableColumn<ksiazka, String> redakcja;

    @FXML
    private TableColumn<ksiazka, String> rodzaj;


    @FXML
    private TableColumn<ksiazka, String> Tytuł;
    @FXML
    private TextField szukaj;
    int index = -1;
    ObservableList<ksiazka>  ksiazkiList;

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
        search_book();


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
    public static ObservableList<ksiazka> getDatabooks(){

        ObservableList<ksiazka> list = FXCollections.observableArrayList();

        try (Connection connection = bazapolaczenie.getCon();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM `ksiazki`");
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()){
                list.add(new ksiazka(Integer.parseInt(rs.getString("ID")),
                        rs.getString("Tytuł"), Integer.parseInt(rs.getString("ilość_stron")),
                        rs.getString("Autor"), rs.getString("redakcja"),
                        rs.getDate("rok_wydania"),rs.getString("rodzaj")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @FXML
    void search_book() {
        ID.setCellValueFactory(new PropertyValueFactory<ksiazka,Integer>("ID"));
        Tytuł.setCellValueFactory(new PropertyValueFactory<ksiazka, String>("Tytuł"));
        ilość_stron.setCellValueFactory(new PropertyValueFactory<ksiazka, String>("ilość_stron"));
        Autor.setCellValueFactory(new PropertyValueFactory<ksiazka, String>("Autor"));
        redakcja.setCellValueFactory(new PropertyValueFactory<ksiazka, String>("redakcja"));
        rodzaj.setCellValueFactory(new PropertyValueFactory<ksiazka, String>("rodzaj"));

        ksiazkiList = getDatabooks();

        connection = bazapolaczenie.getCon();

        table.setItems(ksiaskiList);
        FilteredList<ksiazka> filteredData = new FilteredList<>(ksiazkiList, b -> true);
        szukaj.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((ksiazka person) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(person.getID()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getTytuł().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (String.valueOf(person.getIlość_stron()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (person.getAutor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (person.getRedakcja().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;// Filter matches email
                } else if (person.getRodzaj().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;// Filter matches email
                else
                    return false; // Does not match.
            });
        });

        SortedList<ksiazka> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);



    }

    @FXML
    void Delete(MouseEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("usun.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Usuwanie");
            refresh();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Tabela.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void add(MouseEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("addView.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Dodaj książkę");
            stage.setScene(scene);
            stage.show();


        } catch (IOException ex) {
            Logger.getLogger(Tabela.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        search_book();

    }


    public void Insert(MouseEvent mouseEvent) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("edit.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Edycja");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Tabela.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}





