package common;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 *
 * @author Dylan Holmes-Brown
 */

public class Trade {
    private static final long serialVersionUID = 40L;
    private int id;
    private String buySell;
    private String organisation;
    private String asset;
    private int quantity;
    private int price;
    private Date date;

    public Trade(){}

    public Trade(String buySell, String org, String asset, int quantity, int price, Date date) {
        this.buySell = buySell;
        this.organisation = org;
        this.asset = asset;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }


    /**
     * Get the id of the current trade object.
     *
     * @return The id of the current trade.
     */
    public Integer getID() { return id; }

    /**
     * Set the id of the current trade object.
     *
     * @param id the id of the current trade to set.
     */
    public void setID(Integer id) { this.id = id; }

    /**
     * Get the type of the current trade object.
     *
     * @return The type of the current trade.
     */
    public String getBuySell() { return buySell; }

    /**
     * Set the type of the current trade object.
     *
     * @param type the type of the current trade to set.
     */
    public void setBuySell(String type) { this.buySell = type; }

    /**
     * Get the organisation of the current trade object.
     *
     * @return The organisation of the current trade.
     */
    public String getOrganisation() { return organisation; }

    /**
     * Set the organisation of the current trade object.
     *
     * @param organisation the organisation of the current trade to set.
     */
    public void setOrganisation(String organisation) { this.organisation = organisation; }

    /**
     * Get the asset of the current trade object.
     *
     * @return The asset of the current trade.
     */
    public String getAsset() { return asset; }

    /**
     * Set the asset of the current trade object.
     *
     * @param asset the asset of the current trade to set.
     */
    public void setAsset(String asset) { this.asset = asset; }

    /**
     * Get the quantity of the current trade object.
     *
     * @return The quantity of the current trade.
     */
    public int getQuantity() { return quantity; }

    /**
     * Set the quantity of the current trade object.
     *
     * @param quantity the quantity of the current trade to set.
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Get the price of the current trade object.
     *
     * @return The price of the current trade.
     */
    public int getPrice() { return price; }

    /**
     * Set the price of the current trade object.
     *
     * @param price the price of the current trade to set.
     */
    public void setPrice(int price) { this.price = price; }

    /**
     * Get the date of the current trade object.
     *
     * @return The date of the current trade.
     */
    public Date getDate() { return date; }

    /**
     * Set the date of the current trade object.
     *
     * @param date the date of the current trade to set.
     */
    public void setDate(Date date) { this.date = date; }
}
