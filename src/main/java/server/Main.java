package server;

import java.io.IOException;

/**
 * Main class for the server
 *
 * @author Dylan Holmes-Brown
 */

public class Main {

    /**
     * Initialises the server
     *
     * @param args Array of arguments
     * @throws IOException Input/Output error
     */
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
