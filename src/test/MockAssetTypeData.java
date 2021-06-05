package test;

import common.AssetType;

import java.util.ArrayList;
import java.util.List;

public class MockAssetTypeData {
    private List<AssetType> assetList;

    public MockAssetTypeData() {
        assetList = new ArrayList<>();
    }

    public void add (AssetType asset) {
        if (!assetList.contains(asset)) {
            assetList.add(asset);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public AssetType get(Object key) {
        for (int i = 0; i<assetList.size(); i++) {
            if (assetList.get(i).equals(key)) {
                return assetList.get(i);
            }
        }
        return null;
    }

    public int getSize() {
        return assetList.size();
    }

    public void remove(Object key) {
        assetList.remove(key);
    }

    public List getModel() {
        return assetList;
    }
}
