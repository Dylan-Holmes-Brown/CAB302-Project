import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception{
        DriverManager.registerDriver(new org.sqlite.JDBC());
        CreateDatabase.build();
    }
}
