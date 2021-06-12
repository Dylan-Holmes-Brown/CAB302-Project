package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Creates a connection to the database and stores in a singleton instance
 *
 * @author Dylan Holmes-Brown
 */
public class DBConnection {

   //The singleton instance of the database connection.
   private static Connection instance = null;

   /**
    * Constructor initialises the connection.
    */
   private DBConnection() {
      Properties properties = new Properties();
      FileInputStream in = null;
      try {
         in = new FileInputStream("./db.props");
         properties.load(in);
         in.close();

         // specify the data source, username and password
         String url = properties.getProperty("jdbc.url");
         String username = properties.getProperty("jdbc.username");
         String password = properties.getProperty("jdbc.password");
         String schema = properties.getProperty("jdbc.schema");

         // get a connection
         instance = DriverManager.getConnection(url + "/" + schema, username, password);
      } catch (SQLException sqle) {
         System.err.println(sqle);
      } catch (FileNotFoundException fnfe) {
         System.err.println(fnfe);
      } catch (IOException ex) {
         ex.printStackTrace();
      }
   }

   /**
    * Provides global access to the singleton instance of the UrlSet.
    *
    * @return a handle to the singleton instance of the UrlSet.
    */
   public static Connection getInstance() {
      // Set instance to the database connection if it is null
      if (instance == null) {
         new DBConnection();
      }
      // If instance is not null return the current instance
      return instance;
   }
}
