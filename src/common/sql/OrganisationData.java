package common.sql;

import common.Organisation;
import common.User;
import server.JDBCOrganisationDataSource;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * This class uses an OrganisationDataSource and its methods to retrieve data
 *
 * @author Dylan Holmes-Brown
 */
public class OrganisationData {
    DefaultListModel listModel;
    OrganisationDataSource orgData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public OrganisationData(OrganisationDataSource dataSource) {
        listModel = new DefaultListModel();
        orgData = dataSource;

        for (String name : orgData.OrgNameSet()){
            listModel.addElement(name);
        }
    }

    /**
     * Adds an Organisation to the organisational_unit table
     *
     * @param o An Organisation to add to the user table.
     */
    public void add(Organisation o) {

        // Check to see if the organisation has already been added
        if (!listModel.contains(o.getName())) {
            listModel.addElement(o.getName());
            orgData.addOrg(o);
        }
    }

    /**
     * Based on the name of the organisation in the organisation table,
     * delete the organisation.
     *
     * @param key the name key to delete
     */
    public void remove(Object key) {
        // remove from both list and map
        listModel.removeElement(key);
        orgData.deleteOrg((String) key);
    }

    /**
     * Add credits to an organisation
     *
     * @param key the organisation to search for
     * @param credits the credits amount to add
     */
    public void addCredits(Object key, int credits) {
        if (listModel.contains(key)) {
            orgData.addCredits((String) key, credits);
        }
    }

    /**
     * Remove credits from an organisation
     *
     * @param key the organisation to search for
     * @param credits the credits amount to remove
     */
    public void removeCredits(Object key, int credits) {
        if (listModel.contains(key)) {
            orgData.removeCredits((String) key, credits);
        }
    }

    /**
     * Add quantity to an organisation
     *
     * @param key the organisation to search for
     * @param asset the asset to add the quantity to
     * @param quantity the quantity amount to add
     */
    public void addQuantity(Object key, String asset, int quantity) {
        if (listModel.contains(key)) {
            orgData.addQuantity((String) key, asset, quantity);
        }
    }

    /**
     * Remove quantity from an organisation
     *
     * @param key the organisation to search for
     * @param asset the asset to remove the quantity from
     * @param quantity the quantity amount to remove
     */
    public void removeCredits(Object key, String asset, int quantity) {
        if (listModel.contains(key)) {
            orgData.removeQuantity((String) key, asset, quantity);
        }
    }

    /**
     * Saves the data in the organisation table using the persistence mechanism.
     */
    public void persist() { orgData.close(); }

    /**
     * Retrieves Organisation details from the model.
     *
     * @param key the name to retrieve.
     * @return the Organisation object related to the name.
     */
    public Organisation get(Object key) { return orgData.getOrg((String) key); }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() { return listModel; }

    /**
     * Get the number of organisations in the table
     *
     * @return the size of the organisation table.
     */
    public int getSize() { return orgData.getOrgSize(); }
}
