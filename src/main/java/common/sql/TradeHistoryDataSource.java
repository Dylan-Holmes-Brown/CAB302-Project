package common.sql;

import common.Trade;

import java.util.Set;

public interface TradeHistoryDataSource {
    /**
     * Adds a trade to the table, if it is not already in the list
     *
     * @param trades CurrentTrade to add
     */
    void addTradeHistory(Trade trades);

    /**
     * Gets the number of organisations in the table.
     *
     * @return size of organisation table.
     */
    int getHistorySize();

    /**
     * Finalizes any resources used by the data source and ensures data is
     * persisited.
     */
    void close();

    /**
     * Retrieves a set of id's from the data source that are used in
     * the id list.
     *
     * @return set of id's.
     */
    Set<Integer> historySet();

    /**
     * Retrieves a set of id's from the data source given an asset
     *
     * @param asset The asset to retrieve
     * @return set of id's.
     */
    Set<Integer> assetSet(String asset);
}
