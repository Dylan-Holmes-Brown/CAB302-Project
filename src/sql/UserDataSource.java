package sql;

import common.User;

import java.util.Set;

public interface UserDataSource {
    /**
     * Adds a Person to the address list, if they are not already in the list
     *
     * @param user Person to add
     */
    void addUser(User user);

    /**
     * Extracts all the details of a Person from the address book based on the
     * name passed in.
     *
     * @param username The name as a String to search for.
     * @return all details in a Person object for the name
     */
    User getUser(String username);

    /**
     * Gets the number of addresses in the address book.
     *
     * @return size of address book.
     */
    int getSize();

    /**
     * Deletes a Person from the address book.
     *
     * @param username The name to delete from the address book.
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
