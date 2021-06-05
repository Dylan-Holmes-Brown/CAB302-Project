package test;

import common.AssetType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAssetTypeData {

    private List<AssetType> assetList;
    MockAssetTypeData data;
    AssetType asset = new AssetType("CPU");

    @BeforeEach @Test
    public void setupAssetList() {
        assetList = new ArrayList<>();
        data = new MockAssetTypeData();
    }

    @Test
    public void testAddAsset() {
        data.add(asset);
        assertEquals(asset, data.get(asset), "Adding asset failed");
    }

    @Test
    public void testGetSize() {
        data.add(asset);
        assertEquals(1, data.getSize());
    }

    @Test
    public void testRemoveAsset() {
        data.add(asset);
        data.remove(asset);
        assertNull(data.get(asset));
    }

    @Test
    public void testGetAssetModel() {
        data.add(asset);
        assetList.add(asset);
        assertEquals(assetList, data.getModel());
    }
}
