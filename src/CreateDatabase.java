import java.sql.*;

/**
 * This class initilises the database
 *
 * @author Dylan Holmes-Brown
 */

public class CreateDatabase {

    public CreateDatabase() {}

    /**
     * Connect to the Database
     */
    public static void build() throws Exception{

        Connection conn = null;
        String url = "jdbc:sqlite:database.sqlite";
        conn = DriverManager.getConnection(url);

        System.out.println("Connection Established");
    }
}