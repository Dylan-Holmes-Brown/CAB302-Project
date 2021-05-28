package common;

/**
 * This class initialises Asset Types
 *
 * @author Laku Jackson
 */


public class AssetTypes {
    public String assetType;
    public AssetTypes() {

    }

    public AssetTypes(String assetType){

        this.assetType = assetType;
    }

    /**
     * @return the asset name
     */
    public String getAsset(){ return assetType; }

    /**
     * @param assetType the asset name to set
     */
    public void setName(String assetType){ this.assetType = assetType; }
}
