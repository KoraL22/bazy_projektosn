package projektjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class bazapolaczenie {

    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String NAZWABAZY = "biblioteka";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection ;
    public static Connection getCon() {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",HOST,PORT,NAZWABAZY),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(bazapolaczenie.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }

}
