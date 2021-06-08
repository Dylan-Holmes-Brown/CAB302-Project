package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import common.AssetType;

public class TestAssetType {
    AssetType asset;

    @BeforeEach @Test
    public void setupAssetType()
    {
        asset = new AssetType("CPU");
    }

    @Test
    public void testGetAsset()
    {
        assertEquals("CPU", asset.getAsset());
    }

    @Test
    public void testSetAsset()
    {
        asset.setAsset("GPU");
        assertEquals("GPU", asset.getAsset());
    }
}
