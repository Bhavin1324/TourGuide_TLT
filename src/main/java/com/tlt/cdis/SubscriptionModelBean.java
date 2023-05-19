/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.SubscriptionModel;
import com.tlt.utils.Utils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.component.outputlabel.OutputLabel;

/**
 *
 * @author kunal
 */
@Named(value = "subsModelBean")
@SessionScoped
public class SubscriptionModelBean implements Serializable {

    @EJB
    AdminLocal ad;
    @EJB
    TouristLocal tb;
    List<SubscriptionModel> pc;
    Collection<SubscriptionModel> allSubscriptionModel;
    SubscriptionModel selectedSubModel;
    SubscriptionModel modelForPayment;
    String subModelid;
    
    Integer currentCost;
    private UIComponent costLabel;

    List<SubscriptionModel> selectedSubModels;

    public SubscriptionModelBean() {
        pc = new ArrayList<>();
        selectedSubModels = new ArrayList<>();
        selectedSubModel = new SubscriptionModel();
        allSubscriptionModel = new ArrayList<>();
        subModelid = "";

    }

    public SubscriptionModel getModelForPayment() {
        return modelForPayment;
    }

    public void setModelForPayment(SubscriptionModel modelForPayment) {
        this.modelForPayment = modelForPayment;
    }

    public void onModalAction(SubscriptionModel subModel) {
        this.currentCost = subModel.getCost();
        System.out.println(this.currentCost);
        PrimeFaces.current().executeScript("PF('paymentModal').show()");
        PrimeFaces.current().ajax().update(":dialog:payment-modal");
    }

    public Collection<SubscriptionModel> getSubscriptionModel() {
        allSubscriptionModel = ad.getAllSubscriptionModel();
        return allSubscriptionModel;
    }

    public void setSubscriptionModel(Collection<SubscriptionModel> allSubscriptionModel) {
        this.allSubscriptionModel = allSubscriptionModel;
    }

    public String getSubModelid() {
        return subModelid;
    }

    public void setSubModelid(String subModelid) {
        this.subModelid = subModelid;
    }

    public SubscriptionModel getSelectedSubModel() {
        return selectedSubModel;
    }

    public void setSelectedSubModel(SubscriptionModel selectedSubModel) {
        this.selectedSubModel = selectedSubModel;
    }

    public List<SubscriptionModel> getPc() {
        return (List<SubscriptionModel>) ad.getAllSubscriptionModel();
    }

    public void setPc(List<SubscriptionModel> pc) {
        this.pc = pc;
    }

    public List<SubscriptionModel> getSelectedSubModels() {
        return selectedSubModels;
    }

    public void setSelectedSubModels(List<SubscriptionModel> selectedSubModels) {
        this.selectedSubModels = selectedSubModels;
    }

    public void openNew() {
        this.selectedSubModel = new SubscriptionModel();
    }

    public Integer getCurrentCost() {
        System.out.println("Current cost getter: " + currentCost);
        return currentCost;
    }

    public void setCurrentCost(Integer currentCost) {
        System.out.println("Current cost setter: " + currentCost);
        this.currentCost = currentCost;
    }

    public void saveSubModel() {
        if (this.selectedSubModel.getId() == null) {
            this.selectedSubModel.setId(Utils.getUUID());
            this.pc.add(this.selectedSubModel);
            ad.insertSubscriptionModel(selectedSubModel);
            this.selectedSubModel = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription Model Added"));
        } else {
//            this.pc.add(this.selectedSubModel);
            ad.updateSubscriptionModel(selectedSubModel.getId(), selectedSubModel);
            this.selectedSubModel = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription Model Updated"));
        }
        PrimeFaces.current().executeScript("PF('manageSubModelDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-submodel");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSubModels()) {
            int size = this.selectedSubModels.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }

    public boolean hasSelectedSubModels() {
        return this.selectedSubModels != null && !this.selectedSubModels.isEmpty();
    }

    public void deleteSubModel() {
        try {
            ad.deleteSubscriptionModel(subModelid);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription Model Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-submodel");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Subscription Model"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-submodel");
        }
    }

    public void deleteSelectedSubModels() {
        try {
            for (SubscriptionModel p : selectedSubModels) {
                ad.deleteSubscriptionModel(p.getId());
            }
            selectedSubModels = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription Model Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-submodel");
            PrimeFaces.current().executeScript("PF('dtSubModel').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Subscription Model"));
            PrimeFaces.current().executeScript("PF('dtSubModel').clearFilters()");
        }
    }

    public void Subscribe() {

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uname = req.getSession().getAttribute("username").toString();
        System.out.println(uname);
        System.out.println(this.modelForPayment.getName());
        tb.subscribeToPlan(this.modelForPayment, uname);
        PrimeFaces.current().executeScript("PF('success_dlg').show()");
        PrimeFaces.current().executeScript("PF('paymentModal').hide()");
    }
}
