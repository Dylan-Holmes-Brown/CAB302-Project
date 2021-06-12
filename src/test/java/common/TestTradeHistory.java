package common;

import common.Trade;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import common.mocks.MockTradeHistoryData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TestTradeHistory {

    private List<Trade> tradeList;
    private MockTradeHistoryData data;
    private Trade trade;
    private Trade trade2;
    private Trade trade3;

    /**
     * Before each test.test initialise any trade history data for the tests
     */
    @BeforeEach @Test
    public void setupTradeList() {
        tradeList = new ArrayList<>();
        data = new MockTradeHistoryData();
        trade = new Trade("Buy", "org1", "CPU", 10, 40, new Date(2021, 06, 06));
        trade2 = new Trade("Sell", "org1", "CPU", 10, 40, new Date(2021, 06, 06));
        trade3 = new Trade("Buy", "org3", "Hard Drive", 10, 40, new Date(2021, 06, 06));
    }

    /**
     * Test adding a Trade History
     */
    @Test
    public void testAddTrade() {
        data.add(trade);
        assertEquals(trade, data.get(trade));
    }

    /**
     * Test adding two Trades
     */
    @Test
    public void testAddTwoTrades() {
        data.add(trade);
        data.add(trade2);
        assertEquals(trade, data.get(trade));
        assertEquals(trade2, data.get(trade2));
    }

    /**
     * Test adding two of the same users
     */
    @Test
    public void testAddDuplicateTrade() {
        data.add(trade);
        assertThrows(IllegalArgumentException.class, () -> data.add(trade));
    }

    /**
     * Test get all trades of a given asset
     */
    @Test
    public void testGetAsset() {
        data.add(trade);
        data.add(trade2);
        data.add(trade3);

        tradeList.add(trade);
        tradeList.add(trade2);
        assertEquals(tradeList, data.getAsset("CPU"));
    }

    /**
     * Test get trades of an asset where none can be found
     */
    @Test
    public void testGetWrongAsset() {
        assertEquals(tradeList, data.getAsset("asset"));
    }

    /**
     * Test getting the size of the trade list
     */
    @Test
    public void testGetSize() {
        data.add(trade);
        assertEquals(1, data.getSize());
    }

    /**
     * Test getting the size of the trade list when nothing has been added
     */
    @Test
    public void testGetZero() {
        assertEquals(0, data.getSize());
    }

    /**
     * Test getting the current trade model
     */
    @Test
    public void testGetTradeHistoryModel() {
        data.add(trade);
        tradeList.add(trade);
        assertEquals(tradeList, data.getModel());
    }

    /**
     * Test getting the current trade model with multiple user's in it
     */
    @Test
    public void testGetMultipleTradeModel() {
        data.add(trade);
        tradeList.add(trade);
        data.add(trade2);
        tradeList.add(trade2);
        assertEquals(tradeList, data.getModel());
    }

    /**
     * Test getting the trade model with nothing in the list
     */
    @Test
    public void testNoTradesModel() {
        assertEquals(tradeList, data.getModel());
    }
}
