package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.UserMaster;
import com.tlt.utils.GraphUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        GuideMaster guide = em.find(GuideMaster.class, user.getId());
        return guide.getAppointmentMasterCollection();
    }

    @Override
    public Collection<AppointmentMaster> getAppointmentsOfGuide(String gusername, String status) {
        UserMaster guide = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
//         GuideMaster gm = em.find(GuideMaster.class, guide.getId());

        Collection<AppointmentMaster> appointments = em.createNamedQuery("AppointmentMaster.getPendingAppointments").setParameter("gid", guide.getId()).setParameter("status", status).getResultList();
        return appointments;
    }

    @Override
    public List<GraphUtils> getMonthlyAppointmentsCount(String gusername) {
        UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        GuideMaster guide = em.find(GuideMaster.class, user.getId());
        List<Object[]> list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(a.start_datetime), COUNT(*) as 'appointmentCounts' FROM `appointment_master` a WHERE a.guide_id = '" + guide.getId() + "' GROUP BY MONTHNAME(a.start_datetime) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(a.start_datetime), ' 01'), '%Y %M %d') ASC;").getResultList();
        List<GraphUtils> graphData = new ArrayList<>();
        for (Object[] obj : list) {
            GraphUtils gd = new GraphUtils();
            gd.setMonth(obj[0].toString());
            gd.setCount(Long.parseLong(obj[1].toString()));
            graphData.add(gd);
        }
        return graphData;
    }

    @Override
    public List<GraphUtils> getMonthlyRevenueOfGuide(String gusername) {
         UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        GuideMaster guide = em.find(GuideMaster.class, user.getId());
        List<Object[]> list = new ArrayList<>();
        list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(a.start_datetime), SUM(g.amount) as 'appointmentCounts' FROM `appointment_master` a JOIN guide_master g on a.guide_id = g.id  WHERE guide_id = '" + guide.getId() +"' GROUP BY MONTHNAME(a.start_datetime) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(a.start_datetime), ' 01'), '%Y %M %d') ASC;").getResultList();

        List<GraphUtils> graphData = new ArrayList<>();
        for (Object[] g : list) {
            GraphUtils gd = new GraphUtils();
            gd.setMonth(g[0].toString());
            gd.setCount(Long.parseLong(g[1].toString()));
            graphData.add(gd);
        }
        return graphData;
    }

}
