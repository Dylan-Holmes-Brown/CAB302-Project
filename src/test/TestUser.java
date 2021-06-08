package test;

import common.AssetType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.User;

public class TestUser {
    User user;
    User user2;
    @BeforeEach @Test
    public void setupUser()
    {
        user = new User("Adam", "1234", "Member", "Woolies");
        user2 = new User("Mitch", "4321", "Admin");
    }

    @Test
    public void testGetUsername()
    {
        assertEquals("Adam", user.getUsername());
        assertEquals("Mitch", user2.getUsername());
    }

    @Test
    public void testSetUsername()
    {
        user.setUsername("Dale");
        assertEquals("Dale", user.getUsername());
        user2.setUsername("Calvin");
        assertEquals("Calvin", user2.getUsername());
    }

    @Test
    public void testGetPassword()
    {
        assertEquals("1234", user.getPassword());
        assertEquals("4321", user2.getPassword());
    }

    @Test
    public void testSetPassword()
    {
        user.setPassword("abcd");
        assertEquals("abcd", user.getPassword());
        user2.setPassword("dcba");
        assertEquals("dcba", user2.getPassword());
    }

    @Test
    public void testGetAccountType()
    {
        assertEquals("Member", user.getAccountType());
        assertEquals("Admin", user2.getAccountType());
    }

    @Test
    public void testSetAccountType()
    {
        user.setAccountType("Test Member");
        assertEquals("Test Member", user.getAccountType());
        user2.setAccountType("Test Admin");
        assertEquals("Test Admin", user2.getAccountType());
    }

    @Test
    public void testGetOrganisation()
    {
        assertEquals("Woolies", user.getOrganisationalUnit());
    }

    @Test
    public void testSetOrganisation()
    {
        user.setOrganisationalUnit("Coles");
        assertEquals("Coles", user.getOrganisationalUnit());
    }
}
