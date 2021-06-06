package test;

import common.CurrentTrades;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import test.mocks.MockCurrentTradeData;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TestCurrentTradeData {

    private List<CurrentTrades> tradeList;
    private MockCurrentTradeData data;
    private CurrentTrades trade;
    private CurrentTrades trade2;
    private CurrentTrades trade3;

    @BeforeEach @Test
    public void setupTradeList() {
        tradeList = new ArrayList<>();
        data = new MockCurrentTradeData();
        trade = new CurrentTrades("Buy", "org1", "CPU", 10, 40, new Date(2021, 06, 06));
        trade2 = new CurrentTrades("Sell", "org1", "CPU", 10, 40, new Date(2021, 06, 06));
        trade3 = new CurrentTrades("Buy", "org3", "CPU", 10, 40, new Date(2021, 06, 06));
    }

    /**
     * Test adding a Current Trade
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
        assertThrows(IllegalArgumentException.class, () -> {
            data.add(trade);
        });
    }

    /**
     * Test removing a trade
     */
    @Test
    public void testRemoveTrade() {
        data.add(trade);
        data.remove(trade);
        assertNull(data.get(trade));
    }

    /**
     * Test removing a trade that isn't in the list
     */
    @Test
    public void testRemoveNoUser() {
        assertThrows(IllegalArgumentException.class, () -> {
            data.remove(trade);
        });
    }

    /**
     * Test adding an trade and getting a different one
     */
    @Test
    public void testGetDifferentTrade() {
        data.add(trade);
        assertNull(data.get(trade2));
    }

    /**
     * Test adding two user's and getting them both back
     */
    @Test
    public void testGetMultipleTrades() {
        data.add(trade);
        data.add(trade2);
        assertEquals(trade, data.get(trade));
        assertEquals(trade2, data.get(trade2));
    }

    /**
     * Test getting a trade when none are in the list
     */
    @Test
    public void testGetNoTrade() {
        assertNull(data.get(trade));
    }

    /**
     * Test get all trades of type buy
     */
    @Test
    public void testGetTypeBuy() {
        data.add(trade);
        data.add(trade2);
        data.add(trade3);

        tradeList.add(trade);
        tradeList.add(trade3);
        assertEquals(tradeList, data.getType("Buy"));
    }

    /**
     * Test get all trades of type sell
     */
    @Test
    public void testGetTypeSell() {
        data.add(trade);
        data.add(trade2);
        data.add(trade3);

        tradeList.add(trade2);
        assertEquals(tradeList, data.getType("Sell"));
    }

    /**
     * Test get trades of type where none can be found
     */
    @Test
    public void testGetWrongType() {
        assertEquals(tradeList, data.getType("Buy"));
    }

    /**
     * Test get all trades of a given organisation
     */
    @Test
    public void testGetOrg() {
        data.add(trade);
        data.add(trade2);
        data.add(trade3);

        tradeList.add(trade);
        tradeList.add(trade2);
        assertEquals(tradeList, data.getOrg("org1"));
    }

    /**
     * Test get trades of organisation where none can be found
     */
    @Test
    public void testGetWrongOrg() {
        assertEquals(tradeList, data.getOrg("org1"));
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
    public void testGetCurrentTradeModel() {
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
