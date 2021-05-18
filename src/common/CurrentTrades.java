package common;
import java.sql.Date;
import java.math.BigDecimal;
/**
 *
 *
 * @author Dylan Holmes-Brown
 */

public class CurrentTrades {
    public String buySell;
    public String organisational_unit;
    public String asset;
    public int quantity;
    public BigDecimal price;
    public Date date;

    public CurrentTrades(String buySell, String org, String asset, int quantity, BigDecimal price, Date date) {
        this.buySell = buySell;
        this.organisational_unit = org;
        this.asset = asset;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
}
