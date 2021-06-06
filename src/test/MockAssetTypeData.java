package test;

import common.AssetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock class to mock the functionality of AssetTypeData to use without a database
 *
 * @author Dylan Holmes-Brown
 */
public class MockAssetTypeData {
    private List<AssetType> assetList;

    /**
     * Constructor initialises the asset list
     */
    public MockAssetTypeData() {
        assetList = new ArrayList<>();
    }

    /**
     * Adds a AssetType to the asset list
     *
     * @param asset A AssetType to add
     */
    public void add (AssetType asset) {
        // Check to see if the asset has already been added
        if (!assetList.contains(asset)) {
            assetList.add(asset);
        }
        // List already contains the asset
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Retrieves Asset details from the list
     *
     * @param key the asset to retrieve
     * @return the AssetType object related to the name.
     */
    public AssetType get(Object key) {
        // Loop through the entire list
        for (int i = 0; i< assetList.size(); i++) {
            // Check if the key is in the list
            if (assetList.get(i).equals(key)) {
                return assetList.get(i);
            }
        }
        // key couldn't be found in the list
        return null;
    }

    /**
     * Get the number of asset types in the list.
     *
     * @return the number of assets in the asset list
     */
    public int getSize() {
        return assetList.size();
    }

    /**
     * Based on the asset, delete the asset from the list
     *
     * @param key The name key to delete.
     */
    public void remove(Object key) {
        // If the key is in the list remove the asset
        if (assetList.contains(key)) {
            assetList.remove(key);
        }
        // Key not found, throw exception
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Accessor for the list
     *
     * @return the assetList
     */
    public List getModel() {
        return assetList;
    }
}
