package server;

import common.AssetType;
import common.Trade;
import common.Organisation;
import common.User;
import common.sql.*;

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

    // The timeout used for actual clients
    private static final int SOCKET_READ_TIMEOUT = 5000;

    private AtomicBoolean running = new AtomicBoolean( true);

    // The connections to the database where everything is stored.
    private AssetTypeDataSource tableAssetType;
    private OrganisationDataSource tableOrg;
    private UserDataSource tableUser;
    private CurrentDataSource tableCurrentTrade;
    private TradeHistoryDataSource tableTradeHistory;

    /**
     * Constructor that gets server information from server.props file
     */
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
     * Handles the connection from the server socket
     *
     * @param socket Socket used to communicate to the connected client
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
     * Handles a request from the client
     *
     * @param socket Socket for the client
     * @param inputStream Input stream to read from
     * @param outputStream Output stream to write to
     * @param command Command being handled
     * @throws IOException Client has been disconnected
     * @throws ClassNotFoundException Client has sent an invalid object
     */
    private void handleCommand (Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream, Commands command)
            throws IOException, ClassNotFoundException {
        switch (command) {
            /*
            Asset Commands
             */
            case ADD_ASSET: {
                // Client is sending a new Asset Type
                final AssetType a = (AssetType) inputStream.readObject();
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
                    final AssetType assetTypes = tableAssetType.getAsset(assetName);

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

            case GET_ASSET_NAME_SET: {
                // no parameters sent by client

                // send the client back the name set
                synchronized (tableAssetType) {
                    outputStream.writeObject(tableAssetType.AssetNameSet());
                }
                outputStream.flush();

                System.out.println(String.format("Sent name set to client %s",
                        socket.toString()));
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
                        org.getName(), org.getAsset(), socket.toString()));
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
                    tableOrg.removeQuantity(name, asset, quantity);
                }
                System.out.println(String.format("Removed '%s' quantity from '%s' for '%s' from client '%s'.",
                        quantity, asset, name, socket.toString()));
            }
            break;

            case GET_ORG: {
                // Client sends the id of the organisation to retrieve
                final Integer id = (Integer) inputStream.readObject();
                synchronized (tableOrg) {
                    // Synchronise both the get as well as the send,
                    // therefore half a organisation isn't sent
                    final Organisation org = tableOrg.getOrg(id);

                    // Send the client back the asset type details or null
                    outputStream.writeObject(org);
                    if (org != null) {
                        System.out.println(String.format("Sent organisation '%s' to client '%s'.",
                                org.getName(), socket.toString()));
                    }
                    outputStream.flush();
                }
            }
            break;

            case GET_ORG_ASSET_SET: {
                final String organisation = (String) inputStream.readObject();
                synchronized (tableOrg) {
                    outputStream.writeObject((tableOrg.orgAssetSet(organisation)));
                }
                outputStream.flush();

                System.out.println(String.format("Sent assets of organisation '%s' to client '%s'.",
                        organisation, socket.toString()));
            }

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

                System.out.println(String.format("Sent size of '%d' to client '%s'",
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

                System.out.println(String.format("Sent organisation id set to client '%s'",
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
                System.out.println(String.format("Added user '%s' to database from client '%s'",
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
                final Trade a = (Trade) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    tableCurrentTrade.addTrade(a);
                }
                System.out.println(String.format("Added trade '%s' to database from client '%s'.",
                        a.getBuySell(), socket.toString()));
            }
            break;

            case GET_CURRENT_TRADE: {
                final Integer id = (Integer) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    final Trade trade = tableCurrentTrade.getCurrentTrade(id);

                    outputStream.writeObject(trade);
                    if (trade != null)
                        System.out.println(String.format("Sent current trade '%s' to client %s",
                                trade.getID(), socket.toString()));
                }
                outputStream.flush();
            }
            break;

            case GET_BUY_SELL: {
                final String type = (String) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    outputStream.writeObject((tableCurrentTrade.typeSet(type)));
                }
                outputStream.flush();

                System.out.println(String.format("Sent id of trades with type '%s' to client '%s'.",
                        type, socket.toString()));
            }
            break;

            case GET_ORGTRADE: {
                final String organisation = (String) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    outputStream.writeObject((tableCurrentTrade.orgSet(organisation)));
                }
                outputStream.flush();

                System.out.println(String.format("Sent id of trades from organisation '%s' to client '%s'.",
                        organisation, socket.toString()));
            }
            break;

            case DELETE_TRADE: {
                final int id = (int) inputStream.readObject();
                synchronized (tableCurrentTrade) {
                    tableCurrentTrade.deleteTrade(id);
                }
                System.out.println(String.format("Deleted trade '%s' on behalf of client '%s'.",
                        id, socket.toString()));
            }
            break;

            case GET_TRADE_SIZE: {
                synchronized (tableCurrentTrade) {
                    outputStream.writeInt(tableCurrentTrade.getCurrentSize());
                }
                outputStream.flush();
                System.out.println(String.format("Sent size of '%d' to client '%s'.",
                        tableCurrentTrade.getCurrentSize(), socket.toString()));
            }
            break;

            case GET_TRADE_NAME_SET: {
                synchronized (tableCurrentTrade) {
                    outputStream.writeObject((tableCurrentTrade.idSet()));
                }
                outputStream.flush();

                System.out.println(String.format("Sent id set to client '%s'.",
                        socket.toString()));
            }
            break;

            /*
            Trade History Commands
             */
            case ADD_TRADE_HISTORY: {
                final Trade trade = (Trade) inputStream.readObject();
                synchronized (tableTradeHistory) {
                    tableTradeHistory.addTradeHistory(trade);
                }
                System.out.println(String.format("Added trade history '%s' to database from client '%s'.",
                        trade.getID(), socket.toString()));
            }
            break;

            case GET_TRADE_HISTORY: {
                final Integer id = (Integer) inputStream.readObject();
                synchronized (tableTradeHistory) {
                    final Trade trade = tableTradeHistory.getTradeHistory(id);

                    outputStream.writeObject(trade);
                    if (trade != null)
                        System.out.println(String.format("Sent trade history '%s' to client %s",
                                trade.getID(), socket.toString()));
                }
                outputStream.flush();
            }
            break;

            case GET_TRADE_ASSETS: {
                final String asset = (String) inputStream.readObject();
                synchronized (tableTradeHistory) {
                    outputStream.writeObject((tableTradeHistory.assetSet(asset)));
                }
                outputStream.flush();

                System.out.println(String.format("Sent id of trade history with asset '%s' to client '%s'.",
                        asset, socket.toString()));
            }
            break;

            case GET_TRADE_HISTORY_SIZE: {
                synchronized (tableTradeHistory) {
                    outputStream.writeInt(tableTradeHistory.getHistorySize());
                }
                outputStream.flush();
                System.out.println(String.format("Sent size of '%d' to client '%s'.",
                        tableTradeHistory.getHistorySize(), socket.toString()));
            }
            break;

            case GET_TRADE_HISTORY_ID_SET: {
                synchronized (tableTradeHistory) {
                    outputStream.writeObject((tableTradeHistory.historySet()));
                }
                outputStream.flush();

                System.out.println(String.format("Sent id set to client '%s'.",
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
        tableCurrentTrade = new JDBCCurrentDataSource();
        tableTradeHistory = new JDBCTradeHistoryDataSource();

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