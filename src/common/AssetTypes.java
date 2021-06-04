package common;

import java.io.Serializable;

/**
 * This class initialises Asset Types
 *
 * @author Laku Jackson
 */


public class AssetTypes implements Serializable {
    private static final long serialVersionUID = 30L;
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
