package common;

import java.io.Serializable;

/**
 * This class initialises an Organisation and there assets
 *
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class Organisation implements Serializable {
    private static final long serialVersionUID = 20L;
    public String name;
    public int credits = 0;
    public String assets;
    public int quantity = 0;


    public Organisation() {

    }

    public Organisation(String name, int credits, String assets, int quantity) {
        this.name = name;
        this.credits = credits;
        this.assets = assets;
        this.quantity = quantity;
    }

    public Organisation(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    /**
     * @return the name
     */
    public String getName() { return name; }

    /**
     * @param name the organisation name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return the credits
     */
    public int getCredits() { return credits; }

    /**
     * @param credits the credits to set
     */
    public void setCredits(int credits) { this.credits = credits; }

    /**
     * @return the assets
     */
    public String getAssets() { return assets; }

    /**
     * @param assets the assets to set
     */
    public void setAssets(String assets) { this.assets = assets; }

    /**
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
