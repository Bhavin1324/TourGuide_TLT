package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.utils.GraphUtils;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface GuideLocal {

    //appointments
    Collection<AppointmentMaster> getAllAppointments();

    Collection<AppointmentMaster> getAllAppointmentsByGuide(String Username);

    Collection<AppointmentMaster> getAppointmentsOfGuide(String guide, String status);

    List<GraphUtils> getMonthlyAppointmentsCount(String gusername);

    List<GraphUtils> getMonthlyRevenueOfGuide(String gusername);

    void updateAppointmentStatus(AppointmentMaster appointment);

    Collection<PlaceMaster> getAllPlacesOfGuide(String username);

    void removeGuidesPlace(PlaceMaster pm, String username);

    void addGuidesPlace(PlaceMaster pm, String username);
}
