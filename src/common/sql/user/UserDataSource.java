package common.sql.user;

import common.User;

import java.util.Set;

public interface UserDataSource {
    /**
     * Adds a User to the table, if they are not already in the table
     *
     * @param user User to add
     */
    void addUser(User user);

    /**
     * Extracts all the details of a User from the table based on the
     * name passed in.
     *
     * @param username The name as a String to search for.
     * @return all details in a User object for the name
     */
    User getUser(String username);

    /**
     * Gets the number of users in the table.
     *
     * @return size of user.
     */
    int getSize();

    /**
     * Deletes a User from the table.
     *
     * @param username The name to delete from the table.
     */
    void deleteUser(String username);

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
