package server;

import common.AssetTypes;
import common.sql.AssetTypeDataSource;
import common.sql.CommandAssetType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class NetworkServer {
    private static int PORT;

    // The timeout period between accepting clients, not reading from the socket itself
    private static final int SOCKET_ACCEPT_TIMEOUT = 100;

    // The timout used for actual clients
    private static final int SOCKET_READ_TIMEOUT = 5000;

    private AtomicBoolean running = new AtomicBoolean( true);

    // The connections to the database where everything is stored.
    private AssetTypeDataSource tableAssetType;

    public NetworkServer(){
        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./server.props");
            properties.load(in);
            in.close();

            // specify the port
            String port = properties.getProperty("server.port");
            PORT = Integer.parseInt(port);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException nfe) {
            System.err.println(nfe);
        }
    }

    /**
     *
     * @param socket
     */
    private void handleConnection(Socket socket) {
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    final CommandAssetType commandAssetType = (CommandAssetType) inputStream.readObject();
                    handleCommand(socket, inputStream, outputStream, commandAssetType);
                } catch (SocketTimeoutException e) {
                    continue;
                }
            }
        }
        catch (IOException | ClassCastException | ClassNotFoundException e) {
            System.out.println(String.format("Connection %s closed", socket.toString()));
        }
    }

    /**
     *
     * @param socket
     * @param inputStream
     * @param outputStream
     * @param commandAssetType
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void handleCommand (Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream, CommandAssetType commandAssetType)
            throws IOException, ClassNotFoundException {
        switch (commandAssetType) {
            case ADD_ASSET: {
                // Client is sending a new Asset Type
                final AssetTypes a = (AssetTypes) inputStream.readObject();
                synchronized (tableAssetType) {
                    tableAssetType.addAssetType(a);
                }
                System.out.println(String.format("Added asset '%s' to database from client %s",
                        a.getAsset(), socket.toString()));
            }
            break;

            case GET_ASSET: {
                // Client sends the name of the asset to retrieve
                final String assetName = (String) inputStream.readObject();
                synchronized (tableAssetType) {
                    // Synchronise both the get as well as the send,
                    // therefore half a asset isn't sent
                    final AssetTypes assetTypes = tableAssetType.getAsset(assetName);

                    // Send the client back the asset type details or null
                    outputStream.writeObject(assetTypes);
                    if (assetTypes != null) {
                        System.out.println(String.format("Sent asset '%s' to client '%s'",
                                assetTypes.getAsset(), socket.toString()));
                    }
                    outputStream.flush();
                }
            }
            break;

            case DELETE_ASSET: {
                // Parameter - the asset name
                final String assetName = (String) inputStream.readObject();
                synchronized (tableAssetType) {
                    tableAssetType.deleteAssetType(assetName);
                }
                System.out.println(String.format("Deleted asset '%s' on behalf of client %s",
                        assetName, socket.toString()));
            }
            break;

            case GET_SIZE: {
                // No parameters
                // Send the client the size of the table
                synchronized (tableAssetType) {
                    outputStream.writeInt(tableAssetType.getSize());
                }
                outputStream.flush();
                System.out.println(String.format("Sent size of %d to client %s",
                        tableAssetType.getSize(), socket.toString()));
            }
            break;
            case GET_NAME_SET: {
                // No parameters
                // Send the client the name set
                synchronized (tableAssetType) {
                    outputStream.writeObject(tableAssetType.nameSet());
                }
                outputStream.flush();
                System.out.println(String.format("Sent name set to client %s",
                        socket.toString()));
            }
            break;
        }
    }

    /**
     * Returns the port the server is configured to use
     * @return the port number
     */
    public static int getPort() {return PORT;}

    public void start() throws IOException {
        // connect
        tableAssetType = new JDBCAssetTypeDataSource();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
            for (;;) {
                if (!running.get()) {
                    // The server is no longer running
                    break;
                }
                try {
                    final Socket socket = serverSocket.accept();
                    socket.setSoTimeout(SOCKET_READ_TIMEOUT);

                    // Got connection from client. Use runnable and thread to handle client.
                    // The lambda wraps the functional interface.
                    final Thread clientThread = new Thread(() -> handleConnection(socket));
                    clientThread.start();
                }
                // Do nothing - having a timeout is normal
                catch (SocketTimeoutException ignored) {
                }
                // Report other exceptions by printing the stack trace
                // Do not shut down the server.
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            // If there is an error at start up, show an error then exit.
            e.printStackTrace();
            System.exit(1);
        }

        // Close down the server
        System.exit(0);
    }

    /**
     * Requests the server to shut down.
     */
    public void shutdown() {
        // Shut the server down
        running.set(false);
    }
}
