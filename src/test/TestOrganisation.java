package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.Organisation;

public class TestOrganisation {
    Organisation organisation;
    Organisation organisation2;

    @BeforeEach @Test
    public void setupOrganisation()
    {
        organisation = new Organisation("Microsoft", 560, "CPU", 100);
        organisation2 = new Organisation("Linux", 420);
    }

    @Test
    public void testGetName()
    {
        assertEquals("Microsoft", organisation.getName());
        assertEquals("Linux", organisation2.getName());
    }

    @Test
    public void testSetName()
    {
        organisation.setName("Valve");
        assertEquals("Valve", organisation.getName());
        organisation2.setName("Riot");
        assertEquals("Riot", organisation2.getName());
    }

    @Test
    public void testGetCredits()
    {
        assertEquals(560, organisation.getCredits());
        assertEquals(420, organisation2.getCredits());
    }

    @Test
    public void testSetCredits()
    {
        organisation.setCredits(1210);
        assertEquals(1210, organisation.getCredits());
        organisation2.setCredits(690);
        assertEquals(690, organisation2.getCredits());
    }

    @Test
    public void testGetAsset()
    {
        assertEquals("CPU", organisation.getAsset());
    }

    @Test
    public void testSetAsset()
    {
        organisation.setAssets("GPU");
        assertEquals("GPU", organisation.getAsset());
    }

    @Test
    public void testGetQuantity()
    {
        assertEquals(100, organisation.getQuantity());
    }

    @Test
    public void testSetQuantity()
    {
        organisation.setQuantity(300);
        assertEquals(300, organisation.getQuantity());
    }
}
