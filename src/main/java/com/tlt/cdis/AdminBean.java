/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.PlaceCategory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    @EJB
    AdminLocal ad;
    List<PlaceCategory> pc;
    PlaceCategory selectedCategory;
    String catid;
    List<PlaceCategory> selectedCategories;

    /**
     * Creates a new instance of AdminBean
     */
    public AdminBean() {
        pc = new ArrayList<>();
        selectedCategories = new ArrayList<>();
        selectedCategory = new PlaceCategory();
        catid = "";
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public PlaceCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(PlaceCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<PlaceCategory> getPc() {
        return (List<PlaceCategory>) ad.getAllPlaceCategories();
    }

    public void setPc(List<PlaceCategory> pc) {
        this.pc = pc;
    }

    public List<PlaceCategory> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<PlaceCategory> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public void openNew() {
        this.selectedCategory = new PlaceCategory();
    }

    public void saveProduct() {
        if (this.selectedCategory.getId() == null) {
            this.selectedCategory.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.pc.add(this.selectedCategory);
            ad.insertPlaceCategory(selectedCategory);
            this.selectedCategory = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category Added"));
        } else {
//            this.pc.add(this.selectedCategory);
            ad.updatePlaceCategory(selectedCategory.getId(), selectedCategory);
            this.selectedCategory = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public void updateProduct() {
        if (this.selectedCategory.getId() != null) {
//            this.pc.add(this.selectedCategory);
            ad.updatePlaceCategory(selectedCategory.getId(), selectedCategory);
//            ad.insertPlaceCategory(selectedCategory);
            this.selectedCategory = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category Added"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selectedCategories.size();
            return size > 1 ? size + " selected" : "1 selected";
        }

        return "Delete";
    }

    public boolean hasSelectedProducts() {
        return this.selectedCategories != null && !this.selectedCategories.isEmpty();
    }

    public void deleteProduct() {
        System.out.println("=>>>>>>>>>>> " + this.catid);
        ad.deletePlaceCategory(catid);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public void deleteSelectedProducts() {
//        this.products.removeAll(this.selectedProducts);
//        this.selectedProducts = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
