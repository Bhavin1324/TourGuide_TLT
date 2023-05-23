package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.UserMaster;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class Guide implements GuideLocal {

    @PersistenceContext(unitName = "TLT_Persistance")
    EntityManager em;

    @Override
    public Collection<AppointmentMaster> getAllAppointments() {
        Collection<AppointmentMaster> appointments = em.createNamedQuery("AppointmentMaster.findAll").getResultList();
        return appointments;
    }

    @Override
    public Collection<AppointmentMaster> getAllAppointmentsByGuide(String Username) {
        UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", Username).getSingleResult();
        GuideMaster guide = em.find(GuideMaster.class,user.getId());
        return guide.getAppointmentMasterCollection();
    }

    @Override
    public Collection<AppointmentMaster> getAppointmentsOfGuide(String gusername,String status) {
         UserMaster guide = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
//         GuideMaster gm = em.find(GuideMaster.class, guide.getId());
         
        Collection<AppointmentMaster> appointments = em.createNamedQuery("AppointmentMaster.getPendingAppointments").setParameter("gid", guide.getId()).setParameter("status", status).getResultList();
        return appointments;
    }

}
