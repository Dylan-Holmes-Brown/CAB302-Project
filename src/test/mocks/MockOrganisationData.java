package test.mocks;

import common.Organisation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mock class to mock the functionality of UserData to use without a database
 *
 * @author Dylan Holmes-Brown
 */
public class MockOrganisationData {
    private List<Organisation> orgList;

    /**
     * Constructor initialises the organisation list
     */
    public MockOrganisationData() {
        orgList = new ArrayList<>();
    }

    /**
     * Adds a organisation to the organisation list
     *
     * @param org A organisation to add
     */
    public void add (Organisation org) {
        // Check to see if the organisation has already been added
        if (!orgList.contains(org)) {
            orgList.add(org);
        }
        // List already contains the organisation
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Based on the organisation, delete the organisation from the list
     *
     * @param key The name key to delete.
     */
    public void remove (Object key) {
        // If the key is in the list remove the organisation
        if (orgList.contains(key)) {
            orgList.remove(key);
        }
        // Key not found, throw exception
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds credits to an organisation given the name.
     *
     * @param key the organisation to search for
     * @param credits the credits amount to add
     */
    public void addCredits(Object key, int credits) {
        // If the key is in the list add credits
        if (orgList.contains(key)) {
            int index = orgList.indexOf(key);
            int currentCredits = orgList.get(index).getCredits();
            orgList.get(index).setCredits(currentCredits + credits);
        }
        // Organisation could not be found
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Remove credits from an organisation given the name.
     *
     * @param key the organisation to search for
     * @param credits the credits amount to remove
     */
    public void removeCredits(Object key, int credits) {
        // If the key is in the list remove credits
        if (orgList.contains(key)) {
            int index = orgList.indexOf(key);
            int currentCredits = orgList.get(index).getCredits();
            orgList.get(index).setCredits(currentCredits - credits);
        }
        // Organisation could not be found
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds quantity to an organisation given the name.
     *
     * @param key the organisation to search for
     * @param quantity the quantity amount to add
     */
    public void addQuantity(Object key, int quantity) {
        // If the key is in the list add quantity
        if (orgList.contains(key)) {
            int index = orgList.indexOf(key);
            int currentQuantity = orgList.get(index).getQuantity();
            orgList.get(index).setQuantity(currentQuantity + quantity);
        }
        // Organisation could not be found
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removes quantity from a organisation given the name.
     *
     * @param key the organisation to search for
     * @param quantity the quantity amount to remove
     */
    public void removeQuantity (Object key, int quantity) {
        // If the key is in the list remove credits
        if (orgList.contains(key)) {
            int index = orgList.indexOf(key);
            int currentQuantity = orgList.get(index).getQuantity();
            orgList.get(index).setQuantity(currentQuantity - quantity);
        }
        // Organisation could not be found
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Retrieves User details from the list
     *
     * @param key the organisation to retrieve
     * @return the User object related to the name
     */
    public Organisation get(Object key) {
        // Loop through the entire list
        for (int i = 0; i< orgList.size(); i++) {
            // Check if the key is in the list
            if (orgList.get(i).equals(key)) {
                return orgList.get(i);
            }
        }
        // key couldn't be found in the list
        return null;
    }

    /**
     * Get the number of organisations in the list
     *
     * @return the size of the organisation list
     */
    public int getSize() {
        Set<String> uniqueList = new HashSet<>();
        for (int i = 0; i< orgList.size(); i++) {
            String name = orgList.get(i).getName();
            uniqueList.add(name);
        }
        return uniqueList.size();
    }

    /**
     * Accessor for the list
     *
     * @return the orglist
     */
    public List getModel() {
        return orgList;
    }
}