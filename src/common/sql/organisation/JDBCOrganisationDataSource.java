package common.sql.organisation;

import common.Organisation;
import server.DBConnection;


import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class JDBCOrganisationDataSource implements OrganisationDataSource {
    private Connection connection;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS organisational_unit ("
                    + "name VARCHAR(30) PRIMARY KEY NOT NULL,"
                    + "credits INTEGER NOT NULL,"
                    + "assets VARCHAR(20) NOT NULL,"
                    + "quantity int NOT NULL,"
                    + "CONSTRAINT FK_Asset FOREIGN KEY (assets) REFERENCES asset_types(assetType)"
                    + ");";

    private static final String INSERT_ORG = "INSERT INTO organisational_unit (name, credits, assets, quantity) VALUES (?, ?, ?, ?);";
    private PreparedStatement addOrg;

    private static final String GET_ORGNAME = "SELECT name FROM organisational_unit";
    private PreparedStatement getOrgNameList;

    private static final String GET_ORG = "SELECT * FROM organisational_unit WHERE name=?";
    private PreparedStatement getOrg;

    private static final String DELETE_ORG = "DELETE FROM organisational_unit WHERE name=?";
    private PreparedStatement deleteOrg;

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM organisational_unit";
    private PreparedStatement rowCount;

    public JDBCOrganisationDataSource() {
        try {
            connection = DBConnection.getInstance();

            Statement stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE);

            // Initialise prepared statements for table
            addOrg = connection.prepareStatement(INSERT_ORG);
            getOrgNameList = connection.prepareStatement(GET_ORGNAME);
            getOrg = connection.prepareStatement(GET_ORG);
            deleteOrg = connection.prepareStatement(DELETE_ORG);
            rowCount = connection.prepareStatement(COUNT_ROWS);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     * @see OrganisationDataSource#addOrg(common.Organisation)
     */
    public void addOrg(Organisation org) {
        try {
            addOrg.setString(1, org.getName());
            addOrg.setInt(2, org.getCredits());
            addOrg.setString(3, org.getAssets());
            addOrg.setInt(4, org.getQuantity());
            addOrg.execute();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }


    }

    /**
     * @see OrganisationDataSource#getOrg(String)
     */
    public Organisation getOrg(String name) {
        Organisation org = new Organisation();
        ResultSet resultSet = null;

        try {
            getOrg.setString(1, name);
            resultSet = getOrg.executeQuery();
            resultSet.next();
            org.setName(resultSet.getString("name"));
            org.setCredits(resultSet.getInt("credits"));
            org.setAssets(resultSet.getString("assets"));
            org.setQuantity(resultSet.getInt("quantity"));
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return org;
    }

    /**
     * @see OrganisationDataSource#getSize()
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
     * @see OrganisationDataSource#deleteOrg(String)
     */
    public void deleteOrg(String name) {
        try {
            deleteOrg.setString(1, name);
            deleteOrg.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#close()
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
     * @see OrganisationDataSource#nameSet()
     */
    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet resultSet = null;

        try {
            resultSet = getOrgNameList.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return names;
    }
}
