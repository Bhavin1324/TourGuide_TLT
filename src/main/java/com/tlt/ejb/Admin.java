
package com.tlt.ejb;

import com.tlt.entities.PlaceCategory;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateful;

@Stateful
public class Admin implements AdminLocal {

    @Override
    public void insertPlaceCategory() {
        System.out.println("Hello world!!!");
    }

    @Override
    public Collection<PlaceCategory> getAllPlaceCategories() {
        return new ArrayList<PlaceCategory>();
    }

}
