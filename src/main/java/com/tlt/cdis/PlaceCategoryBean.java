
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.PlaceCategory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@Named(value = "placeCatBean")
@SessionScoped
public class PlaceCategoryBean implements Serializable {

    @EJB
    AdminLocal ad;
    List<PlaceCategory> pc;
    PlaceCategory selectedCategory;
    String catid;
    List<PlaceCategory> selectedCategories;

    
    public PlaceCategoryBean() {
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

    public void saveCategory() {
        if (this.selectedCategory.getId() == null) {
            this.selectedCategory.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20));
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
        PrimeFaces.current().executeScript("PF('manageCategoryDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCategories()) {
            int size = this.selectedCategories.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }

    public boolean hasSelectedCategories() {
        return this.selectedCategories != null && !this.selectedCategories.isEmpty();
    }

    public void deleteCategory() {
        try {
            ad.deletePlaceCategory(catid);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Category"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
        }
    }

    public void deleteSelectedCategories() {
        try {
            for (PlaceCategory p : selectedCategories) {
                ad.deletePlaceCategory(p.getId());
            }
            selectedCategories = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
            PrimeFaces.current().executeScript("PF('dtCategory').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Category"));
            PrimeFaces.current().executeScript("PF('dtCategory').clearFilters()");
        }
    }

}
