package server;

import common.Trade;
import common.sql.TradeHistoryDataSource;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

public class JDBCTradeHistoryDataSource implements TradeHistoryDataSource {

    private Connection connection;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS tradeHistory ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "buySell VARCHAR(4) NOT NULL CHECK(buySell IN ('Buy', 'Sell')),"
                    + "organisation VARCHAR(10) NOT NULL,"
                    + "asset VARCHAR(20) NOT NULL,"
                    + "quantity INTEGER NOT NULL CHECK (quantity >= 0),"
                    + "price INTEGER NOT NULL CHECK (price >= 0),"
                    + "date DATE NOT NULL,"
                    + "FOREIGN KEY (organisation) REFERENCES organisational_unit(name),"
                    + "FOREIGN KEY (asset) REFERENCES asset_types(assetType)"
                    + ");";

    private static final String INSERT_TRADE = "INSERT INTO tradeHistory (buySell, organisation, asset, quantity, price, date) VALUES (?, ?, ?, ?, ?, ?);";
    private PreparedStatement addTrade;

    private static final String GET_ASSETS = "SELECT * FROM tradeHistory WHERE asset=?;";
    private PreparedStatement getAssets;

    private static final String GET_TRADE_ID = "SELECT id FROM tradeHistory";
    private PreparedStatement getTradeID;

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM user;";
    private PreparedStatement rowCount;

    /**
     * Constructor gets connection to database and prepares all sql statements
     */
    public JDBCTradeHistoryDataSource() {
        try {
            connection = DBConnection.getInstance();

            Statement stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE);

            // Initialise prepared statements for table
            addTrade = connection.prepareStatement(INSERT_TRADE);
            getAssets = connection.prepareStatement(GET_ASSETS);
            getTradeID = connection.prepareStatement(GET_TRADE_ID);
            rowCount = connection.prepareStatement(COUNT_ROWS);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see TradeHistoryDataSource#addTradeHistory(Trade)
     */
    public void addTradeHistory(Trade trades) {
        try {
            addTrade.setString(1, trades.getBuySell());
            addTrade.setString(2, trades.getOrganisation());
            addTrade.setString(3, trades. getAsset());
            addTrade.setInt(4, trades.getQuantity());
            addTrade.setInt(5, trades.getPrice());
            addTrade.setDate(6, trades.getDate());
            addTrade.execute();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see TradeHistoryDataSource#getHistorySize()
     */
    public int getHistorySize() {
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
     * @see TradeHistoryDataSource#close()
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
     * @see TradeHistoryDataSource#historySet()
     */
    public Set<Integer> historySet() {
        Set<Integer> name = new TreeSet<Integer>();
        ResultSet resultSet = null;

        try {
            resultSet = getTradeID.executeQuery();
            while (resultSet.next()) {
                name.add(resultSet.getInt("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return name;
    }

    /**
     * @see TradeHistoryDataSource#assetSet(String)
     */
    public Set<Integer> assetSet(String asset) {
        Set<Integer> id = new TreeSet<Integer>();
        ResultSet resultSet = null;

        try {
            getAssets.setString(1, asset);
            resultSet = getAssets.executeQuery();
            while (resultSet.next()) {
                id.add(resultSet.getInt("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }


}
