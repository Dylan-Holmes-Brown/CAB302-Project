import common.AssetTypes;
import common.CurrentTrades;
import java.sql.Date;
import common.Organisation;
import common.User;
import common.sql.current_trade.JDBCCurrentDataSource;
import server.JDBCUserDataSource;
import server.JDBCAssetTypeDataSource;
import server.JDBCOrganisationDataSource;


/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JDBCAssetTypeDataSource jdbcAssetTypeDataSource = new JDBCAssetTypeDataSource();
        JDBCOrganisationDataSource jdbcOrganisationDataSource = new JDBCOrganisationDataSource();
        JDBCUserDataSource jdbcUserDataSource = new JDBCUserDataSource();
        JDBCCurrentDataSource jdbcCurrentDataSource = new JDBCCurrentDataSource();

        // Test asset type table
        AssetTypes asset = new AssetTypes("CPU");
        //jdbcAssetTypeDataSource.addAssetType(asset);
        //jdbcAssetTypeDataSource.deleteAssetType("CPU");

        //AssetTypes assetGet = jdbcAssetTypeDataSource.getAsset("CPU");
        //System.out.println(assetGet.assetType);


        // Test Organisation table
        Organisation org = new Organisation("amazon", 100, "CPU", 10);
        //jdbcOrganisationDataSource.addOrg(org);
        //jdbcOrganisationDataSource.deleteOrg("amazon");
        //jdbcOrganisationDataSource.addCredits("amazon", 20);
        //jdbcOrganisationDataSource.removeCredits("amazon", 20);
        //jdbcOrganisationDataSource.addQuantity("amazon", "CPU", 10);
        //jdbcOrganisationDataSource.removeQuantity("amazon", "CPU", 10);
        Organisation orgGet = jdbcOrganisationDataSource.getOrg("amazon");
        System.out.println("Organisation:\n" +
                orgGet.name + " " +
                orgGet.credits + " " +
                orgGet.assets + " " +
                orgGet.quantity);

        // Test User table
        User user = new User("Dylan", "1234", "Member", "amazon");
        //jdbcUserDataSource.addUser(user);
        //jdbcUserDataSource.deleteUser("Dylan");
        //jdbcUserDataSource.updatePassword("Dylan", "4321");

        User userGet = jdbcUserDataSource.getUser("Dylan");
        int size = jdbcUserDataSource.getSize();
        System.out.println(userGet.username + " " + userGet.password + " " + userGet.accountType + " " + userGet.org +" \nTable size: " + size);

        // Test Current Trades Table
        long now = System.currentTimeMillis();
        Date sqlDate = new Date(now);
        CurrentTrades trades = new CurrentTrades("Buy", "amazon", "CPU", 10, 10, sqlDate);
        //jdbcCurrentDataSource.addTrade(trades);
        CurrentTrades thisTrade = jdbcCurrentDataSource.getOrgTrade("amazon");
        System.out.println("Current Trades\n " +
                thisTrade.buySell + " " +
                thisTrade.organisation + " " +
                thisTrade.asset + " " +
                thisTrade.quantity + " " +
                thisTrade.price + " " +
                thisTrade.date);
    }
}
