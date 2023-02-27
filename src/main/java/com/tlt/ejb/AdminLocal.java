
package com.tlt.ejb;

import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import java.util.Collection;
import javax.ejb.Local;


@Local
public interface AdminLocal {
   void insertPlace(PlaceMaster data);
   void deletePlace(String id);
   void updatePlace(String id, PlaceMaster newPlaceData);
   Collection<PlaceMaster> getAllPlaces();
   PlaceMaster getPlaceById(String id);
}
