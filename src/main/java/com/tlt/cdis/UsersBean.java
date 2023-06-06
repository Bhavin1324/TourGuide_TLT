/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.EventMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.utils.Utils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "usersBean")
@SessionScoped
public class UsersBean implements Serializable {

    @EJB
    AdminLocal ad;
    Collection<UserMaster> users;
    String userid;
    UserMaster selectedUser;
    List<UserMaster> selectedUsers;
    String role;
    Collection<GuideMaster> allGuides;
    int activeTabIndex;
    Collection<AppointmentMaster> allAppointmentsOfGuides;
    Collection<EventMaster> allEventsOfGuides;

    public UsersBean() {
        users = new ArrayList<>();
        selectedUsers = new ArrayList<>();
        userid = "";
        role = "";
        selectedUser = new UserMaster();

    }

    @PostConstruct
    public void init() {
        users = ad.getAllUsers();
    }

    public int getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(int activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public String getFormatedDate(Date date) {
        return Utils.getDateTimeFormat(date);
    }

    public String getFormatedTime(Date date) {
        return Utils.getTime12h(date);
    }

    public Collection<AppointmentMaster> getAllAppointmentsOfGuides() {
        return ad.getAppointmentsOfAllGuides();
    }

    public void setAllAppointmentsOfGuides(Collection<AppointmentMaster> allAppointmentsOfGuides) {
        this.allAppointmentsOfGuides = allAppointmentsOfGuides;
    }

    public Collection<EventMaster> getAllEventsOfGuides() {
        return ad.getEventsOfAllGuides();
    }

    public void setAllEventsOfGuides(Collection<EventMaster> allEventsOfGuides) {
        this.allEventsOfGuides = allEventsOfGuides;
    }

    public Collection<GuideMaster> getAllGuides() {
        return ad.getAllGuides();
    }

    public void setAllGuides(Collection<GuideMaster> allGuides) {
        this.allGuides = allGuides;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<UserMaster> getUsers() {
        return users;
    }

    public void setUsers(List<UserMaster> users) {
        this.users = users;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        System.out.println("UserID: " + userid);
        this.userid = userid;
    }

    public UserMaster getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserMaster selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserMaster> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<UserMaster> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public void openNew() {
        this.selectedUser = new UserMaster();
    }

    public void saveUser() {
        if (this.selectedUser.getId() == null) {
            this.selectedUser.setId(Utils.getUUID());
            ad.insertUser(selectedUser);
            this.selectedUser = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Added"));
        }
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }

    public boolean hasSelectedUsers() {
        return this.selectedUsers != null && !this.selectedUsers.isEmpty();
    }

    public void deleteUser() {
        try {
            ad.deleteUser(userid);
            this.users = ad.getAllUsers();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting User"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
        }
    }

    public void deleteSelectedUsers() {
        try {
            for (UserMaster p : selectedUsers) {
                ad.deleteUser(p.getId());
            }
            selectedUsers = null;
            this.users = ad.getAllUsers();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
            PrimeFaces.current().executeScript("PF('dtUser').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting User"));
            PrimeFaces.current().executeScript("PF('dtUser').clearFilters()");
        }
    }

    public void roleChangeListener(ValueChangeEvent e) {
        this.role = String.valueOf(e.getNewValue());
        if (!this.role.equals("notSelected")) {
//            for (UserRole r : ad.getAllRoles()) {
//                if (r.getUserRolePK().getRole().equals(this.role)) {
            this.users = null;
            this.users = ad.getUsersByRoles(this.role);
//            return;
////                } else {
////                    this.users = null;
////                }
//            }
        } else {
            this.users = ad.getAllUsers();
        }
    }

}
