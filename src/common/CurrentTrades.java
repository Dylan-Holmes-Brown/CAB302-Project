package common;
import java.sql.Date;
/**
 *
 *
 * @author Dylan Holmes-Brown
 */

public class CurrentTrades {
    private static final long serialVersionUID = 40L;
    private int id;
    private String buySell;
    private String organisation;
    private String asset;
    private int quantity;
    private int price;
    private Date date;

    public CurrentTrades(){}

    public CurrentTrades(String buySell, String org, String asset, int quantity, int price, Date date) {
        this.buySell = buySell;
        this.organisation = org;
        this.asset = asset;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public Integer getID() { return id; }

    public String getBuySell() { return buySell; }

    public void setBuySell(String buySell) { this.buySell = buySell; }

    public String getOrganisation() { return organisation; }

    public void setOrganisation(String organisation) { this.organisation = organisation; }

    public String getAsset() { return asset; }

    public void setAsset(String asset) { this.asset = asset; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
