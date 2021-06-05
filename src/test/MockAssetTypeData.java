package test;

import common.AssetTypes;

import java.util.ArrayList;
import java.util.List;

public class MockAssetTypeData {
    private List<AssetTypes> assetList;

    public MockAssetTypeData() {
        assetList = new ArrayList<>();
    }

    public void add (AssetTypes asset) {
        if (!assetList.contains(asset)) {
            assetList.add(asset);
        }
    }

    public AssetTypes get(Object key) {
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
