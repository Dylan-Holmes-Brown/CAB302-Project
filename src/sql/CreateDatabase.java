package sql;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
    public static void build() throws Exception {

        Connection conn = null;
        String url = "jdbc:sqlite:database.sqlite";
        conn = DriverManager.getConnection(url);

        System.out.println("Connection Established\n");
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

        List<Field> fieldList = Arrays.asList(name.getFields());

        // Loop through each field found in the class
        for (int i = 0; i < fieldList.size(); i ++) {
//            String a = null;
//            System.out.println(fieldList.get(i).getAnnotatedType() + "\n");
//            if (type== "java.lang.String") {
//                type.set(i, "text");
//            }

            // If the field has the correct annotation append the name and type of field to the sql query
            //if (correctAnnotation) {
                sql.append(fieldList.get(i).getName() + " "); //+ correctAnnotation.get().type());

                if (i < fieldList.size() - 1) {
                    sql.append(",\n");
                }
            //}
        }

        sql.append(")");
        return sql.toString();
    }
}