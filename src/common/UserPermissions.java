package common;

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

    //Empty constructor to create UserPermissions Object
    public UserPermissions(){

    }

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
    public String getUsername(){
        return username;
    }

    public Boolean getNewUser(){
        return newUser;
    }

    public Boolean getNewAdmin(){
        return newAdmin;
    }

    public Boolean getNewOrg(){
        return newOrg;
    }

    public Boolean getNewAsset(){
        return newAsset;
    }

    public Boolean getEditAsset(){
        return editAsset;
    }

    public Boolean getEditCredits(){
        return editCredits;
    }

    //returns the updated state of setNewUser
    public void setNewUser(Boolean update){
        newUser = update;
    }

    //returns the updated state of setNewAdmin
    public void setNewAdmin(Boolean update){
        newAdmin = update;
    }

    //returns the updated state of setNewOrg
    public void setNewOrg(Boolean update){
        newOrg = update;
    }

    //returns the updated state of setNewAsset
    public void setNewAsset(Boolean update){
        newAsset = update;
    }

    //returns the updated state of setEditAsset
    public void setEditAsset(Boolean update){
        editAsset = update;
    }

    //returns the updated state of setEditCredits
    public void setEditCredits(Boolean update){
        editCredits = update;
    }

    //

}
