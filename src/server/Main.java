package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
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
}
