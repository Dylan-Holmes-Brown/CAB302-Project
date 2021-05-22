package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NetworkServer server = new NetworkServer();
        System.out.println("Asset Trading Server\n" +
               "======================\n");
        try {
            System.out.println("Server running on port: " + NetworkServer.getPort());
            server.start();
        }
        catch (IOException e) {
            // In the case of an exception, show an error message and terminate
            System.out.println("Error starting the server");
            System.exit(1);
        }
    }

    private static void createAndShowGUI(NetworkServer server) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Network server for Address Book");
        JButton shutdownButton = new JButton("Shut down server");
        // This button will simply close the dialog. CLosing the dialog
        // will shut down the server
        shutdownButton.addActionListener(e -> dialog.dispose());

        // When the dialog is closed, shut down the server
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                server.shutdown();
            }
        });

        // Create a label to show server info
        JLabel serverLabel = new JLabel("Server running on port " + NetworkServer.getPort());

        // Add the button and label to the dialog
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(shutdownButton, BorderLayout.SOUTH);
        dialog.getContentPane().add(serverLabel, BorderLayout.NORTH);
        dialog.pack();

        // Centre the dialog on the screen
        dialog.setLocationRelativeTo(null);

        // Show the dialog
        dialog.setVisible(true);
    }
}
