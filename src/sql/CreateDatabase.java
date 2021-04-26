package sql;

import java.sql.*;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

/**
 * This class initilises the database
 *
 * @author Dylan Holmes-Brown
 */

public class CreateDatabase {

    public CreateDatabase() {}

    /**
     * Initialise a connection to the Database
     */
    public static void build(Class<?> name) throws Exception {

        // SQLite connection string
        String url = "jdbc:sqlite:database.sqlite";

        // SQLite query for creating a new table
        String sql = CreateTable(name);

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            System.out.println("Connection Established\n");

            // Create a new table
            stmt.execute(sql);
        }

        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * This function uses the fields from the class to generate a SQLite Create Table query
     *
     * @param name The name of the class to generate the table from
     * @return The sqlite query as a string
     */
    public static String CreateTable(Class name) {
        // Create Table if it does not already exist
        var sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name.getSimpleName().toUpperCase() + " (\n");

        // Create Array with fields of class name
        List<Field> fieldList = Arrays.asList(name.getFields());

        // Create an empty hash map
        HashMap<String, String> typeMap = new HashMap<>();

        // Fill Hashmap with key of java field types and index to SQLite notation
        typeMap.put("java.lang.String", "TEXT");
        typeMap.put("int", "INT");

        // Loop through each field found in the class
        for (int i = 0; i < fieldList.size(); i ++) {
            // Create SQL query string including field names and types
            sql.append(fieldList.get(i).getName() + " " + typeMap.get(fieldList.get(i).getType().getTypeName()));

            // Add Primary Key if field is labelled id
            if (fieldList.get(i).getName() == "id")
            {
                sql.append(" PRIMARY KEY");
            }

            // Goto new line of string if fields still remain
            if (i < fieldList.size() - 1) {
                sql.append(",\n");
            }
        }

        // Close off SQL query
        sql.append(")");
        return sql.toString();
    }
}