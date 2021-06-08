package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.Trade;
import java.sql.Date;

public class TestTrade {
    Trade trade;
    Date date;

    @BeforeEach @Test
    public void setupTrade()
    {
        date = new Date(System.currentTimeMillis());
        trade = new Trade("Buy", "Apple", "CPU", 4, 20, date);
        trade.setID(1);
    }

    @Test
    public void testGetID()
    {
        assertEquals(1, trade.getID());
    }

    @Test
    public void testSetID()
    {
        trade.setID(2);
        assertEquals(2, trade.getID());
    }

    @Test
    public void testGetBuySell()
    {
        assertEquals("Buy", trade.getBuySell());
    }

    @Test
    public void testSetBuySell()
    {
        trade.setBuySell("Sell");
        assertEquals("Sell", trade.getBuySell());
    }

    @Test
    public void testGetOrganisation()
    {
        assertEquals("Apple", trade.getOrganisation());
    }

    @Test
    public void testSetOrganisation()
    {
        trade.setOrganisation("Orange");
        assertEquals("Orange", trade.getOrganisation());
    }

    @Test
    public void testGetAsset()
    {
        assertEquals("CPU", trade.getAsset());
    }

    @Test
    public void testSetAsset()
    {
        trade.setAsset("GPU");
        assertEquals("GPU", trade.getAsset());
    }

    @Test
    public void testGetQuantity()
    {
        assertEquals(4, trade.getQuantity());
    }

    @Test
    public void testSetQuantity()
    {
        trade.setQuantity(50);
        assertEquals(50, trade.getQuantity());
    }
    @Test
    public void testGetPrice()
    {
        assertEquals(20, trade.getPrice());
    }

    @Test
    public void testSetPrice()
    {
        trade.setPrice(3);
        assertEquals(3, trade.getPrice());
    }

    @Test
    public void testGetDate()
    {
        assertEquals(date, trade.getDate());
    }

    @Test
    public void testSetDate()
    {
        Date date2 = Date.valueOf("2021-02-26");
        trade.setDate(date2);
        assertEquals(date2, trade.getDate());
    }
}
