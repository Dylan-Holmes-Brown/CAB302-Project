package common;

import java.io.Serializable;


public class UserPermissions implements Serializable {


    public User user;


    public Permissions permissions;

   //tie user and permissions together
    public UserPermissions(User user, Permissions permissions) {
        this.user = user;
        this.permissions = permissions;
    }
}
