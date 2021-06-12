package common;

import common.User;
import org.junit.jupiter.api.*;
import common.mocks.MockUserData;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for testing all the functionality of the user data
 *
 * @author Dylan Holmes-Brown
 */
public class TestUserData {

    private List<User> userList;
    private MockUserData data;
    private User user;
    private User user2;

    /**
     * Before each test.test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupUserList() {
        userList = new ArrayList<>();
        data = new MockUserData();
        user = new User("user1", "1234", "Member", "org1");
        user2 = new User("user2", "1234", "Admin", "org1");
    }

    /**
     * Test adding a User
     */
    @Test
    public void testAddUser() {
        data.add(user);
        assertEquals(user, data.get(user));
    }

    /**
     * Test adding two of the same users
     */
    @Test
    public void testAddDuplicateUser() {
        data.add(user);
        assertThrows(IllegalArgumentException.class, () -> data.add(user));
    }

    /**
     * Test update password of user
     */
    @Test
    public void testUpdatePassword() {
        data.add(user);
        data.update(user, "4321");
        assertEquals("4321", data.get(user).getPassword());
    }

    /**
     * Test update password of user where user doesn't exist
     */
    @Test
    public void testUpdateWrongUser() {
        data.add(user2);
        assertThrows(IllegalArgumentException.class, () -> data.update(user, "4321"));
    }

    /**
     * Test adding an user and getting a different one
     */
    @Test
    public void testGetDifferentUser() {
        data.add(user);
        assertNull(data.get(user2));
    }

    /**
     * Test adding two user's and getting them both back
     */
    @Test
    public void testGetMultipleUsers() {
        data.add(user);
        data.add(user2);
        assertEquals(user, data.get(user));
        assertEquals(user2, data.get(user2));
    }

    /**
     * Test getting an user when none are in the list
     */
    @Test
    public void testGetNoUser() {
        assertNull(data.get(user));
    }

    /**
     * Test removing a user
     */
    @Test
    public void testRemoveUser() {
        data.add(user);
        data.remove(user);
        assertNull(data.get(user));
    }

    /**
     * Test removing a user that isn't in the list
     */
    @Test
    public void testRemoveNoUser() {
        assertThrows(IllegalArgumentException.class, () -> data.remove(user));
    }

    /**
     * Test getting the size of the list
     */
    @Test
    public void testGetSize() {
        data.add(user);
        assertEquals(1, data.getSize());
    }

    /**
     * Test getting the size of the user list when nothing has been added
     */
    @Test
    public void testGetZero() {
        assertEquals(0, data.getSize());
    }

    /**
     * Test getting the user model
     */
    @Test
    public void testGetUserModel() {
        data.add(user);
        userList.add(user);
        assertEquals(userList, data.getModel());
    }

    /**
     * Test getting the user model with multiple user's in it
     */
    @Test
    public void testGetMultipleUserModel() {
        data.add(user);
        userList.add(user);
        data.add(user2);
        userList.add(user2);
        assertEquals(userList, data.getModel());
    }

    /**
     * Test getting the user model with nothing in the list
     */
    @Test
    public void testNoUsersModel() {
        assertEquals(userList, data.getModel());
    }
}
