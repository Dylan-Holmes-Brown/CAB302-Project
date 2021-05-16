import common.AssetTypes;
import common.Organisation;
import common.User;
import sql.user.JDBCUserDataSource;
import sql.asset_type.JDBCAssetTypeDataSource;
import sql.organisation.JDBCOrganisationDataSource;


/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JDBCUserDataSource jdbcUserDataSource = new JDBCUserDataSource();
        JDBCOrganisationDataSource jdbcOrganisationDataSource = new JDBCOrganisationDataSource();
        JDBCAssetTypeDataSource jdbcAssetTypeDataSource = new JDBCAssetTypeDataSource();


        // Test User table
        User user = new User("Dylan", "1234", "member", "amazon");
        //jdbcUserDataSource.addUser(user);
        //jdbcUserDataSource.deleteUser("Dylan");

        // Test asset type table
        AssetTypes asset = new AssetTypes("CPU");
        //jdbcAssetTypeDataSource.addAssetType(asset);

        // Test Organisation table
        Organisation org = new Organisation("amazon", 100, "CPU", 20);
        //jdbcOrganisationDataSource.addOrg(org);
        //jdbcOrganisationDataSource.deleteOrg("amazon");
    }
}
