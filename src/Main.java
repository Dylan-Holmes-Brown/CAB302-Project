import sql.CreateDatabase;
import sql.Statement;

import java.sql.*;

/**
 * This class initilises the database
 *
 * @author Dylan Holmes-Brown
 */
public class Main {

    public static void main(String[] args) throws Exception{
        DriverManager.registerDriver(new org.sqlite.JDBC());
        //CreateDatabase.build(User.class);
        //System.out.println(CreateDatabase.CreateTable(User.class)); // Test code to see if sql query is correct

    }
}
