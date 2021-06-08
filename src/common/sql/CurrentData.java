package common.sql;

import common.Trade;

import javax.swing.*;

/**
 * This class uses an CurrentDataSource and its methods to retrieve data
 *
 * @author Dylan Holmes-Brown
 */
public class CurrentData {
    DefaultListModel listModel;
    CurrentDataSource currentData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     */
    public CurrentData(CurrentDataSource dataSource) {
        listModel = new DefaultListModel();
        currentData = dataSource;

        for (Integer id : currentData.idSet()){
            listModel.addElement(id);
            System.out.println(id);
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
            currentData.addTrade(trade);
        }
    }

    /**
     * Based on the id of the current trade in the current_trades table, delete the trade.
     *
     * @param key the id key to delete
     */
    public void remove(Object key) {
        // remove from both list and map
        listModel.removeElement(key);
        currentData.deleteTrade((Integer) key);
    }

    /**
     * Saves the data in the current_traddes table using the persistence mechanism.
     */
    public void persist() { currentData.close(); }

    /**
     * Retrieves trade details from the model.
     *
     * @param key the type of trade to retrieve.
     * @return the CurrentTrades object related to the id.
     */
    public Trade getType(Object key) { return currentData.getBuySell((String) key); }

    /**
     * Retrieves trade details from the model.
     *
     * @param key the org to retrieve.
     * @return the CurrentTrades object related to the id.
     */
    public Trade getOrg(Object key) { return currentData.getOrgTrade((String) key); }

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
    public int getSize() { return currentData.getCurrentSize(); }
}
