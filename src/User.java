/**
 * This class initialises a user
 *
 * @author Laku Jackson
 */

public class User extends Object{

    //may change these to private variables in future
    public String username;
    public String password;
    public String salt;
    public String org;
    public int id;

    //Empty constructor to create User Object
    public User(){

    }

    //User Constructor
    public User(String username, String password, String salt){
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    //User Constructor with their organization
    public User(int id, String username, String password, String salt, String org){
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.org = org;
    }


}
