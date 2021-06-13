package common;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.AssetType;

/**
 * Test class for testing all the functionality of the asset type object
 *
 * @author Adam Buchsbaum
 */

public class TestAssetType {
    AssetType asset;
    AssetType asset2;

    /**
     * Before each test.test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupAssetType()
    {
        asset = new AssetType("CPU");
        asset2 = new AssetType();
    }

    /**
     * Test getting an asset's name
     */
    @Test
    public void testGetAsset()
    {
        assertEquals("CPU", asset.getAsset());
    }

    /**
     * Test setting an asset's name
     */
    @Test
    public void testSetAsset()
    {
        asset.setAsset("GPU");
        assertEquals("GPU", asset.getAsset());
    }
}
