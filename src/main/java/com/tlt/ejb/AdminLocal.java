package com.tlt.ejb;

import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface AdminLocal {
    //-------------places crud-------

    void insertPlace(PlaceMaster data);

    void deletePlace(String id);

    void updatePlace(String id, PlaceMaster newPlaceData);

    Collection<PlaceMaster> getAllPlaces();

    PlaceMaster getPlaceById(String id);

    Collection<PlaceMaster> getPlacesByCategory(PlaceCategory categoryId);

    PlaceMaster getPlacesByName(String Name);
    
    //-------------Guide crud--------

    void insertGuide(GuideMaster data);

    void deleteGuide(String id);

    void updateGuide(String id, GuideMaster newGuideData);
    
    GuideMaster getGuideById(String id);

    Collection<GuideMaster> getAllGuides();
    
    Collection<GuideMaster> getGuideByPlace(String PlaceId);

    
}
