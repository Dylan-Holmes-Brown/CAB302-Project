package common;

import org.junit.jupiter.api.*;
import common.mocks.MockAssetTypeData;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Test class for testing all the functionality of the asset type data
 *
 * @author Dylan Holmes-Brown
 */
public class TestAssetTypeData {

    private List<AssetType> assetList;
    private MockAssetTypeData data;
    private AssetType asset;
    private AssetType asset2;

    /**
     * Before each test.test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupAssetList() {
        assetList = new ArrayList<>();
        data = new MockAssetTypeData();
        asset = new AssetType("CPU");
        asset2 = new AssetType("Hard Drive");
    }

    /**
     * Test adding an asset
     */
    @Test
    public void testAddAsset() {
        data.add(asset);
        assertEquals(asset, data.get(asset));
    }

    /**
     * Test adding two of the same assets
     */
    @Test
    public void testAddDuplicate() {
        data.add(asset);
        assertThrows(IllegalArgumentException.class, () -> data.add(asset));
    }

    /**
     * Test adding an asset and getting a different one
     */
    @Test
    public void testGetDifferentAsset() {
        data.add(asset);
        assertNull(data.get(asset2));
    }

    /**
     * Test adding two assets and getting them both back
     */
    @Test
    public void testGetMultipleAssets() {
        data.add(asset);
        data.add(asset2);
        assertEquals(asset, data.get(asset));
        assertEquals(asset2, data.get(asset2));
    }

    /**
     * Test getting an asset when none are in the list
     */
    @Test
    public void testGetNoAsset() {
        assertNull(data.get(asset));
    }

    /**
     * Test getting the size of the asset list
     */
    @Test
    public void testGetSize() {
        data.add(asset);
        assertEquals(1, data.getSize());
    }

    /**
     * Test getting the size of the asset list when nothing has been added
     */
    @Test
    public void testGetZero() {
        assertEquals(0, data.getSize());
    }

    /**
     * Test removing an asset
     */
    @Test
    public void testRemoveAsset() {
        data.add(asset);
        data.remove(asset);
        assertNull(data.get(asset));
    }

    /**
     * Test removing an asset that isn't in the list
     */
    @Test
    public void testRemoveNoAsset() {
        assertThrows(IllegalArgumentException.class, () -> data.remove(asset));
    }

    /**
     * Test getting the asset model
     */
    @Test
    public void testGetAssetModel() {
        data.add(asset);
        assetList.add(asset);
        assertEquals(assetList, data.getModel());
    }

    /**
     * Test getting the asset model with multiple assets in it
     */
    @Test
    public void testGetMultipleAssetsModel() {
        data.add(asset);
        assetList.add(asset);
        data.add(asset2);
        assetList.add(asset2);
        assertEquals(assetList, data.getModel());
    }

    /**
     * Test getting the asset model with nothing in the list
     */
    @Test
    public void testNoUsersModel() {
        assertEquals(assetList, data.getModel());
    }
}
