module com.example.ksiazki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens projektjava to javafx.fxml;
    exports projektjava;
}