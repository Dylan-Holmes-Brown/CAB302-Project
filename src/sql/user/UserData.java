package sql.user;

import common.User;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * This class uses an UserDataSource and its methods to retrieve data
 */
public class UserData {
    DefaultListModel listModel;
    UserDataSource userData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public UserData() {
        listModel = new DefaultListModel();
        userData = new JDBCUserDataSource();

        for (String name : userData.nameSet()){
            listModel.addElement(name);
        }
    }

    /**
     * Based on the name of the user in the user table, delete the user.
     *
     * @param key
     */
    public void remove(Object key) {
        // remove from both list and map
        listModel.removeElement(key);
        userData.deleteUser((String) key);
    }

    /**
     * Saves the data in the user table using the persistence mechanism.
     */
    public void persist() { userData.close(); }

    /**
     * Retrieves User details from the model.
     *
     * @param key the name to retrieve.
     * @return the User object related to the name.
     */
    public User get(Object key) { return userData.getUser((String) key); }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() { return listModel; }

    /**
     *
     * @return the number of names in the user table.
     */
    public int getSize() { return userData.getSize(); }
}
