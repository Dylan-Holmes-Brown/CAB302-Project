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
