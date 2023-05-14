package com.tlt.cdis;

import static com.tlt.constants.PathConstants.PLACES_IMG_UPLOAD;
import com.tlt.ejb.AdminLocal;
import com.tlt.entities.Cities;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.utils.Utils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

@Named(value = "placesBean")
@SessionScoped
public class PlacesBean implements Serializable {

    @EJB
    AdminLocal ad;
    List<PlaceMaster> pc;
    PlaceMaster selectedPlace;
    String catid, cityid;
    List<PlaceMaster> selectedPlaces;
    Collection<PlaceCategory> placeCategories;
    Collection<Cities> cities;
    private String cityTxt;
    UploadedFile file;
    String fileName;

    public PlacesBean() {
        pc = new ArrayList<>();
        selectedPlaces = new ArrayList<>();
        selectedPlace = new PlaceMaster();
        placeCategories = new ArrayList<>();
        cities = new ArrayList<>();
        catid = "";
        cityid = "";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCityTxt() {
        return cityTxt;
    }

    public void setCityTxt(String cityTxt) {
        this.cityTxt = cityTxt;
    }

    public Collection<Cities> getCities() {
        return ad.getCityByStateId(4030);
    }

    public void setCities(Collection<Cities> cities) {
        this.cities = cities;
    }

    public Collection<PlaceCategory> getPlaceCategories() {
        return ad.getAllPlaceCategories();
    }

    public void setPlaceCategories(Collection<PlaceCategory> placeCategories) {
        this.placeCategories = placeCategories;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public PlaceMaster getSelectedPlace() {
        return selectedPlace;
    }

    public void setSelectedPlace(PlaceMaster selectedPlace) {
        System.out.println(selectedPlace);
        this.selectedPlace = selectedPlace;
    }

    public List<PlaceMaster> getPc() {
        return (List<PlaceMaster>) ad.getAllPlaces();
    }

    public void setPc(List<PlaceMaster> pc) {
        this.pc = pc;
    }

    public List<PlaceMaster> getSelectedPlaces() {
        return selectedPlaces;
    }

    public void setSelectedPlaces(List<PlaceMaster> selectedPlaces) {
        this.selectedPlaces = selectedPlaces;
    }

    public void openNew() {
        this.selectedPlace = new PlaceMaster();
    }

    public void savePlace() {
        try {
            PlaceCategory p = ad.getPlaceCategoryById(catid);
            Cities city = ad.getCityOfStateByName(4030, cityTxt);
            selectedPlace.setCityId(city);
            selectedPlace.setCategoryId(p);
            if (this.selectedPlace.getId() == null) {
                this.selectedPlace.setId(Utils.getUUID());
//                    this.pc.add(this.selectedPlace);
                boolean uploadStatus = Utils.uploadFile_PF(file, Utils.IMAGE, PLACES_IMG_UPLOAD);
                if (!uploadStatus) {
                    if (file != null && !file.getContentType().equalsIgnoreCase("image/png") && !file.getContentType().equalsIgnoreCase("image/jpeg") && !file.getContentType().equalsIgnoreCase("image/jpg")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Image: image should be jpeg | jpg | png only", ""));
                        return;
                    }
//                        this.fileName = DEFAULT_USER_IMG;
                } else {
                    this.fileName = Utils.FileName;
                }
                this.selectedPlace.setImages(this.fileName);
                ad.insertPlace(selectedPlace);
                this.selectedPlace = null;
                cityid = "";
                catid = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Added"));
            } else {
                cityid = "";
                catid = "";
//            this.pc.add(this.selectedPlace);
                ad.updatePlace(selectedPlace.getId(), selectedPlace);
                this.selectedPlace = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Updated"));
            }
            PrimeFaces.current().executeScript("PF('managePlaceDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Enter Valid City!"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
        }
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPlaces()) {
            int size = this.selectedPlaces.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }

    public boolean hasSelectedPlaces() {
        return this.selectedPlaces != null && !this.selectedPlaces.isEmpty();
    }

    public void deletePlace() {
        ad.deletePlace(catid);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
    }

    public void deleteSelectedPlaces() {
        try {
            for (PlaceMaster p : selectedPlaces) {
                ad.deletePlace(p.getId());
            }
            selectedPlaces = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Places Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
            PrimeFaces.current().executeScript("PF('dtPlaces').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Place"));
            PrimeFaces.current().executeScript("PF('dtPlaces').clearFilters()");
        }
    }

    public void selectPlace(PlaceMaster place) {
        catid = place.getCategoryId().getId();
        cityid = place.getCityId().getId().toString();
    }

    public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> cityList = new ArrayList<>();
        List<Cities> city = (List<Cities>) ad.getCityByStateId(4030);
        for (Cities c : city) {
            cityList.add(c.getName());
        }

        return cityList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

}
