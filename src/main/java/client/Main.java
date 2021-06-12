package client;

import javax.swing.*;
import views.*;
import common.sql.UserData;

/**
 * Main class for the client
 *
 * @author Dylan Holmes-Brown
 */
public class Main {

    /**
     * Initialises the client
     *
     * @param args Array of arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowLoginGUI);
    }

    /**
     * Creates the initial login GUI for the client
     */
    public static void createAndShowLoginGUI() {
        new loginGui( new UserData(new NetworkDataSource()));
    }

}
