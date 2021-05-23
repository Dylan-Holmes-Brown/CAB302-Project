package client;

import common.AssetTypes;
import common.sql.asset_type.AssetTypeDataSource;
import common.sql.asset_type.CommandAssetType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class NetworkDataSource implements AssetTypeDataSource {
    private static final String HOSTNAME = "0.0.0.0";
    private static final int PORT = 10000;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public NetworkDataSource() {
        try {
            socket = new Socket(HOSTNAME, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            System.out.println("Failed to connect to the server");
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
