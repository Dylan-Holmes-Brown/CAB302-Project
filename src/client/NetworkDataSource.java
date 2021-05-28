package client;

import common.AssetTypes;
import common.sql.AssetTypeDataSource;
import common.sql.CommandAssetType;

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
public class NetworkDataSource implements AssetTypeDataSource {
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

    @Override
    public void addAssetType(AssetTypes asset) {
        if (asset == null)
            throw new IllegalArgumentException("Asset cannot be null");
        try {
            // Tell sever - expect a asset's details
            outputStream.writeObject(CommandAssetType.ADD_ASSET);

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
            outputStream.writeObject(CommandAssetType.GET_ASSET);
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
    public int getSize() {
        try {
            outputStream.writeObject(CommandAssetType.GET_SIZE);
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
            outputStream.writeObject(CommandAssetType.DELETE_ASSET);
            outputStream.writeObject(assetName);
            outputStream.flush();
        }
        catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public Set<String> nameSet() {
        try {
            outputStream.writeObject(CommandAssetType.GET_NAME_SET);
            outputStream.flush();
            return (Set<String>)  inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }
}
