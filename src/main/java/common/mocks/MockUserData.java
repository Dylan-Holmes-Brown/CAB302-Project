package common.mocks;

import common.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock class to mock the functionality of UserData to use without a database
 *
 * @author Dylan Holmes-Brown
 */
public class MockUserData {
    private List<User> userList;

    /**
     * Constructor initialises the user list
     */
    public MockUserData() {
        userList = new ArrayList<>();
    }

    /**
     * Adds a User to the user list
     *
     * @param user A User to add
     */
    public void add(User user) {
        // Check to see if the user has already been added
        if (!userList.contains(user)) {
            userList.add(user);
        }
        // List already contains the user
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Update the password of a user
     *
     * @param key the name key of a user
     * @param password the password to update
     */
    public void update(Object key, String password) {
        // If the key is in the list update the password
        if (userList.contains(key)) {
            int index = userList.indexOf(key);
            userList.get(index).setPassword(password);
        }
        // Username could not be found
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Based on the user, delete the user from the list
     *
     * @param key The name key to delete.
     */
    public void remove(Object key) {
        // If the key is in the list remove the user
        if (userList.contains(key)) {
            userList.remove(key);
        }
        // Key not found, throw exception
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Retrieves User details from the list
     *
     * @param key the user to retrieve
     * @return the User object related to the name.
     */
    public User get(Object key) {
        // Loop through the entire list
        for (int i = 0; i< userList.size(); i++) {
            // Check if the key is in the list
            if (userList.get(i).equals(key)) {
                return userList.get(i);
            }
        }
        // key couldn't be found in the list
        return null;
    }

    /**
     * Accessor for the list
     *
     * @return the assetList
     */
    public List getModel() { return userList; }

    /**
     * Get the number of users in the list.
     *
     * @return the number of users in the user list
     */
    public int getSize() { return userList.size(); }
}
