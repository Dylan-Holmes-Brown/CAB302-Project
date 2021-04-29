package common;

/**
 * This class initialises an Organization and their assets
 *
 * @author Laku Jackson
 */

public class Organization {
    public String orgName;
    private int credits = 0;
    private int organizationID;


    public Organization() {

    }

    public Organization(int organizationID, int credits, String orgName) {
        this.organizationID = organizationID;
        this.orgName = orgName;
        this.credits = credits;
    }

//    public User createUser(String name){
//        //return new User(id,organizationID)
//    }

    public String getOrgName() {
        return orgName;
    }

    public int getCredits() {
        return credits;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
