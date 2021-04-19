package common;

/**
 * This class initialises an Organization and there assets
 *
 * @author Laku Jackson
 */

public class Organization {
    public String orgName;
    private int credits = 0;
    private int assets = 0;
    private int quantity = 0;


    public Organization() {

    }

    public Organization(String orgName, int credits, int assets, int quantity) {
        this.orgName = orgName;
        this.credits = credits;
        this.assets = assets;
        this.quantity = quantity;
    }




}
