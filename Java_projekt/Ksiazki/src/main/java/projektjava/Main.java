package projektjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Biblioteka baza");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        Connection Con = bazapolaczenie.getCon();
        if ( Con == null){
            System.out.println("brak połączenia z bazą");
        }
        else {
            System.out.println("Połączono z baza");
        }
        launch();
    }
}
