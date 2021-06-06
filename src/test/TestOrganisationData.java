package test;

import common.Organisation;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import test.mocks.MockOrganisationData;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for testing all the functionality of the user data
 *
 * @author Dylan Holmes-Brown
 */
public class TestOrganisationData {

    private List<Organisation> orgList;
    private MockOrganisationData data;
    private Organisation org;
    private Organisation org2;
    /**
     * Before each test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupUserList() {
        orgList = new ArrayList<>();
        data = new MockOrganisationData();
        org = new Organisation("org1", 100, "CPU", 10);
        org2 = new Organisation("org1", 100, "Hard Drive", 10);
    }

    /**
     * Test adding a Organisation
     */
    @Test
    public void testAddOrganisation() {
        data.add(org);
        assertEquals(org, data.get(org));
    }

    /**
     * Test adding two of the same organisations
     */
    @Test
    public void testAddDuplicateUser() {
        data.add(org);
        assertThrows(IllegalArgumentException.class, () -> {
            data.add(org);
        });
    }

    /**
     * Test removing a organisation
     */
    @Test
    public void testRemoveOrganisation() {
        data.add(org);
        data.remove(org);
        assertNull(data.get(org));
    }

    /**
     * Test removing a organisation that isn't in the list
     */
    @Test
    public void testRemoveNoOrganisation() {
        assertThrows(IllegalArgumentException.class, () -> {
            data.remove(org);
        });
    }

    /**
     * Test adding credits to an organisation
     */
    @Test
    public void testAddCredits() {
        data.add(org);
        data.addCredits(org, 10);
        assertEquals(110, data.get(org).getCredits());
    }

    /**
     * Test adding credits of organisations where organisations doesn't exist
     */
    @Test
    public void testAddCreditsWrongOrganisation() {
        assertThrows(IllegalArgumentException.class, () -> {
            data.addCredits(org, 20);
        });
    }

    /**
     * Test removing credits from an organisation
     */
    @Test
    public void testRemoveCredits() {
        data.add(org);
        data.removeCredits(org, 10);
        assertEquals(90, data.get(org).getCredits());
    }

    /**
     * Test removing credits of organisations where organisations doesn't exist
     */
    @Test
    public void testRemoveCreditsWrongOrganisation() {
        assertThrows(IllegalArgumentException.class, () -> {
            data.removeCredits(org, 20);
        });
    }

    /**
     * Test adding quantity to an organisation
     */
    @Test
    public void testAddQuantity() {
        data.add(org);
        data.addQuantity(org, 5);
        assertEquals(15, data.get(org).getQuantity());
    }

    /**
     * Test adding quantity of organisations where organisations doesn't exist
     */
    @Test
    public void testAddQuantityWrongOrganisation() {
        assertThrows(IllegalArgumentException.class, () -> {
            data.addQuantity(org, 20);
        });
    }

    /**
     * Test removing credits from an organisation
     */
    @Test
    public void testRemoveQuantity() {
        data.add(org);
        data.removeQuantity(org, 5);
        assertEquals(5, data.get(org).getQuantity());
    }

    /**
     * Test removing quantity of organisations where organisations doesn't exist
     */
    @Test
    public void testRemoveQuantityWrongOrganisation() {
        assertThrows(IllegalArgumentException.class, () -> {
            data.removeQuantity(org, 5);
        });
    }

    /**
     * Test adding an organisation and getting a different one
     */
    @Test
    public void testGetDifferentUser() {
        data.add(org);
        assertNull(data.get(org2));
    }

    /**
     * Test adding two organisations and getting them both back
     */
    @Test
    public void testGetMultipleUsers() {
        data.add(org);
        data.add(org2);
        assertEquals(org, data.get(org));
        assertEquals(org2, data.get(org2));
    }

    /**
     * Test getting an organisation when none are in the list
     */
    @Test
    public void testGetNoUser() {
        assertNull(data.get(org));
    }

    /**
     * Test getting the amount of unique organisations in the list
     */
    @Test
    public void testGetSize() {
        data.add(org);
        data.add(org2);
        assertEquals(1, data.getSize());
    }

    /**
     * Test getting the organisation model
     */
    @Test
    public void testGetUserModel() {
        data.add(org);
        orgList.add(org);
        assertEquals(orgList, data.getModel());
    }

    /**
     * Test getting the organisation model with multiple organisations in it
     */
    @Test
    public void testGetMultipleUserModel() {
        data.add(org);
        orgList.add(org);
        data.add(org2);
        orgList.add(org2);
        assertEquals(orgList, data.getModel());
    }

    /**
     * Test getting the organisation model with nothing in the list
     */
    @Test
    public void testNoUsersModel() {
        assertEquals(orgList, data.getModel());
    }
}
