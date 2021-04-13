/**
 * This class contains the user permissions (Admin mostly) object and methods.
 *
 * @author Laku Jackson
 */

public class UserPermissions {

    //username
    public String username;

    //Add new users and assign them passwords and to an organisational unit
    public boolean newUser = false;

    //Create new organisational units
    public boolean newOrg = false;

    //Edit the number of credits they have
    public boolean editCredits = false;

    //Edit the number of each asset they have and so forth
    public boolean editAsset = false;

    //Add new asset type to the database
    public boolean newAsset = false;

    //Assign user with same level access (new IT Administration member)
    public boolean newAdmin = false;

    public UserPermissions(String username,
                           boolean newUser,
                           boolean newAdmin,
                           boolean newOrg,
                           boolean newAsset,
                           boolean editAsset,
                           boolean editCredits) {
        this.username = username;
        this.newUser = newUser;
        this.newAdmin = newAdmin;
        this.newOrg = newOrg;
        this.newAsset = newAsset;
        this.editAsset = editAsset;
        this.editCredits = editCredits;

    }

    //-------------------------------- Getters and Setters section -----------------------------------------------------

    //return string of the username
    public String getUsername(){return username;}
    public Boolean getNewUser(){return newUser;}
    public Boolean getNewAdmin(){return newAdmin;}
    public Boolean getNewOrg(){return newOrg;}
    public Boolean getNewAsset(){return newAsset;}
    public Boolean getEditAsset(){return editAsset;}
    public Boolean getEditCredits(){return editCredits;}


//    public Boolean setNewUser(boolean update){ return newUser = update;}
//    public Boolean setNewUser(boolean update){ return newUser = update;}





}
