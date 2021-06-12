package client;

import javax.swing.*;
import views.*;
import common.sql.UserData;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {


    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(Main::createAndShowLoginGUI);
    }

    public static void createAndShowLoginGUI() {
        new loginGui( new UserData(new NetworkDataSource()));
    }

}
