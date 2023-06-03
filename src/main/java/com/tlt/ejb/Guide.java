package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
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
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
        Collection<AppointmentMaster> appts = em.createNamedQuery("AppointmentMaster.getAppointmentsOfGuide").setParameter("gid", guide.getId()).getResultList();
        return appts;
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
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
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
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
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

    @Override
    public void updateAppointmentStatus(AppointmentMaster appointment) {
        AppointmentMaster appt = em.find(AppointmentMaster.class,appointment.getId());
        appt.setAppointmentStatus("Complete");
        em.merge(appt);
        
        
    }

    @Override
    public Collection<PlaceMaster> getAllPlacesOfGuide(String username) {
      UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", username).getSingleResult();
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
        Collection<PlaceMaster> guidesPlaces = new ArrayList<>();
        guidesPlaces = guide.getPlaceMasterCollection();
        return guidesPlaces;
    }

    @Override
    public void removeGuidesPlace(PlaceMaster pm, String username) {
         PlaceMaster placeMaster = em.find(PlaceMaster.class, pm.getId());
         UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", username).getSingleResult();
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
        Collection<PlaceMaster> guidesPlaces = new ArrayList<>();
        
        //removing placemaster from guide's placeColl
        guidesPlaces = guide.getPlaceMasterCollection();
        guidesPlaces.remove(placeMaster);
        guide.setPlaceMasterCollection(guidesPlaces);
        
        //removing guide from Placemaster's guideColl
        Collection<GuideMaster> guideColl = placeMaster.getGuideMasterCollection();
        guideColl.remove(guide);
        placeMaster.setGuideMasterCollection(guideColl);
        
        em.merge(placeMaster);
        em.merge(guide);
    }

    @Override
    public void addGuidesPlace(PlaceMaster pm, String username) {
        PlaceMaster placeMaster = em.find(PlaceMaster.class, pm.getId());
         UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", username).getSingleResult();
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
        Collection<PlaceMaster> guidesPlaces = new ArrayList<>();
        
        //adding placemaster to guide's placeColl
        guidesPlaces = guide.getPlaceMasterCollection();
        guidesPlaces.add(placeMaster);
        guide.setPlaceMasterCollection(guidesPlaces);
        
        //adding guide to placemaster's guideColl
        Collection<GuideMaster> guideColl = placeMaster.getGuideMasterCollection();
        guideColl.add(guide);
        placeMaster.setGuideMasterCollection(guideColl);
        
        em.merge(placeMaster);
        em.merge(guide);
    }

}
