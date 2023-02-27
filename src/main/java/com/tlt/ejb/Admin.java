
package com.tlt.ejb;

import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class Admin implements AdminLocal {
    @PersistenceContext(unitName = "TLT_Persistance")
    EntityManager em;
    
    @Override
    public void insertPlace(PlaceMaster data) {
        if(data == null)
            return;
        em.persist(data);
    }

    @Override
    public void deletePlace(String id) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, id);
        em.remove(place);
    }

    @Override
    public void updatePlace(String id, PlaceMaster newPlaceData) {
       PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, id);
       if(place != null){
           em.merge(newPlaceData);
       }
    }

    @Override
    public Collection<PlaceMaster> getAllPlaces() {
       Collection<PlaceMaster> places = em.createNamedQuery("PlaceMaster.findAll").getResultList();
       return places;
    }

    @Override
    public PlaceMaster getPlaceById(String id) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, id);
       return place;
    }
        

}
