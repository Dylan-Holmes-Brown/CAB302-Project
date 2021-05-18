package common;

/**
 * This class initialises a user
 *
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class User extends Object{

    //may change these to private variables in future
    public int id;
    public String username;
    public String password;
    public String accountType;
    public String org;

    //Empty constructor to create User Object
    public User(){

    }

    //User Constructor
    public User(String username, String password, String accountType, String org){
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.org = org;
    }

    //User Constructor with their organization
    public User(int id, String username, String password, String salt, String org){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = salt;
        this.org = org;
    }

    /**
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * @param username the username to set
     */
    public void setName(String username) { this.username = username; }

    /**
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * @return the accountType
     */
    public String getAccountType() { return accountType; }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) { this.accountType = accountType; }

    /**
     * @return the org
     */
    public String getOrganisationalUnit() { return org; }

    /**
     * @param org the org to set
     */
    public void setOrganisationalUnit(String org) { this.org = org; }
}
