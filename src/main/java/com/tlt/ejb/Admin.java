/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package com.tlt.ejb;

import com.tlt.entities.PlaceCategory;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateful;

/**
 *
 * @author kunal
 */
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
