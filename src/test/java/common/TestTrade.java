package common;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.Trade;
import java.sql.Date;

/**
 * Test class for testing all the functionality of the trade object
 *
 * @author Adam Buchsbaum
 */

public class TestTrade {
    Trade trade;
    Date date;

    /**
     * Before each test.test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupTrade()
    {
        date = new Date(System.currentTimeMillis());
        trade = new Trade("Buy", "Apple", "CPU", 4, 20, date);
        trade.setID(1);
    }

    /**
     * Test getting a trade's ID
     */
    @Test
    public void testGetID()
    {
        assertEquals(1, trade.getID());
    }

    /**
     * Test setting a trade's ID
     */
    @Test
    public void testSetID()
    {
        trade.setID(2);
        assertEquals(2, trade.getID());
    }

    /**
     * Test getting a trade's type
     */
    @Test
    public void testGetBuySell()
    {
        assertEquals("Buy", trade.getBuySell());
    }

    /**
     * Test setting a trade's type
     */
    @Test
    public void testSetBuySell()
    {
        trade.setBuySell("Sell");
        assertEquals("Sell", trade.getBuySell());
    }

    /**
     * Test getting a trade's organisation
     */
    @Test
    public void testGetOrganisation()
    {
        assertEquals("Apple", trade.getOrganisation());
    }

    /**
     * Test setting a trade's organisation
     */
    @Test
    public void testSetOrganisation()
    {
        trade.setOrganisation("Orange");
        assertEquals("Orange", trade.getOrganisation());
    }

    /**
     * Test getting a trade's asset
     */
    @Test
    public void testGetAsset()
    {
        assertEquals("CPU", trade.getAsset());
    }

    /**
     * Test setting a trade's asset
     */
    @Test
    public void testSetAsset()
    {
        trade.setAsset("GPU");
        assertEquals("GPU", trade.getAsset());
    }

    /**
     * Test getting a trade's quantity
     */
    @Test
    public void testGetQuantity()
    {
        assertEquals(4, trade.getQuantity());
    }

    /**
     * Test setting a trade's quantity
     */
    @Test
    public void testSetQuantity()
    {
        trade.setQuantity(50);
        assertEquals(50, trade.getQuantity());
    }

    /**
     * Test getting a trade's price
     */
    @Test
    public void testGetPrice()
    {
        assertEquals(20, trade.getPrice());
    }

    /**
     * Test setting a trade's price
     */
    @Test
    public void testSetPrice()
    {
        trade.setPrice(3);
        assertEquals(3, trade.getPrice());
    }

    /**
     * Test getting a trade's date
     */
    @Test
    public void testGetDate()
    {
        assertEquals(date, trade.getDate());
    }

    /**
     * Test setting a trade's date
     */
    @Test
    public void testSetDate()
    {
        Date date2 = Date.valueOf("2021-02-26");
        trade.setDate(date2);
        assertEquals(date2, trade.getDate());
    }
}
