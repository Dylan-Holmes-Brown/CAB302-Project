import common.AssetTypes;
import common.User;
import sql.JDBCAssetTypeDataSource;
import sql.JDBCUserDataSource;


/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    public static void main(String[] args) throws Exception{

        //JDBCUserDataSource jdbcUserDataSource = new JDBCUserDataSource();
        //User user = new User("dylan", "1234", "member", "org");
        //jdbcUserDataSource.addUser(user);
        //jdbcUserDataSource.deleteUser("dylan");


        JDBCAssetTypeDataSource jdbcAssetTypeDataSource = new JDBCAssetTypeDataSource();
        AssetTypes asset = new AssetTypes("CPU");
        jdbcAssetTypeDataSource.addAssetType(asset);
    }
}
