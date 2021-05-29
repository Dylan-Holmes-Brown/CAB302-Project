package common.sql;

import common.Organisation;

import java.util.Set;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public interface OrganisationDataSource {
    /**
     * Adds a organisation to the table, if they are not already in the list
     *
     * @param org Organisation to add
     */
    void addOrg(Organisation org);

    /**
     * Adds credits to an organisation given the name.
     *
     * @param name The name as a String to search for
     * @param credits The quantity amount to add
     */
    void addCredits (String name, int credits);

    /**
     * Remove credits from a given organisation
     *
     * @param name The name as a String to search for
     * @param credits The quantity amount to remove
     */
    void removeCredits (String name, int credits);

    /**
     * Adds quantity to an organisation given the name.
     *
     * @param name The name as a String to search for
     * @param asset The name of the asset to search for
     * @param credits The quantity amount to add
     */
    void addQuantity (String name, String asset, int credits);

    /**
     * Remove credits from a given organisation
     *
     * @param name The name as a String to search for
     * @param asset The name of the asset to search for
     * @param credits The quantity amount to remove
     */
    void removeQuantity (String name, String asset, int credits);

    /**
     * Extracts all the details of a Organisation from the table based on the
     * name passed in.
     *
     * @param name The name as a String to search for.
     * @return all details in a Organisation object for the name
     */
    Organisation getOrg(String name);

    /**
     * Gets the number of organisations in the table.
     *
     * @return size of organisation table.
     */
    int getOrgSize();

    /**
     * Deletes a Organisation from the table.
     *
     * @param name The name to delete from the table.
     */
    void deleteOrg(String name);

    /**
     * Finalizes any resources used by the data source and ensures data is
     * persisited.
     */
    void close();

    /**
     * Retrieves a set of names from the data source that are used in
     * the name list.
     *
     * @return set of names.
     */
    Set<String> OrgNameSet();
}
