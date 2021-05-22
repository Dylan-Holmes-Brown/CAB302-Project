package client;

import javax.swing.*;
import common.sql.asset_type.AssetTypeData;
import Views.loginGui;

public class Main {
    private static void createAndShowGUI() {
        new loginGui();
            new AssetTypeData();
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
