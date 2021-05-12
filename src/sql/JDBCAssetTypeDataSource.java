package sql;

import common.AssetTypes;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 *
 * @author Adam Buchsbaum
 */

public class JDBCAssetTypeDataSource implements AssetTypeDataSource{
    private Connection connection;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS asset_types ("
                    + "idx INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,"
                    + "assetType VARCHAR(40) NOT NULL UNIQUE"
                    + ");";

    private static final String INSERT_ASSET_TYPE = "INSERT INTO asset_types (assetType) VALUES (?);";
    private PreparedStatement addAssetType;

    private static final String GET_ALL_ASSET_TYPES = "SELECT assetType FROM asset_types";
    private PreparedStatement getAllAssetTypes;

    private static final String GET_ASSET_TYPE = "SELECT * FROM asset_types WHERE assetType=?";
    private PreparedStatement getAssetType;

    private static final String DELETE_ASSET_TYPE = "DELETE FROM asset_types WHERE assetType=?";
    private PreparedStatement deleteAssetType;

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM asset_types";
    private PreparedStatement rowCount;

    public JDBCAssetTypeDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE);

            // Initialise prepared statements for table
            addAssetType = connection.prepareStatement(INSERT_ASSET_TYPE);
            getAllAssetTypes = connection.prepareStatement(GET_ALL_ASSET_TYPES);
            getAssetType = connection.prepareStatement(GET_ASSET_TYPE);
            deleteAssetType = connection.prepareStatement(DELETE_ASSET_TYPE);
            rowCount = connection.prepareStatement(COUNT_ROWS);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see AssetTypeDataSource#addAssetType(common.AssetTypes)
     */
    public void addAssetType(AssetTypes asset) {
        try {
            addAssetType.setString(1, asset.getAssetType());
            addAssetType.execute();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see AssetTypeDataSource#getAsset(String)
     */
    public AssetTypes getAsset(String assetType) {
        AssetTypes assetTypes = new AssetTypes();
        ResultSet resultSet = null;

        try {
            getAssetType.setString(1, assetType);
            resultSet = getAssetType.executeQuery();
            resultSet.next();
            assetTypes.setName(resultSet.getString("assetType"));
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return assetTypes;
    }

    /**
     * @see AssetTypeDataSource#getSize()
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
     * @see AssetTypeDataSource#deleteAssetType(String)
     */
    public void deleteAssetType(String assetType) {
        try {
            deleteAssetType.setString(1, assetType);
            deleteAssetType.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see AssetTypeDataSource#close()
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
     * @see AssetTypeDataSource#nameSet()
     */
    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet resultSet = null;

        try {
            resultSet = getAllAssetTypes.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("assetType"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return names;
    }
}