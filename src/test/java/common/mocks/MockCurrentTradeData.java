package common.mocks;

import common.Trade;


import java.util.ArrayList;
import java.util.List;

/**
 * Mock class to mock the functionality of CurrentTradeData to use without a database
 *
 * @author Dylan Holmes-Brown
 */
public class MockCurrentTradeData {
    private List<Trade> tradeList;
    private List<Trade> getList;

    /**
     * Constructor initialises the current trade list
     */
    public MockCurrentTradeData() {
        tradeList = new ArrayList<>();
        getList = new ArrayList<>();
    }

    /**
     * Adds a trade to the list
     *
     * @param trade A currentTrade to add
     */
    public void add(Trade trade) {
        // Check to see if the trade has already been added
        if (!tradeList.contains(trade)) {
            tradeList.add(trade);
        }
        // List already contains the current trade
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Based on the trade, delete the trade from the list
     *
     * @param key the key to delete
     */
    public void remove (Object key) {
        // If the key is in the list remove the current trade
        if (tradeList.contains(key)) {
            tradeList.remove(key);
        }
        // Key not found, throw exception
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Retrieves currentTrade details from the list
     *
     * @param key the trade to retrieve
     * @return the CurrentTrade object related to the name.
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
     * Retrieves currentTrade details from the list given the type
     *
     * @param type the type of trade to retrieve
     * @return a list of current trades with a given trade
     */
    public List getType(String type) {
        // Loop through the entire list
        for (int i = 0; i< tradeList.size(); i++) {
            // Check if the type is in the list index
            if (tradeList.get(i).getBuySell() == type) {
                getList.add(tradeList.get(i));
            }
        }
        return getList;
    }

    /**
     * Retrieves currentTrade details from the list given the organisation
     *
     * @param org the organisation of trade to retrieve
     * @return a list of current trades with a given trade
     */
    public List getOrg(String org){
        // Loop through the entire list
        for (int i = 0; i< tradeList.size(); i++) {
            // Check if the org is in the list index
            if (tradeList.get(i).getOrganisation() == org) {
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
