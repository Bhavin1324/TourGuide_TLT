package com.tlt.cdis;

import static com.tlt.constants.JwtConstants.ROLE_TOURIST;
import static com.tlt.constants.PathConstants.DEFAULT_USER_IMG;
import static com.tlt.constants.PathConstants.PROFILE_IMG_DEST;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.utils.Utils;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

@Named(value = "registerUserBean")
@SessionScoped
public class RegisterUserBean implements Serializable {

    Pbkdf2PasswordHashImpl passHash;
    @EJB
    TouristLocal tejb;
    @EJB
    AdminLocal aejb;
    UserMaster userMaster;

    String confirmPass;
    String contactNumber;

    UploadedFile file;
    String fileName;
    
    boolean showDialog;

    public RegisterUserBean() {
        userMaster = new UserMaster();
        passHash = new Pbkdf2PasswordHashImpl();
        showDialog = false;
    }

    public UserMaster getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(UserMaster userMaster) {
        this.userMaster = userMaster;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    
    
    public void register() throws IOException {

        boolean uploadStatus = Utils.uploadFile_PF(file, Utils.IMAGE, PROFILE_IMG_DEST);
        if (!uploadStatus) {
            if (file != null && !file.getContentType().equalsIgnoreCase("image/png") && !file.getContentType().equalsIgnoreCase("image/jpeg") && !file.getContentType().equalsIgnoreCase("image/jpg")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Image: image should be jpeg | jpg | png only", ""));
                return;
            }
            this.fileName = DEFAULT_USER_IMG;
        } else {
            this.fileName = Utils.FileName;
        }

        if (tejb.findUserByUsername(userMaster.getUsername()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username: This username is already taken. Try to make new.", "Create new username which is unique"));
            return;
        }
        if (!userMaster.getPassword().equals(confirmPass)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password: Confirm password must match with entered password", ""));
            return;
        }

        userMaster.setId(Utils.getUUID());
        userMaster.setContact(Long.parseLong(contactNumber.replaceAll(" ", "")));
        userMaster.setProfileImage(this.fileName);

        String hashedPassword = Utils.generateHash(userMaster.getPassword().toString());
        userMaster.setPassword(hashedPassword);
        tejb.insertUser(userMaster);
        UserRole role = new UserRole(userMaster.getUsername(), ROLE_TOURIST);
        role.setUserMaster(userMaster);
        aejb.addUserRole(role);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User has been registerd successfully"));
        userMaster = new UserMaster();
        confirmPass = "";
        contactNumber = "";
        Utils.resetFilesCache();
        PrimeFaces.current().executeScript("PF('success_dlg').show()");
    }
}
