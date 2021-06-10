package client;

import common.AssetType;
import common.Trade;
import common.Organisation;
import common.User;
import common.sql.*;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *This class uses the JDBC classes of all tables within the database to communicate with the server
 *
 * @author Dylan Holmes-Brown
 */
public class NetworkDataSource implements AssetTypeDataSource, OrganisationDataSource, UserDataSource, CurrentDataSource, TradeHistoryDataSource {
    private static String HOSTNAME;
    private static int PORT;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    /**
     *
     */
    public NetworkDataSource() {
        Properties properties = new Properties();
        FileInputStream in = null;

        try {
            // File to read
            in = new FileInputStream("./server.props");
            properties.load(in);
            in.close();

            // Specify the server address and port
            HOSTNAME = properties.getProperty("server.address");
            String port = properties.getProperty("server.port");
            PORT = Integer.parseInt(port);

            // Persist a single connection throughout the runtime.
            socket = new Socket(HOSTNAME, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        // server.props file doesn't exist
        catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        }
        // Cannot connect to the server
        catch (IOException e) {
            System.out.println("Failed to connect to the server");
        }
        // Port cannot be converted to integer
        catch (NumberFormatException nfe) {
            System.err.println(nfe);
        }
    }

    /*
    Asset Type
     */

    /**
     * @see AssetTypeDataSource#addAssetType(AssetType)
     */
    @Override
    public void addAssetType(AssetType asset) {
        // asset object is null
        if (asset == null)
            throw new IllegalArgumentException("Asset cannot be null");
        try {
            // Tell sever - expect an asset's details
            outputStream.writeObject(Commands.ADD_ASSET);

            // Send the data
            outputStream.writeObject(asset);
            outputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see AssetTypeDataSource#getAsset(String)
     */
    @Override
    public AssetType getAsset(String assetName) {
        try {
            // Tell server - expect asset name, and send the details
            outputStream.writeObject(Commands.GET_ASSET);
            outputStream.writeObject(assetName);

            // Flush as the request might not be sent yet, and we're waiting for a response
            outputStream.flush();

            // Read the asset's details back from the server
            return (AssetType)inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @see AssetTypeDataSource#getAssetSize()
     */
    @Override
    public int getAssetSize() {
        try {
            // No parameters
            outputStream.writeObject(Commands.GET_ASSET_SIZE);
            outputStream.flush();

            // Read the asset details back from the server
            return inputStream.readInt();
        }
        catch (IOException | ClassCastException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @see AssetTypeDataSource#deleteAssetType(String)
     */
    @Override
    public void deleteAssetType(String assetName) {
        try {
            // Tell server - expect asset name, and send the details
            outputStream.writeObject(Commands.DELETE_ASSET);
            outputStream.writeObject(assetName);
            outputStream.flush();
        }
        catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see AssetTypeDataSource#AssetNameSet()
     */
    @Override
    public Set<String> AssetNameSet() {
        try {
            outputStream.writeObject(Commands.GET_ASSET_NAME_SET);
            outputStream.flush();
            return (Set<String>)  inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /*
    Organisation
     */

    /**
     * @see OrganisationDataSource#addOrg(common.Organisation)
     */
    @Override
    public void addOrg(Organisation org) {
        if (org == null)
            throw new IllegalArgumentException("Organisation cannot be null");

        try {
            // Tell the server to expect a organisation's details
            outputStream.writeObject(Commands.ADD_ORG);

            // Send organisation data
            outputStream.writeObject(org);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#deleteOrg(String)
     */
    @Override
    public void deleteOrg(String name) {
        try {
            outputStream.writeObject(Commands.DELETE_ORG);
            outputStream.writeObject(name);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#addCredits(String, int)
     */
    @Override
    public void addCredits(String name, int credits) {
        try {
            outputStream.writeObject(Commands.ADD_CREDITS);
            outputStream.writeObject(name);
            outputStream.writeObject(credits);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#removeCredits(String, int)
     */
    @Override
    public void removeCredits(String name, int credits) {
        try {
            outputStream.writeObject(Commands.REMOVE_CREDITS);
            outputStream.writeObject(name);
            outputStream.writeObject(credits);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#addQuantity(String, String, int)
     */
    @Override
    public void addQuantity(String name, String asset, int credits) {
        try {
            outputStream.writeObject(Commands.ADD_QUANTITY);
            outputStream.writeObject(name);
            outputStream.writeObject(asset);
            outputStream.writeObject(credits);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#removeQuantity(String, String, int)
     */
    @Override
    public void removeQuantity(String name, String asset, int credits) {
        try {
            outputStream.writeObject(Commands.REMOVE_QUANTITY);
            outputStream.writeObject(name);
            outputStream.writeObject(asset);
            outputStream.writeObject(credits);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see OrganisationDataSource#getOrg(String)
     */
    @Override
    public Organisation getOrg(String name) {
        try {
            // Tell server to expect a organisations name, and send the details
            outputStream.writeObject(Commands.GET_ORG);
            outputStream.writeObject(name);
            outputStream.flush();

            // Read the organisation's details from the server
            return (Organisation) inputStream.readObject();
        } catch (IOException | ClassNotFoundException  | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @see OrganisationDataSource#orgAssetSet(String)
     */
    @Override
    public Set<String> orgAssetSet(String organisation) {
        try {
            outputStream.writeObject(Commands.GET_ASSET_NAME_SET);
            outputStream.writeObject(organisation);
            outputStream.flush();
            return (Set<String>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /**
     * @see OrganisationDataSource#getOrgSize()
     */
    @Override
    public int getOrgSize() {
        try {
            outputStream.writeObject(Commands.GET_ORG_SIZE);
            outputStream.flush();

            // Read the asset details back from the server
            return inputStream.readInt();
        }
        catch (IOException | ClassCastException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @see OrganisationDataSource#OrgNameSet()
     */
    @Override
    public Set<String> OrgNameSet() {
        try {
            outputStream.writeObject(Commands.GET_ORG_NAME_SET);
            outputStream.flush();
            return (Set<String>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /*
    User
     */
    /**
     * @see UserDataSource#addUser(common.User)
     */
    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        try {
            // Tell the server to expect a user's details
            outputStream.writeObject(Commands.ADD_USER);

            // Send the user data
            outputStream.writeObject(user);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#updatePassword(String, String)
     */
    @Override
    public void updatePassword(String username, String password) {
        try {
            // Tell the server to expect a user's details
            outputStream.writeObject(Commands.UPDATE_PASSWORD);

            // Send the data
            outputStream.writeObject(username);
            outputStream.writeObject(password);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#getUser(String)
     */
    @Override
    public User getUser(String username) {
        try {
            // Tell the server to expect the user's username, and send the details
            outputStream.writeObject(Commands.GET_USER);
            outputStream.writeObject(username);
            outputStream.flush();

            // Read the users details from the server
            return (User) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @see UserDataSource#getUserSize()
     */
    @Override
    public int getUserSize() {
        try {
            outputStream.writeObject(Commands.GET_USER_SIZE);
            outputStream.flush();

            // Read the user details from the server
            return inputStream.readInt();
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @see UserDataSource#deleteUser(String)
     */
    @Override
    public void deleteUser(String username) {
        try {
            outputStream.writeObject(Commands.DELETE_USER);
            outputStream.writeObject(username);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see UserDataSource#UsernameSet()
     */
    @Override
    public Set<String> UsernameSet() {
        try {
            outputStream.writeObject(Commands.GET_USER_NAME_SET);
            outputStream.flush();
            return (Set<String>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /*
    Current Trade
     */
    @Override
    public void addTrade(Trade trades) {
        if (trades == null) {
            throw new IllegalArgumentException("Trade cannot be null");
        }

        try {
            outputStream.writeObject(Commands.ADD_TRADE);

            outputStream.writeObject(trades);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trade getCurrentTrade(Integer id) {
        try {
            // Tell the server to expect the current trades id, and send the details
            outputStream.writeObject(Commands.GET_CURRENT_TRADE);
            outputStream.writeObject(id);
            outputStream.flush();

            // Read the users details from the server
            return (Trade) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Integer> typeSet(String type) {
        try {
            outputStream.writeObject(Commands.GET_BUY_SELL);
            outputStream.writeObject(type);
            outputStream.flush();
            return (Set<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public Set<Integer> orgSet(String org) {
        try {
            outputStream.writeObject(Commands.GET_ORGTRADE);
            outputStream.writeObject(org);
            outputStream.flush();
            return (Set<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public int getCurrentSize() {
        try {
            outputStream.writeObject(Commands.GET_TRADE_SIZE);
            outputStream.flush();

            return inputStream.readInt();
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void deleteTrade(int id) {
        try {
            outputStream.writeObject(Commands.DELETE_TRADE);
            outputStream.writeObject(id);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Integer> idSet() {
        try {
            outputStream.writeObject(Commands.GET_TRADE_NAME_SET);
            outputStream.flush();
            return (Set<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /*
    Trade History
     */
    @Override
    public void addTradeHistory(Trade trades) {
        if (trades == null) {
            throw new IllegalArgumentException("Trade cannot be null");
        }

        try {
            outputStream.writeObject(Commands.ADD_TRADE_HISTORY);

            outputStream.writeObject(trades);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getHistorySize() {
        try {
            outputStream.writeObject(Commands.GET_TRADE_HISTORY_SIZE);
            outputStream.flush();

            return inputStream.readInt();
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Set<Integer> historySet() {
        try {
            outputStream.writeObject(Commands.GET_TRADE_HISTORY_ID_SET);
            outputStream.flush();
            return (Set<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public Set<Integer> assetSet(String asset) {
        try {
            outputStream.writeObject(Commands.GET_TRADE_ASSETS);
            outputStream.writeObject(asset);
            outputStream.flush();
            return (Set<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public void close() {
    }
}
