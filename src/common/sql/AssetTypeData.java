package common.sql;

import common.AssetType;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * This class uses an AssetTypeDataSource and its methods to retrieve data.
 *
 * @author Adam Buchsbaum
 * @author Dylan Holmes-Brown
 */

/**
 * This class uses an AssetTypeDataSource and its methods to retrieve data
 */

public class AssetTypeData {
    DefaultListModel listModel;
    AssetTypeDataSource assetTypeData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     */
    public AssetTypeData(AssetTypeDataSource dataSource) {
        listModel = new DefaultListModel();
        assetTypeData = dataSource;

        for (String name : assetTypeData.AssetNameSet()) {
            listModel.addElement(name);
        }
    }

    /**
     * Adds a AssetType to the asset_types table
     *
     * @param asset A AssetType to add to the user table.
     */
    public void add(AssetType asset) {

        // Check to see if the user has already been added
        if (!listModel.contains(asset.getAsset())) {
            listModel.addElement(asset.getAsset());
            assetTypeData.addAssetType(asset);
        }
    }

    /**
     * Based on the name of the user in the user table, delete the user.
     *
     * @param key
     */
    public void remove(Object key) {
        // remove from both list and map
        listModel.removeElement(key);
        assetTypeData.deleteAssetType((String) key);
    }

    /**
     * Saves the data in the user table using the persistence mechanism.
     */
    public void persist() {
        assetTypeData.close();
    }

    /**
     * Retrieves User details from the model.
     *
     * @param key the name to retrieve.
     * @return the User object related to the name.
     */
    public AssetType get(Object key) {
        return assetTypeData.getAsset((String) key);
    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() {
        return listModel;
    }

    /**
     * Get the number of assets types in the table
     *
     * @return the size of the table.
     */
    public int getSize() {
        return assetTypeData.getAssetSize();
    }
}