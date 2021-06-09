package test.mocks;

import common.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock class to mock the functionality of TradeHistoryData to use without a database
 *
 * @author Dylan Holmes-Brown
 */
public class MockTradeHistoryData {
    private List<Trade> tradeList;
    private List<Trade> getList;

    /**
     * Constructor initialises the trade history list
     */
    public MockTradeHistoryData() {
        tradeList = new ArrayList<>();
        getList = new ArrayList<>();
    }

    /**
     * Adds a trade to the list
     *
     * @param trade A trade history to add
     */
    public void add(Trade trade) {
        // Check to see if the trade has already been added
        if (!tradeList.contains(trade)) {
            tradeList.add(trade);
        }
        // List already contains the trade history
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Retrieves trade history details from the list
     *
     * @param key the trade to retrieve
     * @return the Trade object related to the name.
     */
    public Trade get(Object key) {
        // Loop through the entire list
        for (int i = 0; i< tradeList.size(); i++) {
            // Check if the key is in the list
            if (tradeList.get(i).equals(key)) {
                return tradeList.get(i);
            }
        }
        // key couldn't be found in the list
        return null;
    }

    /**
     * Retrieves all trades with a given asset and store in a separate list
     *
     * @param asset the asset of the trade to retrieve
     * @return a list of all trades with a given asset
     */
    public List getAsset(String asset) {
        for (int i = 0; i < tradeList.size(); i++) {
            if (tradeList.get(i).getAsset() == asset) {
                getList.add(tradeList.get(i));
            }
        }
        return getList;
    }

    /**
     * Get the number of trades in the list.
     *
     * @return the number of trades in the trade list
     */
    public int getSize() {
        return tradeList.size();
    }

    /**
     * Accessor for the list
     *
     * @return the assetList
     */
    public List getModel() { return tradeList; }
}
