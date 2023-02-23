
package com.tlt.ejb;

import com.tlt.entities.PlaceCategory;
import java.util.Collection;
import javax.ejb.Local;


@Local
public interface AdminLocal {
    void insertPlaceCategory();
    Collection<PlaceCategory> getAllPlaceCategories();
}
