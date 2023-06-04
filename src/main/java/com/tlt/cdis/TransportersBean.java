/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.Cities;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.TransporterMaster;
import com.tlt.utils.Utils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "transportersBean")
@SessionScoped
public class TransportersBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    TransporterMaster selectedTransporter;
    Collection<TransporterMaster> allTransporters;
    List<TransporterMaster> selectedTransporters;
    String cityTxt;
    String transporterId;

    public TransportersBean() {
        selectedTransporter = new TransporterMaster();
        selectedTransporters = new ArrayList<>();
        allTransporters = new ArrayList<>();
        cityTxt = "";
    }

    public void openNew() {
        cityTxt="";
        selectedTransporter = new TransporterMaster();
    }

    public String getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(String transporterId) {
        this.transporterId = transporterId;
    }

    public String getCityTxt() {
        return cityTxt;
    }

    public void setCityTxt(String cityTxt) {
        this.cityTxt = cityTxt;
    }

    public List<TransporterMaster> getSelectedTransporters() {
        return selectedTransporters;
    }

    public void setSelectedTransporters(List<TransporterMaster> selectedTransporters) {
        this.selectedTransporters = selectedTransporters;
    }

    public TransporterMaster getSelectedTransporter() {
        return selectedTransporter;
    }

    public void setSelectedTransporter(TransporterMaster selectedTransporter) {
        this.selectedTransporter = selectedTransporter;
    }

    public Collection<TransporterMaster> getAllTransporters() {
        allTransporters = adminLogic.getAllTransporters();
        return allTransporters;
    }

    public void setAllTransporters(Collection<TransporterMaster> allTransporters) {
        this.allTransporters = allTransporters;
    }

    public void saveTransporter() {
        Cities city = adminLogic.getCityOfStateByName(4030, cityTxt);
        selectedTransporter.setCityId(city);
        if (this.selectedTransporter.getId() == null) {
            this.selectedTransporter.setId(Utils.getUUID());
            adminLogic.addTransporter(selectedTransporter);
            this.selectedTransporter = null;
            cityTxt="";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transporter Added"));
        } else {
            adminLogic.updateTransporter(selectedTransporter.getId(), selectedTransporter);
            this.selectedTransporter = null;
            cityTxt = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transporter Updated"));
        }
        PrimeFaces.current().executeScript("PF('manageTransporterDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "dt-transporter");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedTransporters()) {
            int size = this.selectedTransporters.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }
    public void selectTransporter(){
        cityTxt = selectedTransporter.getCityId().getName();
    }
    public boolean hasSelectedTransporters() {
        return this.selectedTransporter != null && !this.selectedTransporters.isEmpty();
    }

    public void deleteTransporter() {
        try {
            adminLogic.removeTransporter(transporterId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transporter Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-transporter");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Transporter"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-transporter");
        }
    }

    public void deleteSelectedTransporters() {
        try {
            for (TransporterMaster t : selectedTransporters) {
                adminLogic.removeTransporter(t.getId());
            }
            selectedTransporter = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transporters Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-transporter");
            PrimeFaces.current().executeScript("PF('dtTransporter').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Transporters"));
            PrimeFaces.current().executeScript("PF('dtTransporter').clearFilters()");
        }
    }
}
