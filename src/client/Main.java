package client;

import javax.swing.*;
import common.sql.AssetTypeData;
import Views.*;
import common.sql.OrganisationData;
import common.sql.UserData;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    private static void createAndShowGUI() {

        new adding( new UserData(new NetworkDataSource()));
    }
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    public static void createAndShowLoginGUI() {
        new loginGui( new UserData(new NetworkDataSource()));
    }

}
