package common;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.Organisation;

/**
 * Test class for testing all the functionality of the organisation object
 *
 * @author Adam Buchsbaum
 */

public class TestOrganisation {
    Organisation organisation;
    Organisation organisation2;

    /**
     * Before each test.test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupOrganisation()
    {
        organisation = new Organisation("Microsoft", 560, "CPU", 100);
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
