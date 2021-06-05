package common.sql;

import common.AssetType;
import common.Organisation;
import server.JDBCAssetTypeDataSource;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 *
 * @author Adam Buchsbaum
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
    public AssetTypeData() {
        listModel = new DefaultListModel();
        assetTypeData = new JDBCAssetTypeDataSource();

        for (String name : assetTypeData.AssetNameSet()) {
            listModel.addElement(name);
        }
    }


    /**
     * Adds a AssetType to the Asset table
     *
     * @param a A AssetType to add to the Asset table.
     */
    public void add(AssetType a) {

        // Check to see if the organisation has already been added
        if (!listModel.contains(a.getAsset())) {
            listModel.addElement(a.getAsset());
            assetTypeData.addAssetType(a);
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
     * @return the number of names in the user table.
     */
    public int getSize() {
        return assetTypeData.getAssetSize();
    }
}