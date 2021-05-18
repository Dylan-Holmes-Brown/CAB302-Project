package common;

/**
 * This class initialises Asset Types
 *
 * @author Laku Jackson
 */


public class AssetTypes {
    public int ID;
    public String assetType;
    public AssetTypes(){

    }
    public AssetTypes(String assetType){
        this.assetType = assetType;
    }
    public String getAssetType(){ return assetType; }
    public void setName(String assetType){ this.assetType = assetType; }
}
