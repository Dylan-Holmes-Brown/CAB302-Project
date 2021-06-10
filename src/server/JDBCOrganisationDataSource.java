package server;

import common.Organisation;
import common.sql.OrganisationDataSource;


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
                    + "credits INTEGER NOT NULL CHECK (credits >= 0),"
                    + "assets VARCHAR(20) NOT NULL,"
                    + "quantity INTEGER NOT NULL CHECK (quantity >= 0),"
                    + "CONSTRAINT FK_Asset FOREIGN KEY (assets) REFERENCES asset_types(assetType)"
                    + ");";

    private static final String INSERT_ORG = "INSERT INTO organisational_unit (name, credits, assets, quantity) VALUES (?, ?, ?, ?);";
    private PreparedStatement addOrg;

    private static final String ADD_CREDITS = "UPDATE organisational_unit SET credits = (credits + ?) WHERE name = ?";
    private PreparedStatement addCredits;

    private static final String REMOVE_CREDITS = "UPDATE organisational_unit SET credits = (credits - ?) WHERE name = ?";
    private PreparedStatement removeCredits;

    private static final String ADD_QUANTITY = "UPDATE organisational_unit SET quantity = (quantity + ?) WHERE name = ? AND assets = ?";
    private PreparedStatement addQuantity;

    private static final String REMOVE_QUANTITY = "UPDATE organisational_unit SET quantity = (quantity - ?) WHERE name = ? AND assets = ?";
    private PreparedStatement removeQuantity;

    private static final String GET_ORGNAME = "SELECT name FROM organisational_unit";
    private PreparedStatement getOrgNameList;

    private static final String GET_ORG = "SELECT * FROM organisational_unit WHERE name=?";
    private PreparedStatement getOrg;

    private static final String GET_ASSETS = "SELECT assets FROM organisational_unit WHERE name=?";
    private PreparedStatement getAssets;

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
            addCredits = connection.prepareStatement(ADD_CREDITS);
            removeCredits = connection.prepareStatement(REMOVE_CREDITS);
            addQuantity = connection.prepareStatement(ADD_QUANTITY);
            removeQuantity = connection.prepareStatement(REMOVE_QUANTITY);
            getOrgNameList = connection.prepareStatement(GET_ORGNAME);
            getOrg = connection.prepareStatement(GET_ORG);
            getAssets = connection.prepareStatement(GET_ASSETS);
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
            addOrg.setString(3, org.getAsset());
            addOrg.setInt(4, org.getQuantity());
            addOrg.execute();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#addCredits(String, int)
     */
    public void addCredits(String name, int credits) {
        try {
            addCredits.setInt(1, credits);
            addCredits.setString(2, name);
            addCredits.execute();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#removeCredits(String, int)
     */
    public void removeCredits(String name, int credits) {
        try {
            removeCredits.setInt(1, credits);
            removeCredits.setString(2, name);
            removeCredits.execute();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#addQuantity(String, String, int)
     */
    public void addQuantity(String name, String asset, int quantity) {
        try {
            addQuantity.setInt(1, quantity);
            addQuantity.setString(2, name);
            addQuantity.setString(3, asset);
            addQuantity.execute();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#removeQuantity(String, String, int)
     */
    public void removeQuantity(String name, String asset, int quantity) {
        try {
            removeQuantity.setInt(1, quantity);
            removeQuantity.setString(2, name);
            removeQuantity.setString(3, asset);
            removeQuantity.execute();
        }
        catch (SQLException sqle) {
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
     * @see OrganisationDataSource#orgAssetSet(String)
     */
    public Set<String> orgAssetSet(String organisation) {
        Set<String> names = new TreeSet<String>();
        ResultSet resultSet = null;

        try {
            getAssets.setString(1, organisation);
            resultSet = getOrgNameList.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("assets"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return names;
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
     * @see OrganisationDataSource#getOrgSize()
     */
    public int getOrgSize() {
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
     * @see OrganisationDataSource#OrgNameSet()
     */
    public Set<String> OrgNameSet() {
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
