import client.NetworkDataSource;
import common.AssetType;
import common.Organisation;
import common.Trade;
import common.User;
import server.JDBCAssetTypeDataSource;
import server.JDBCCurrentDataSource;
import server.JDBCOrganisationDataSource;
import server.JDBCUserDataSource;

import java.sql.Date;



/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JDBCAssetTypeDataSource assetData = new JDBCAssetTypeDataSource();
        JDBCOrganisationDataSource organisationData = new JDBCOrganisationDataSource();
        JDBCUserDataSource userData = new JDBCUserDataSource();
        JDBCCurrentDataSource data = new JDBCCurrentDataSource();
        Date date = Date.valueOf("2021-06-08");
        AssetType asset = new AssetType("CPU");
        User user = new User("Adam", "1234", "Member", "Apple");
        Organisation organisation = new Organisation("Apple", 20, "CPU", 10);
        Trade trade = new Trade("Buy", "Apple", "CPU", 4, 20, date);
//        assetData.addAssetType(asset);
//        organisationData.addOrg(organisation);
//        userData.addUser(user);
//        data.addTrade(trade);
//        Organisation org = organisationData.getOrg(1);
//        System.out.println(org.getName());
    }
}
