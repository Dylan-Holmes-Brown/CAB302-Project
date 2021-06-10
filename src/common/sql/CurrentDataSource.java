package common.sql;

import common.Organisation;
import common.Trade;

import java.util.Set;

/**
 * Provides functionality needed for a CurrentTrades object to be manipulated
 *
 * @author Dylan Holmes-Brown
 */
public interface CurrentDataSource {

    /**
     * Adds a trade to the table, if it is not already in the list
     *
     * @param trades CurrentTrade to add
     */
    void addTrade(Trade trades);

    /**
     * Extracts all the details of a Current Trade from the table based on the
     * id passed in.
     *
     * @param id The id as a Integer to search for.
     * @return all details in a Trade object for the given id
     */
    Trade getCurrentTrade(Integer id);

    /**
     * Deletes a CurrentTrade from the table.
     *
     * @param id The id of the trade to delete
     */
    void deleteTrade(int id);

    /**
     * Get all the current trades given the type
     *
     * @param type The type of trade
     * @return all details of CurrentTrades given the trades
     */
    Set<Integer> typeSet(String type);

    /**
     * Get all current trades given the organisation
     *
     * @param organisation the name of the organisation to search for
     * @return all details in CurrentTrades object
     */
    Set<Integer> orgSet(String organisation);

    /**
     * Gets the number of organisations in the table.
     *
     * @return size of organisation table.
     */
    int getCurrentSize();

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
    Set<Integer> idSet();
}
