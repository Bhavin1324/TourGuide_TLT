package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.EventMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.utils.GraphUtils;
import com.tlt.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
        Collection<AppointmentMaster> appointments = em.createNamedQuery("AppointmentMaster.getAppointmentsByStatus").setParameter("gid", guide.getId()).setParameter("status", status).getResultList();
        return appointments;
    }

    @Override
    public List<GraphUtils> getMonthlyPersonalAppointmentsCount(String gusername) {

        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        List<Object[]> list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(a.start_datetime), COUNT(*) as 'appointmentCounts' FROM `appointment_master` a WHERE a.guide_id = '" + guide.getId() + "' GROUP BY MONTHNAME(a.start_datetime) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(a.start_datetime), ' 01'), '%Y %M %d') ASC;").getResultList();
        List<GraphUtils> graphData = new ArrayList<>();
        for (Object[] obj : list) {
//            if (obj[0] != null && obj[1] != null) {
            GraphUtils gd = new GraphUtils();
            gd.setMonth(obj[0].toString());
            gd.setCount(Long.parseLong(obj[1].toString()));
            graphData.add(gd);
//            }
        }
        return graphData;
    }

    @Override
    public List<GraphUtils> getMonthlyEventsCount(String gusername) {

        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        List<Object[]> list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(e.start_time), COUNT(*) as 'appointmentCounts' FROM `event_master` e WHERE e.guide_id = '" + guide.getId() + "' GROUP BY MONTHNAME(e.start_time) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(e.start_time), ' 01'), '%Y %M %d') ASC;").getResultList();
        List<GraphUtils> graphData = new ArrayList<>();
        for (Object[] obj : list) {
//            if (obj[0] != null && obj[1] != null) {
            GraphUtils gd = new GraphUtils();
            gd.setMonth(obj[0].toString());
            gd.setCount(Long.parseLong(obj[1].toString()));
            graphData.add(gd);
//            }
        }
        return graphData;
    }

    @Override
    public List<GraphUtils> getMonthlyRevenueOfPersonalAppointments(String gusername) {

        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        List<Object[]> list = new ArrayList<>();
        List<GraphUtils> graphData = new ArrayList<>();

        list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(a.start_datetime), g.amount as 'amount',SUM(a.number_of_people) as 'No of people' FROM `appointment_master` a JOIN guide_master g on a.guide_id = g.id  WHERE guide_id = '" + guide.getId() + "' GROUP BY MONTHNAME(a.start_datetime) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(a.start_datetime), ' 01'), '%Y %M %d') ASC;").getResultList();

        for (Object[] g : list) {
            GraphUtils gd = new GraphUtils();
            if (g[0] != null && g[1] != null) {
                gd.setMonth(g[0].toString());
                gd.setAmount(Long.parseLong(g[1].toString()) * Long.parseLong(g[2].toString()));
//            gd.setNoofPeople(Long.parseLong(g[2].toString()));
                graphData.add(gd);
            }

        }
        return graphData;
    }

    @Override
    public Long getTotalRevenueOfPersonalAppointments(String gusername) {

        long revenue = 0;
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        List<Object[]> list = new ArrayList<>();
        if (guide != null) {
            list = em.createNativeQuery("select  g.amount, SUM(a.number_of_people) from appointment_master a join guide_master g on a.guide_id = g.id where a.guide_id = '" + guide.getId() + "'").getResultList();

            for (Object[] g : list) {
                GraphUtils gd = new GraphUtils();
                if (g[0] != null && g[1] != null) {
                    gd.setAmount(Long.parseLong(g[0].toString()));
                    gd.setNoofPeople(Long.parseLong(g[1].toString()));
                    revenue += gd.getAmount() * gd.getNoofPeople();
                }
            }
        }
        return revenue;
    }

    @Override
    public List<GraphUtils> getMonthlyRevenueOfEvents(String gusername) {
        //        UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        List<Object[]> list = new ArrayList<>();
        List<GraphUtils> graphData = new ArrayList<>();
        list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(e.start_time),g.amount as 'amount',SUM(e.number_of_people) as 'No of people' FROM `event_master` e JOIN guide_master g on e.guide_id = g.id  WHERE guide_id = '" + guide.getId() + "' GROUP BY MONTHNAME(e.start_time) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(e.start_time), ' 01'), '%Y %M %d') ASC;").getResultList();

        for (Object[] g : list) {
            GraphUtils gd = new GraphUtils();
            if (g[0] != null && g[1] != null) {
                gd.setMonth(g[0].toString());
                gd.setAmount(Long.parseLong(g[1].toString()) * Long.parseLong(g[2].toString()));
//            gd.setNoofPeople(Long.parseLong(g[2].toString()));
                graphData.add(gd);
            }
        }
        return graphData;
    }

    @Override
    public Long getTotalRevenueOfEvents(String gusername) {
        long revenue = 0;
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        List<Object[]> list = new ArrayList<>();
        list = em.createNativeQuery("select  g.amount, SUM(e.number_of_people) from event_master e join guide_master g on e.guide_id = g.id where e.guide_id = '" + guide.getId() + "'").getResultList();

        for (Object[] g : list) {
            if (g[0] != null && g[1] != null) {
                GraphUtils gd = new GraphUtils();
                gd.setAmount(Long.parseLong(g[0].toString()));
                gd.setNoofPeople(Long.parseLong(g[1].toString()));
                revenue += gd.getAmount() * gd.getNoofPeople();
            }
        }
        return revenue;
    }

    @Override
    public void updateAppointmentStatus(AppointmentMaster appointment, String status) {
        AppointmentMaster appt = em.find(AppointmentMaster.class, appointment.getId());
        appt.setAppointmentStatus(status);
        em.merge(appt);
    }
    @Override
    public void updateEventStatus(EventMaster event, String status) {
        EventMaster e = em.find(EventMaster.class, event.getId());
        e.setEventStatus(status);
        em.merge(e);
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

    @Override
    public void raiseAnEvent(PlaceMaster placeMaster, String gusername, Date startDate, Date endDate) {
        PlaceMaster place = em.find(PlaceMaster.class, placeMaster.getId());
        UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", gusername).getSingleResult();
        GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByPhoneNumber").setParameter("phoneNumber", user.getContact()).getSingleResult();
        EventMaster event = new EventMaster();
        event.setId(Utils.getUUID());
        event.setGuideId(guide);
        event.setPlaceId(place);
        event.setEventStatus("Pending");
        event.setStartTime(startDate);
        event.setEndTime(endDate);
        event.setNumberOfPeople(0);
        em.persist(event);

        //adding this collection to guide's eventColl
        Collection<EventMaster> eventColl = guide.getEventMasterCollection();
        eventColl.add(event);
        guide.setEventMasterCollection(eventColl);
        em.merge(guide);
    }

    @Override
    public Collection<EventMaster> getEventsOfGuide(String gusername) {
       GuideMaster guide = (GuideMaster) em.createNamedQuery("GuideMaster.findByUsername").setParameter("username", gusername).getSingleResult();
       Collection<EventMaster> events =  guide.getEventMasterCollection();
       return events;
    }

}
