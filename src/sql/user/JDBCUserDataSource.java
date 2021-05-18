package sql.user;

import common.User;
import sql.DBConnection;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */

public class JDBCUserDataSource implements UserDataSource {
    private Connection connection;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS user ("
                    + "id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "username VARCHAR(30) NOT NULL UNIQUE,"
                    + "password VARCHAR(30) NOT NULL,"
                    + "accountType VARCHAR(20) NOT NULL,"
                    + "organisationalUnit VARCHAR(10) NOT NULL,"
                    + "CONSTRAINT FK_Org FOREIGN KEY (organisationalUnit) REFERENCES organisational_unit(name)"
                    + ");";

    private static final String INSERT_USER = "INSERT INTO user (username, password, accountType, organisationalUnit) VALUES (?, ?, ?, ?);";
    private PreparedStatement addUser;

    private static final String GET_USERNAMES = "SELECT username FROM user";
    private PreparedStatement getUsernamesList;

    private static final String GET_USER = "SELECT * FROM user WHERE username=?";
    private PreparedStatement getUser;

    private static final String DELETE_USER = "DELETE FROM user WHERE username=?";
    private PreparedStatement deleteUser;

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM user";
    private PreparedStatement rowCount;

    public JDBCUserDataSource() {
        try {
            connection = DBConnection.getInstance();

            Statement stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE);

            // Initialise prepared statements for table
            addUser = connection.prepareStatement(INSERT_USER);
            getUsernamesList = connection.prepareStatement(GET_USERNAMES);
            getUser = connection.prepareStatement(GET_USER);
            deleteUser = connection.prepareStatement(DELETE_USER);
            rowCount = connection.prepareStatement(COUNT_ROWS);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#addUser(common.User)
     */
    public void addUser(User user) {
        try {
            addUser.setString(1, user.getUsername());
            addUser.setString(2, user.getPassword());
            addUser.setString(3, user.getAccountType());
            addUser.setString(4, user.getOrganisationalUnit());
            addUser.execute();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#getUser(String)
     */
    public User getUser(String username) {
        User user = new User();
        ResultSet resultSet = null;

        try {
            getUser.setString(1, username);
            resultSet = getUser.executeQuery();
            resultSet.next();
            user.setName(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAccountType(resultSet.getString("accountType"));
            user.setOrganisationalUnit(resultSet.getString("organisationalUnit"));

        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return user;
    }

    /**
     * @see UserDataSource#getSize()
     */
    public int getSize() {
        ResultSet resultSet = null;
        int rows = 0;

        try {
            resultSet = rowCount.executeQuery();
            resultSet.next();
            rows = resultSet.getInt(1);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return rows;
    }

    /**
     * @see UserDataSource#deleteUser(String)
     */
    public void deleteUser(String username) {
        try {
            deleteUser.setString(1, username);
            deleteUser.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#close()
     */
    public void close() {
        try {
            connection.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#nameSet()
     */
    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet resultSet = null;

        try {
            resultSet = getUsernamesList.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("username"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return names;
    }
}
