package common;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing all the functionality of the organisation object
 *
 * @author Adam Buchsbaum
 */

public class TestOrganisation {
    Organisation organisation;
    Organisation organisation2;
    Organisation organisation3;

    /**
     * Before each test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupOrganisation()
    {
        organisation = new Organisation(1,"Microsoft", 560, "CPU", 100);
        organisation2 = new Organisation("Apple", 100, "Phone", 50);
        organisation3 = new Organisation();
    }

    /**
     * Test getting an organisation's id
     */
    @Test
    public void testGetID() {assertEquals(1, organisation.getID());}

    /**
     * Test setting an organisation's id
     */
    @Test
    public void testSetID()
    {
        organisation.setID(2);
        assertEquals(2, organisation.getID());
    }

    /**
     * Test getting an organisation's name
     */
    @Test
    public void testGetName()
    {
        assertEquals("Microsoft", organisation.getName());
    }

    /**
     * Test setting an organisation's name
     */
    @Test
    public void testSetName()
    {
        organisation.setName("Valve");
        assertEquals("Valve", organisation.getName());
    }

    /**
     * Test getting an organisation's credits
     */
    @Test
    public void testGetCredits()
    {
        assertEquals(560, organisation.getCredits());
    }

    /**
     * Test setting an organisation's credits
     */
    @Test
    public void testSetCredits()
    {
        organisation.setCredits(1210);
        assertEquals(1210, organisation.getCredits());
    }

    /**
     * Test getting an organisation's asset
     */
    @Test
    public void testGetAsset()
    {
        assertEquals("CPU", organisation.getAsset());
    }

    /**
     * Test setting an organisation's asset
     */
    @Test
    public void testSetAsset()
    {
        organisation.setAssets("GPU");
        assertEquals("GPU", organisation.getAsset());
    }

    /**
     * Test getting an organisation's quantity
     */
    @Test
    public void testGetQuantity()
    {
        assertEquals(100, organisation.getQuantity());
    }

    /**
     * Test setting an organisation's quantity
     */
    @Test
    public void testSetQuantity()
    {
        organisation.setQuantity(300);
        assertEquals(300, organisation.getQuantity());
    }
}
