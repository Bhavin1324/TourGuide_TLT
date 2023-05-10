package com.tlt.cdis;

import static com.tlt.constants.JwtConstants.ROLE_TOURIST;
import static com.tlt.constants.PathConstants.DEFAULT_USER_IMG;
import static com.tlt.constants.PathConstants.PROFILE_IMG_UPLOAD;
import static com.tlt.constants.UrlConstants.TO_LOGIN;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;
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
    String filePath;
    String fileName;

    public RegisterUserBean() {
        userMaster = new UserMaster();
        passHash = new Pbkdf2PasswordHashImpl();
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean upload() {
        try ( InputStream input = file.getInputStream()){
            if (file == null) {
                return false;
            }
            String directory = PROFILE_IMG_UPLOAD;
            this.fileName = System.currentTimeMillis() + "_" + file.getFileName();
            this.filePath = directory + fileName;

            try ( OutputStream output = new FileOutputStream(this.filePath)) {
                byte[] buffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void register() throws IOException {
        boolean uploadStatus = upload();
        if (!uploadStatus) {
            this.filePath = DEFAULT_USER_IMG;
        }

        if (!userMaster.getPassword().equals(confirmPass)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password: Confirm password must match with entered password", ""));
        } else {

            if (tejb.findUserByUsername(userMaster.getUsername()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username: This username is already taken. Try to make new.", "Create new username which is unique"));
            } else {
                userMaster.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20));
                userMaster.setContact(Long.parseLong(contactNumber.replaceAll(" ", "")));
                userMaster.setProfileImage(fileName);
                
                
                String nonHashPass = userMaster.getPassword();
                String hashedPassword = passHash.generate(nonHashPass.toCharArray());
                userMaster.setPassword(hashedPassword);
                tejb.insertUser(userMaster);
                UserRole role = new UserRole(userMaster.getUsername(), ROLE_TOURIST);
                role.setUserMaster(userMaster);
                aejb.addUserRole(role);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User has been registerd successfully"));
                userMaster = new UserMaster();
                confirmPass = "";
                contactNumber = "";
                FacesContext.getCurrentInstance().getExternalContext().redirect(TO_LOGIN);
            }
        }
    }

    

}
