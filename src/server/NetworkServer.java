package server;

import common.AssetTypes;
import common.CurrentTrades;
import common.Organisation;
import common.User;
import common.sql.AssetTypeDataSource;
import common.sql.Commands;
import common.sql.OrganisationDataSource;
import common.sql.UserDataSource;
import common.sql.CurrentDataSource;

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
    private OrganisationDataSource tableOrg;
    private UserDataSource tableUser;
    private CurrentDataSource tableCurrentTrade;

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
                    final Commands commandAssetType = (Commands) inputStream.readObject();
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
    private void handleCommand (Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream, Commands commandAssetType)
            throws IOException, ClassNotFoundException {
        switch (commandAssetType) {
            /*
            Asset Commands
             */
            case ADD_ASSET: {
                // Client is sending a new Asset Type
                final AssetTypes a = (AssetTypes) inputStream.readObject();
                synchronized (tableAssetType) {
                    tableAssetType.addAssetType(a);
                }
                System.out.println(String.format("Added asset '%s' to database from client '%s'.",
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
                        System.out.println(String.format("Sent asset '%s' to client '%s'.",
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
                System.out.println(String.format("Deleted asset '%s' on behalf of client '%s'.",
                        assetName, socket.toString()));
            }
            break;

            case GET_ASSET_SIZE: {
                // No parameters
                // Send the client the size of the table
                synchronized (tableAssetType) {
                    outputStream.writeInt(tableAssetType.getAssetSize());
                }
                outputStream.flush();
                System.out.println(String.format("Sent size of %d to client '%s'.",
                        tableAssetType.getAssetSize(), socket.toString()));
            }
            break;

            /*
            Organisation Commands
             */
            case ADD_ORG: {
                // Client is sending a new organisation
                final Organisation org =  (Organisation) inputStream.readObject();
                synchronized(tableOrg) {
                    tableOrg.addOrg(org);
                }
                System.out.println(String.format("Added Organisation '%s' to database with asset '%s' from the client '%s'.",
                        org.getName(), org.getAssets(), socket.toString()));
            }
            break;

            case ADD_CREDITS: {
                final String name = (String) inputStream.readObject();
                final int credits = (Integer) inputStream.readObject();
                synchronized(tableOrg) {
                    tableOrg.addCredits(name, credits);
                }
                System.out.println(String.format("Added '%s' credits to '%s' from client '%s'.",
                        credits, name, socket.toString()));
            }
            break;

            case REMOVE_CREDITS: {
                final String name = (String) inputStream.readObject();
                final int credits = (Integer) inputStream.readObject();
                synchronized(tableOrg) {
                    tableOrg.removeCredits(name, credits);
                }
                System.out.println(String.format("Removed '%s' credits from '%s' from client '%s'.",
                        credits, name, socket.toString()));
            }
            break;

            case ADD_QUANTITY: {
                final String name = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                final int quantity = (Integer) inputStream.readObject();
                synchronized(tableOrg) {
                    tableOrg.addQuantity(name, asset, quantity);
                }
                System.out.println(String.format("Added '%s' quantity to '%s' for '%s' from client '%s'.",
                        quantity, asset, name, socket.toString()));
            }
            break;

            case REMOVE_QUANTITY: {
                final String name = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                final int quantity = (Integer) inputStream.readObject();
                synchronized(tableOrg) {
                    tableOrg.addQuantity(name, asset, quantity);
                }
                System.out.println(String.format("Removed '%s' quantity from '%s' for '%s' from client '%s'.",
                        quantity, asset, name, socket.toString()));
            }
            break;

            case GET_ORG: {
                final String name = (String) inputStream.readObject();
                synchronized (tableOrg) {
                    tableOrg.getOrg(name);
                }
                System.out.println(String.format("Sent organisation '%s' to client %s",
                        name, socket.toString()));
            }
            break;

            case DELETE_ORG: {
                final String name = (String) inputStream.readObject();
                synchronized (tableOrg) {
                    tableOrg.deleteOrg(name);
                }

                System.out.println(String.format("Deleted organisation '%s' on behalf of client %s",
                        name, socket.toString()));
            }
            break;

            case GET_ORG_SIZE: {
                // no parameters sent by client

                // send the client back the size of the organisation table
                synchronized (tableOrg) {
                    outputStream.writeInt(tableOrg.getOrgSize());
                }
                outputStream.flush();

                System.out.println(String.format("Sent size of %d to client %s",
                        tableOrg.getOrgSize(), socket.toString()));
            }
            break;

            case GET_ORG_NAME_SET: {
                // no parameters sent by client

                // send the client back the name set
                synchronized (tableOrg) {
                    outputStream.writeObject(tableOrg.OrgNameSet());
                }
                outputStream.flush();

                System.out.println(String.format("Sent name set to client %s",
                        socket.toString()));
            }
            break;

            /*
            User Commands
             */
            case ADD_USER: {
                final User user = (User) inputStream.readObject();
                synchronized (tableUser) {
                    tableUser.addUser(user);
                }
                System.out.println(String.format("Added person '%s' to database from client %s",
                        user.getUsername(), socket.toString()));
            }
            break;

            case UPDATE_PASSWORD: {
                final String username = (String) inputStream.readObject();
                final String password = (String) inputStream.readObject();
                synchronized (tableUser) {
                    tableUser.updatePassword(username, password);
                }
                System.out.println(String.format("Updated password for '%s' from client '%s'",
                        username, socket.toString()));
            }
            break;

            case GET_USER: {
                final String username = (String) inputStream.readObject();
                synchronized (tableUser) {
                    final User user = tableUser.getUser(username);

                    outputStream.writeObject(user);
                    if (user != null)
                        System.out.println(String.format("Sent user '%s' to client %s",
                                user.getUsername(), socket.toString()));
                }
                outputStream.flush();
            }
            break;

            case DELETE_USER: {
                final String username = (String) inputStream.readObject();
                synchronized (tableUser) {
                    tableUser.deleteUser(username);
                }
                System.out.println(String.format("Deleted user '%s' on behalf of client '%s'",
                        username, socket.toString()));
            }
            break;

            case GET_USER_SIZE: {
                synchronized (tableUser) {
                    outputStream.writeInt(tableUser.getUserSize());
                }
                outputStream.flush();

                System.out.println(String.format("Sent size of %d to client %s",
                        tableUser.getUserSize(), socket.toString()));
            }
            break;

            case GET_USER_NAME_SET: {
                synchronized (tableUser) {
                    outputStream.writeObject(tableUser.UsernameSet());
                }
                outputStream.flush();

                System.out.println(String.format("Sent name set to client %s",
                        socket.toString()));
            }
            break;

            /*
            CurrentTrade Commands
             */
            case ADD_TRADE: {
                final CurrentTrades a = (CurrentTrades) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    tableCurrentTrade.addTrade(a);
                }
                System.out.println(String.format("Added trade '%s' to ??? from ??? '%s'.",
                        a.getBuySell(), socket.toString()));
            }
            break;

            case GET_BUY_SELL: {
                final String type = (String) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    final CurrentTrades currentTrades = tableCurrentTrade.getBuySell(type);

                    outputStream.writeObject(currentTrades);
                    if (currentTrades != null) {
                        System.out.println(String.format("Sent ??? '%s' to ??? '%x'.",
                                currentTrades.getBuySell(), socket.toString()));
                    }
                    outputStream.flush();
                }
            }
            break;

            case GET_ORGTRADE: {
                final String organisation = (String) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    final CurrentTrades currentTrades = tableCurrentTrade.getBuySell(organisation);

                    outputStream.writeObject(currentTrades);
                    if (currentTrades != null) {
                        System.out.println(String.format("Sent ??? '%s' to ??? '%x'.",
                                currentTrades.getBuySell(), socket.toString()));
                    }
                    outputStream.flush();
                }
            }
            break;

            case DELETE_TRADE: {
                final int id = (int) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    tableCurrentTrade.deleteTrade(id);
                }
                System.out.println(String.format("Deleted ??? '%s' '%s'.",
                        id, socket.toString()));
            }
            break;

            case GET_TRADE_SIZE: {
                synchronized (tableCurrentTrade) {
                    outputStream.writeInt(tableCurrentTrade.getCurrentSize());
                }
                outputStream.flush();
                System.out.println(String.format("Sent size of %d to client '%s'.",
                        tableCurrentTrade.getCurrentSize(), socket.toString()));
            }
            break;

            case GET_TRADE_NAME_SET: {
                synchronized (tableCurrentTrade) {
                    outputStream.writeObject((tableCurrentTrade.idSet()));
                }
                outputStream.flush();

                System.out.println(String.format("Sent id set to client %s",
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
        // connect to tables
        tableAssetType = new JDBCAssetTypeDataSource();
        tableOrg = new JDBCOrganisationDataSource();
        tableUser = new JDBCUserDataSource();

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
