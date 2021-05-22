import common.Organisation;
import common.User;
import common.sql.user.JDBCUserDataSource;
import server.JDBCAssetTypeDataSource;
import common.sql.organisation.JDBCOrganisationDataSource;


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
        //AssetTypes asset = new AssetTypes("CPU");
        //jdbcAssetTypeDataSource.addAssetType(asset);
        //jdbcAssetTypeDataSource.deleteAssetType("CPU");


        // Test Organisation table
        Organisation org = new Organisation("amazon", 100, "CPU", 20);
        //jdbcOrganisationDataSource.addOrg(org);
        //jdbcOrganisationDataSource.deleteOrg("amazon");

        // Test User table
        User user = new User("Dylan", "1234", "member", "amazon");
        //jdbcUserDataSource.addUser(user);
        //jdbcUserDataSource.deleteUser("Dylan");

        //User userGet = jdbcUserDataSource.getUser("Dylan");
        //int size = jdbcUserDataSource.getSize();
        //System.out.println(userGet.username + " " + userGet.password + " " + userGet.accountType + " " + userGet.org +" \nTable size: " + size);
    }
}
