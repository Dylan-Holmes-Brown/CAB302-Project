package client;

import javax.swing.*;
import common.sql.AssetTypeData;
import Views.loginGui;
import common.sql.OrganisationData;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    private static void createAndShowGUI() {
        new loginGui();
            new OrganisationData();
                new NetworkDataSource();
    }
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
