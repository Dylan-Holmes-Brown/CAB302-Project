package common;

import java.io.Serializable;

/**
 * This class initialises an Organisation
 *
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class Organisation implements Serializable {
    private static final long serialVersionUID = 20L;
    private int id;
    private String name;
    private int credits = 0;
    private String assets;
    private int quantity = 0;

    /**
     * Empty constructor to create Organisation Object
     */
    public Organisation() {

    }

    /**
     * Organisation Constructor
     *
     * @param name The name of the organisation
     * @param credits The amount of credits an organisation has
     * @param assets The name of assets the organisation has
     * @param quantity The quantity of the assets
     */
    public Organisation(String name, int credits, String assets, int quantity) {
        this.name = name;
        this.credits = credits;
        this.assets = assets;
        this.quantity = quantity;
    }

    /**
     * Organisation Constructor for testing
     *
     * @param id the ide of the organisation
     * @param name The name of the organisation
     * @param credits The amount of credits an organisation has
     * @param assets The name of assets the organisation has
     * @param quantity The quantity of the assets
     */
    public Organisation(int id, String name, int credits, String assets, int quantity) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.assets = assets;
        this.quantity = quantity;
    }

    /**
     * Get the id of the organisation object.
     *
     * @return The id of the organisation trade.
     */
    public Integer getID() { return id; }

    /**
     * Set the id of the organisation object.
     *
     * @param id the id of the organisation to set.
     */
    public void setID(Integer id) { this.id = id; }

    /**
     * Get the name of the organisation object.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Set the name of the organisation object.
     *
     * @param name the organisation name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get the credits of the organisation object.
     *
     * @return the credits
     */
    public int getCredits() { return credits; }

    /**
     * Set the credits of the organisation object.
     *
     * @param credits the credits to set
     */
    public void setCredits(int credits) { this.credits = credits; }

    /**
     * Get an asset of the organisation object.
     *
     * @return the assets
     */
    public String getAsset() { return assets; }

    /**
     * @param assets the assets to set
     */
    public void setAssets(String assets) { this.assets = assets; }

    /**
     * Get the quantity of an asset the organisation object holds.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Set the quantity of an asset the organisation object holds.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
