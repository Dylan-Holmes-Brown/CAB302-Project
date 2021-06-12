package common;

import java.io.Serializable;

/**
 * This class initialises a user
 *
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class User extends Object implements Serializable {

    // User Fields
    private static final long serialVersionUID = 10L;
    private String username;
    private String password;
    private String accountType;
    private String org;

    /**
     * Empty constructor to create User Object
     */
    public User(){

    }

    /**
     * Member User Constructor
     *
     * @param username The username of the member
     * @param password The password of the member
     * @param accountType The account type of the user
     * @param org The organisation the user is apart of
     */
    public User(String username, String password, String accountType, String org){
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.org = org;
    }

    /**
     * Admin User Constructor
     *
     * @param username The username of the admin
     * @param password The password of the admin
     * @param accountType The account type of the user
     */
    public User(String username, String password, String accountType){
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * Get the username of the user object.
     *
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * Set the username of the user object.
     *
     * @param username the username to set
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Get the password of the user object.
     *
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Set the password of the user object.
     *
     * @param password the password to set
     */
    public void setPassword(String password) { this.password = password; }

//    /**
//     * @param password the password is hashed and set
//     */
//    public void setHashedPassword(String password) { this.password = HashPassword.toHex(HashPassword.getHashSHA512(password)); }

    /**
     * Get the account type of the user object.
     *
     * @return the accountType
     */
    public String getAccountType() { return accountType; }

    /**
     * Set the account type of the user object.
     *
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) { this.accountType = accountType; }

    /**
     * Get the organisational unit of the user object.
     *
     * @return the org
     */
    public String getOrganisationalUnit() { return org; }

    /**
     * Set the organisational unit of the user object.
     *
     * @param org the org to set
     */
    public void setOrganisationalUnit(String org) { this.org = org; }

    /**
     * @see Object#toString()
     */
    public String toString() { return username + ", " + password + ", " + accountType + ", " + org; }
}
