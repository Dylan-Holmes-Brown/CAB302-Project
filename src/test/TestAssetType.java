package test;

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

    /**
     * Before each test initialise any used data for the tests
     */
    @BeforeEach @Test
    public void setupAssetType()
    {
        asset = new AssetType("CPU");
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
