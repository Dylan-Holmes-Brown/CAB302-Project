package common;

/**
 * This class initialises a user
 *
 * @author Laku Jackson
 */

public class User extends Object{

    //may change these to private variables in future
    private String username;
    private String password;
    private String salt;
    private int userid;
    private int organizationID;

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
    public User(int userid, int organizationID, String username, String password, String salt){
        this.userid = userid;
        this.organizationID = organizationID;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public int getId() {
        return userid;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int userid) {
        this.userid = userid;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
