import common.AssetTypes;
import common.Organisation;
import common.User;
import common.sql.user.JDBCUserDataSource;
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
        User user = new User("Dylan", "1234", "member", "amazon");
        //jdbcUserDataSource.addUser(user);
        //jdbcUserDataSource.deleteUser("Dylan");

        //User userGet = jdbcUserDataSource.getUser("Dylan");
        //int size = jdbcUserDataSource.getSize();
        //System.out.println(userGet.username + " " + userGet.password + " " + userGet.accountType + " " + userGet.org +" \nTable size: " + size);
    }
}
