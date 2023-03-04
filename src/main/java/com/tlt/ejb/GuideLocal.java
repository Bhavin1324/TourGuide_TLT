
package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import java.util.Collection;
import javax.ejb.Local;


@Local
public interface GuideLocal {

    //appointments
    Collection<AppointmentMaster> getAllAppointments();

    Collection<AppointmentMaster> getAllAppointmentsByUserame(String Username);
    
}
