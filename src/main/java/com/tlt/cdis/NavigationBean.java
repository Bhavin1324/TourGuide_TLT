
package com.tlt.cdis;

import static com.tlt.constants.JwtConstants.ROLE_GUIDE;
import static com.tlt.constants.JwtConstants.ROLE_TOURIST;
import com.tlt.constants.UrlConstants;
import com.tlt.record.KeepRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "navigationBean")
@SessionScoped
public class NavigationBean implements Serializable {

    String activeRole;
    boolean roleContainsGuide;
    public NavigationBean() {
    }

    public void redirectTo(String destination) {
        try {
            if(destination.equals(UrlConstants.TO_GUIDE)){
                activeRole = ROLE_GUIDE;
            } else if (destination.equals(UrlConstants.TO_TOURIST)){
                activeRole = ROLE_TOURIST;
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect(destination);
        } catch (Exception ex) {
            System.out.println("Exception in redirection from context");
            ex.printStackTrace();
        }
    }

    public void confirm(String navigation) {
        addMessage("Confirmed", "You have accepted");
    }

    public void delete() {
        addMessage("Confirmed", "Record deleted");
    }

    public void logout(String navigation) {
        addMessage("Confirmed", "Logged out");
        redirectTo(navigation);
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getActiveRole() {
        return activeRole;
    }

    public void setActiveRole(String activeRole) {
        this.activeRole = activeRole;
    }

    public boolean isRoleContainsGuide() {
        Set<String> roles = KeepRecord.getRoles();
        roleContainsGuide = roles.contains(ROLE_GUIDE) ? true : false;
        return roleContainsGuide;
    }

    public void setRoleContainsGuide(boolean roleContainsGuide) {
        this.roleContainsGuide = roleContainsGuide;
    }
    
}
