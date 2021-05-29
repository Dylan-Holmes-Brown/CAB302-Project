package client;

import common.AssetTypes;
import common.Organisation;
import common.sql.AssetTypeDataSource;
import common.sql.Commands;
import common.sql.OrganisationDataSource;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class NetworkDataSource implements AssetTypeDataSource, OrganisationDataSource {
    private static String HOSTNAME;
    private static int PORT;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public NetworkDataSource() {
        try {

            Properties properties = new Properties();
            FileInputStream in = null;
            in = new FileInputStream("./server.props");
            properties.load(in);
            in.close();

            // specify the port
            HOSTNAME = properties.getProperty("server.address");
            String port = properties.getProperty("server.port");
            PORT = Integer.parseInt(port);

            socket = new Socket(HOSTNAME, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException e) {
            System.out.println("Failed to connect to the server");
        } catch (NumberFormatException nfe) {
            System.err.println(nfe);
        }
    }

    /*
    Asset Type
     */
    @Override
    public void addAssetType(AssetTypes asset) {
        if (asset == null)
            throw new IllegalArgumentException("Asset cannot be null");
        try {
            // Tell sever - expect a asset's details
            outputStream.writeObject(Commands.ADD_ASSET);

            // Send the data
            outputStream.writeObject(asset);
            outputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AssetTypes getAsset(String assetName) {
        try {
            // Tell server - expect asset name, and send the details
            outputStream.writeObject(Commands.GET_ASSET);
            outputStream.writeObject(assetName);

            // Flush as the request might not be sent yet, and we're waiting for a response
            outputStream.flush();

            // Read the asset's details back from the server
            return (AssetTypes)inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getAssetSize() {
        try {
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

    @Override
    public void deleteAssetType(String assetName) {
        try {
            outputStream.writeObject(Commands.DELETE_ASSET);
            outputStream.writeObject(assetName);
            outputStream.flush();
        }
        catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> AssetnameSet() {
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

    /*
    Current Trade
     */

    @Override
    public void close() {
    }
}
