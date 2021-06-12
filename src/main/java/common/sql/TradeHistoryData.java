package common.sql;

import common.Trade;

import javax.swing.*;

/**
 * This class uses an TradeHistoryDataSource and its methods to retrieve data
 *
 * @author Adam Buchsbaum
 */
public class TradeHistoryData {
    DefaultListModel listModel;
    TradeHistoryDataSource historyData;
    DefaultListModel assetModel;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     * @param dataSource The data source used to access the trade history database
     */
    public TradeHistoryData(TradeHistoryDataSource dataSource) {
        listModel = new DefaultListModel();
        historyData = dataSource;

        for (Integer id : historyData.historySet()){
            listModel.addElement(id);
        }
    }

    /**
     * Adds a trade to the table
     *
     * @param trade A trade to add to the current_trades table.
     */
    public void add(Trade trade) {

        // Check to see if the user has already been added
        if (!listModel.contains(trade)) {
            listModel.addElement(trade.getID());
            historyData.addTradeHistory(trade);
        }
    }

    /**
     * Saves the data in the current_traddes table using the persistence mechanism.
     */
    public void persist() { historyData.close(); }

    /**
     * Retrieves trade details from the model.
     *
     * @param asset the asset of trade to retrieve.
     * @return the CurrentTrades object related to the id.
     */
    public ListModel getAsset(String asset)
    {
        assetModel = new DefaultListModel();
        for (Integer id : historyData.assetSet(asset)){
            assetModel.addElement(id);
        }
        return assetModel;
    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel of usernames.
     */
    public ListModel getModel() { return listModel; }

    /**
     * Get the number of trades in the table
     *
     * @return the size of the current_trades table.
     */
    public int getSize() { return historyData.getHistorySize(); }
}
