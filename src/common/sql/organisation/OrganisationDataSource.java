package common.sql.organisation;

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
    int getSize();

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
    Set<String> nameSet();
}
