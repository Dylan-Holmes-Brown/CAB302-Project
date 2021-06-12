package common;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.User;

/**
 * Test class for testing all the functionality of the trade object
 *
 * @author Adam Buchsbaum
 */

public class TestUser {
    User user;
    User user2;

    /**
     * Before each test.test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupUser()
    {
        user = new User("Adam", "1234", "Member", "Woolies");
        user2 = new User("Mitch", "4321", "Admin");
    }

    /**
     * Test getting an user's username
     */
    @Test
    public void testGetUsername()
    {
        assertEquals("Adam", user.getUsername());
        assertEquals("Mitch", user2.getUsername());
    }

    /**
     * Test setting an user's username
     */
    @Test
    public void testSetUsername()
    {
        user.setUsername("Dale");
        assertEquals("Dale", user.getUsername());
        user2.setUsername("Calvin");
        assertEquals("Calvin", user2.getUsername());
    }

    /**
     * Test getting an user's password
     */
    @Test
    public void testGetPassword()
    {
        assertEquals("1234", user.getPassword());
        assertEquals("4321", user2.getPassword());
    }

    /**
     * Test setting an user's password
     */
    @Test
    public void testSetPassword()
    {
        user.setPassword("abcd");
        assertEquals("abcd", user.getPassword());
        user2.setPassword("dcba");
        assertEquals("dcba", user2.getPassword());
    }

    /**
     * Test getting an user's account type
     */
    @Test
    public void testGetAccountType()
    {
        assertEquals("Member", user.getAccountType());
        assertEquals("Admin", user2.getAccountType());
    }

    /**
     * Test setting an user's account type
     */
    @Test
    public void testSetAccountType()
    {
        user.setAccountType("Test Member");
        assertEquals("Test Member", user.getAccountType());
        user2.setAccountType("Test Admin");
        assertEquals("Test Admin", user2.getAccountType());
    }

    /**
     * Test getting an user's organisation
     */
    @Test
    public void testGetOrganisation()
    {
        assertEquals("Woolies", user.getOrganisationalUnit());
    }

    /**
     * Test setting an user's organisation
     */
    @Test
    public void testSetOrganisation()
    {
        user.setOrganisationalUnit("Coles");
        assertEquals("Coles", user.getOrganisationalUnit());
    }
}
