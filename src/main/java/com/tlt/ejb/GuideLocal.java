/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.tlt.ejb;

import com.tlt.entities.GuideMaster;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author kunal
 */
@Local
public interface GuideLocal {

    //appointments
    Collection<GuideMaster> getAllAppointments();

    Collection<GuideMaster> getAllAppointmentsByUserame(String Username);
    
    void updateAppointmentStatus();

}
