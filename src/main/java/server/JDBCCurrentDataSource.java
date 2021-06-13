package server;

import common.Trade;
import common.sql.CurrentDataSource;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

public class JDBCCurrentDataSource implements CurrentDataSource {
    private Connection connection;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS currentTrades ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "buySell VARCHAR(4) NOT NULL CHECK(buySell IN ('Buy', 'Sell')),"
                    + "organisation VARCHAR(10) NOT NULL,"
                    + "asset VARCHAR(20) NOT NULL,"
                    + "quantity INTEGER NOT NULL CHECK (quantity >= 0),"
                    + "price INTEGER NOT NULL CHECK (price >= 0),"
                    + "date DATE NOT NULL,"
                    + "FOREIGN KEY (asset) REFERENCES asset_types(assetType)"
                    + ");";

    private static final String INSERT_TRADE = "INSERT INTO currentTrades (buySell, organisation, asset, quantity, price, date) VALUES (?, ?, ?, ?, ?, ?);";
    private PreparedStatement addTrade;

    private static final String GET_TRADE = "SELECT * FROM currentTrades WHERE id=?";
    private PreparedStatement getTrade;

    private static final String GET_BUYSELL = "SELECT * FROM currentTrades WHERE buySell=?;";
    private PreparedStatement getBuySell;

    private static final String GET_ORG_TRADES = "SELECT * FROM currentTrades WHERE organisation=?;";
    private PreparedStatement getOrgTrade;

    private static final String GET_TRADE_ID = "SELECT id FROM currentTrades";
    private PreparedStatement getTradeID;

    private static final String DELETE_TRADE = "DELETE FROM currentTrades WHERE id=?;";
    private PreparedStatement deleteTrade;

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM user;";
    private PreparedStatement rowCount;

    /**
     * Constructor gets connection to database and prepares all sql statements
     */
    public JDBCCurrentDataSource() {
        try {
            connection = DBConnection.getInstance();

            Statement stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE);

            // Initialise prepared statements for table
            addTrade = connection.prepareStatement(INSERT_TRADE);
            getTrade = connection.prepareStatement(GET_TRADE);
            getBuySell = connection.prepareStatement(GET_BUYSELL);
            getOrgTrade = connection.prepareStatement(GET_ORG_TRADES);
            getOrgTrade = connection.prepareStatement(GET_ORG_TRADES);
            getTradeID = connection.prepareStatement(GET_TRADE_ID);
            deleteTrade = connection.prepareStatement(DELETE_TRADE);
            rowCount = connection.prepareStatement(COUNT_ROWS);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see CurrentDataSource#addTrade(Trade)
     */
    public void addTrade(Trade trades) {
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
     * @see CurrentDataSource#getCurrentTrade(Integer)
     */
    public Trade getCurrentTrade(Integer id) {
        Trade trade = new Trade();
        ResultSet resultSet = null;

        try {
            getTrade.setInt(1, id);
            resultSet = getTrade.executeQuery();
            resultSet.next();
            trade.setID(resultSet.getInt("id"));
            trade.setBuySell(resultSet.getString("buySell"));
            trade.setOrganisation(resultSet.getString("organisation"));
            trade.setAsset(resultSet.getString("asset"));
            trade.setQuantity(resultSet.getInt("quantity"));
            trade.setPrice(resultSet.getInt("price"));
            trade.setDate(resultSet.getDate("date"));
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return trade;
    }

    /**
     * @see CurrentDataSource#typeSet(String)
     */
    public Set<Integer> typeSet(String type) {
        Set<Integer> id = new TreeSet<Integer>();
        ResultSet resultSet = null;
        try {
            getBuySell.setString(1, type);
            resultSet = getBuySell.executeQuery();
            while (resultSet.next()) {
                id.add(resultSet.getInt("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    /**
     * @see CurrentDataSource#orgSet(String)
     */
    public Set<Integer> orgSet(String org) {
        Set<Integer> id = new TreeSet<Integer>();
        ResultSet resultSet = null;

        try {
            getBuySell.setString(1, org);
            resultSet = getBuySell.executeQuery();
            while (resultSet.next()) {
                id.add(resultSet.getInt("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    /**
     * @see CurrentDataSource#deleteTrade(int)
     */
    public void deleteTrade(int id) {
        try {
            deleteTrade.setInt(1, id);
            deleteTrade.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * @see CurrentDataSource#getCurrentSize()
     */
    public int getCurrentSize() {
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
     * @see CurrentDataSource#close()
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
     * @see CurrentDataSource#idSet()
     */
    public Set<Integer> idSet() {
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
}
