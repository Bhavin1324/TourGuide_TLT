package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.Cities;
import com.tlt.entities.EventMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.PaymentMethod;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.States;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.TransporterMaster;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.utils.GraphUtils;
import com.tlt.utils.UserSubscriptionMapping;
import com.tlt.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateful
public class Admin implements AdminLocal {

    @PersistenceContext(unitName = "TLT_Persistance")
    EntityManager em;

    // implementation of methods related to places
    @Override
    public void insertPlace(PlaceMaster data) {
        if (data == null) {
            return;
        }
        em.persist(data);
    }

    @Override
    public void deletePlace(String id) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, id);
        em.remove(place);
    }

    @Override
    public void updatePlace(String id, PlaceMaster newPlaceData) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, id);
        if (place != null) {
            em.merge(newPlaceData);
        }
    }

    @Override
    public Collection<PlaceMaster> getAllPlaces() {
        Collection<PlaceMaster> places = em.createNamedQuery("PlaceMaster.findAll").getResultList();
        return places;
    }

    @Override
    public PlaceMaster getPlaceById(String id) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, id);
        return place;
    }

    @Override
    public Collection<PlaceMaster> getPlacesByCategory(PlaceCategory categoryId) {
        Collection<PlaceMaster> places = em.createNamedQuery("PlaceMaster.findByCategoryId").setParameter("categoryId", categoryId).getResultList();
        return places;
    }

    @Override
    public Collection<PlaceMaster> getPlacesByName(String Name) {
        Collection<PlaceMaster> places = em.createNamedQuery("PlaceMaster.findByPlaceName").setParameter("placeName", Name).getResultList();
        return places;
    }

    @Override
    public Collection<PlaceMaster> getPlacesByCity(String cityName, String countryCode) {
        TypedQuery<Cities> cityQuery = em.createNamedQuery("Cities.findByNameAndCountryCode", Cities.class);
        cityQuery.setParameter("name", cityName);
        cityQuery.setParameter("countryCode", countryCode);
        List<Cities> cities = cityQuery.getResultList();
        return em.createNamedQuery("PlaceMaster.findPlacesByCity").setParameter("cityId", cities.get(0)).getResultList();
    }

    @Override
    public Collection<PlaceMaster> getCityPlacesByCategory(PlaceCategory categoryId, String currentCityName, String countryCode) {
        TypedQuery<Cities> cityQuery = em.createNamedQuery("Cities.findByNameAndCountryCode", Cities.class);
        cityQuery.setParameter("name", currentCityName);
        cityQuery.setParameter("countryCode", countryCode);
        List<Cities> cities = cityQuery.getResultList();
        TypedQuery<PlaceMaster> placeQuery = em.createNamedQuery("PlaceMaster.findByCategoryIdInCity", PlaceMaster.class);
        placeQuery.setParameter("categoryId", categoryId);
        placeQuery.setParameter("cityId", cities.get(0));
        return placeQuery.getResultList();
    }

    // implementation of methods related to guide
    @Override
    public void insertGuide(GuideMaster data) {
        if (data == null) {
            return;
        }
        em.persist(data);
    }

    @Override
    public void mapGuideWithPlaces(GuideMaster guide, Collection<PlaceMaster> places) {
        Collection<PlaceMaster> guidePlaces = guide.getPlaceMasterCollection();
        for (PlaceMaster pm : places) {
            if (!guidePlaces.contains(pm)) {
                guidePlaces.add(pm);
                Collection<GuideMaster> guideOfPlace = pm.getGuideMasterCollection();
                guideOfPlace.add(guide);

                pm.setGuideMasterCollection(guideOfPlace);
                guide.setPlaceMasterCollection(guidePlaces);
                em.merge(pm);
                em.merge(guide);
            }
        }
    }

    @Override
    public void deleteGuide(String id) {
        GuideMaster guide = (GuideMaster) em.find(GuideMaster.class, id);
        em.remove(guide);
    }

    @Override
    public void updateGuide(String id, GuideMaster newGuideData) {
        GuideMaster guide = (GuideMaster) em.find(GuideMaster.class, id);
        if (guide != null) {
            em.merge(newGuideData);
        }
    }

    @Override
    public GuideMaster getGuideById(String id) {
        GuideMaster guide = (GuideMaster) em.find(GuideMaster.class, id);
        return guide;
    }

    @Override
    public Collection<GuideMaster> getAllGuides() {
        Collection<GuideMaster> guides = em.createNamedQuery("GuideMaster.findAll").getResultList();
        return guides;
    }

    @Override
    public Collection<GuideMaster> getGuideByPlace(String PlaceId) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, PlaceId);
        return place.getGuideMasterCollection();
    }

    @Override
    public Collection<AppointmentMaster> getAppointmentsOfAllGuides() {
        return em.createNamedQuery("AppointmentMaster.getAllGuidesAppointments").getResultList();
    }

    @Override
    public Collection<EventMaster> getEventsOfAllGuides() {
        return em.createNamedQuery("EventMaster.findAll").getResultList();
    }

    @Override
    public Collection<EventMaster> getEventsByCity(String cityName) {
        return em.createNamedQuery("EventMaster.findByCity").setParameter("cityname", cityName).getResultList();
    }

    @Override
    public Collection<GuideMaster> getAllGuidesOfPlaces(String placeId) {
        PlaceMaster place = (PlaceMaster) em.find(PlaceMaster.class, placeId);
        if (place == null) {
            return null;
        }
        return place.getGuideMasterCollection();
    }

    @Override
    public Collection<GuideMaster> getAvailableGuidesOfPlace(String placeId) {
        String query = "SELECT g.id, g.name, g.gender, g.email,g.username,g.profile_image, g.amount, g.phone_number,g.is_appointed FROM place_guide_mapping p JOIN guide_master g ON guide_id =g.id where place_id = '" + placeId + "' and g.is_appointed = 0;";
        List<Object[]> guides = em.createNativeQuery(query).getResultList();
        Collection<GuideMaster> filteredGuide = new ArrayList<>();
        for (Object[] guide : guides) {
            if (guide[0] == null) {
                return null;
            }
            GuideMaster g = new GuideMaster();
            g.setId(String.valueOf(guide[0]));
            g.setName(String.valueOf(guide[1]));
            g.setGender(String.valueOf(guide[2]));
            g.setEmail(String.valueOf(guide[3]));
            g.setUsername(String.valueOf(guide[4]));
            g.setProfileImage(String.valueOf(guide[5]));
            g.setAmount(Integer.parseInt(String.valueOf(guide[6])));
            g.setPhoneNumber(Long.parseLong(String.valueOf(guide[7])));
            g.setIsAppointed(Boolean.parseBoolean(String.valueOf(guide[8])));
            filteredGuide.add(g);
        }
        return filteredGuide;
    }

    // implementation of method related to subscription
    @Override
    public void insertSubscriptionModel(SubscriptionModel data) {
        if (data == null) {
            return;
        }
        em.persist(data);
    }

    @Override
    public void deleteSubscriptionModel(String id) {
        SubscriptionModel subModel = (SubscriptionModel) em.find(SubscriptionModel.class, id);
        em.remove(subModel);
    }

    @Override
    public void updateSubscriptionModel(String id, SubscriptionModel newSubscriptionModel) {
        SubscriptionModel subModel = (SubscriptionModel) em.find(SubscriptionModel.class, id);
        if (subModel != null) {
            em.merge(newSubscriptionModel);
        }
    }

    @Override
    public SubscriptionModel getSubscriptionModelById(String id) {
        SubscriptionModel subModel = (SubscriptionModel) em.find(SubscriptionModel.class, id);
        return subModel;
    }

    @Override
    public Collection<SubscriptionModel> getSubscriptionModelByName(String modelName) {
        Collection<SubscriptionModel> subscriptionModels = em.createNamedQuery("SubscriptionModel.findByName").setParameter("name", modelName).getResultList();
        return subscriptionModels;
    }

    @Override
    public Collection<UserSubscriptionMapping> getAllSubscriptions() {
        List<Object[]> jointable = em.createNativeQuery("select * from user_subscription_mapping").getResultList();

        Collection<UserSubscriptionMapping> mappedData = new ArrayList<>();
        for (Object[] item : jointable) {
            UserSubscriptionMapping usmap = new UserSubscriptionMapping();

            usmap.setUser(em.find(UserMaster.class, item[0]));
            usmap.setSubMaster(em.find(SubscriptionMaster.class, item[1]));
            mappedData.add(usmap);
        }

        return mappedData;
//        Collection<SubscriptionMaster> subscriptions = em.createNamedQuery("SubscriptionMaster.findAll").getResultList();
//        return subscriptions;
    }

    @Override
    public Collection<SubscriptionMaster> getSubscriptionsbyUser(String userId) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, userId);
        return user.getSubscriptionMasterCollection();
    }

    @Override
    public Collection<PaymentMaster> getAllPaymentDetails() {
        Collection<PaymentMaster> payments = em.createNamedQuery("PaymentMaster.findAll").getResultList();
        return payments;
    }

    @Override
    public Collection<PaymentMaster> getPaymentDetailsByUser(String username) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, username);
        return user.getPaymentMasterCollection();
    }

    @Override
    public UserRole getRoleByName(String role) {
        return (UserRole) em.createNamedQuery("UserRole.findByRole");
    }

    @Override
    public Collection<UserMaster> getAllUsers() {
        Collection<UserMaster> users = em.createNamedQuery("UserMaster.findAll").getResultList();
        return users;
    }

    @Override
    public void insertPlaceCategory(PlaceCategory category) {
        em.persist(category);
    }

    @Override
    public void updatePlaceCategory(String id, PlaceCategory category) {
        PlaceCategory oldCategory = em.find(PlaceCategory.class, id);
        oldCategory.setName(category.getName());
        em.merge(oldCategory);
    }

    @Override
    public void deletePlaceCategory(String id) {
        PlaceCategory p = em.find(PlaceCategory.class, id);
        em.remove(p);
    }

    @Override
    public Collection<PlaceCategory> getAllPlaceCategories() {
        return em.createNamedQuery("PlaceCategory.findAll").getResultList();
    }

    @Override
    public Collection<UserRole> getAllRoles() {
        return em.createNamedQuery("UserRole.findAll").getResultList();
    }

    @Override
    public void addUserRole(UserRole userRole) {
        Collection<UserRole> roles = em.createNamedQuery("UserMaster.findByUsername").setParameter("username", userRole.getUserMaster().getUsername()).getResultList();
        if (!roles.contains(userRole)) {
            em.persist(userRole);
        }
    }

    @Override
    public Collection<Cities> getCityByStateId(Integer stateId) {
        States state = em.find(States.class, stateId);
        return state.getCitiesCollection();
    }

    @Override
    public PlaceCategory getPlaceCategoryById(String catid) {
        return em.find(PlaceCategory.class, catid);
    }

    @Override
    public Cities getCityById(Integer cityid) {
        return em.find(Cities.class, cityid);
    }

    @Override
    public Collection<SubscriptionModel> getAllSubscriptionModel() {
        return em.createNamedQuery("SubscriptionModel.findAll").getResultList();
    }

    @Override
    public long getUserCount() {
        return (long) em.createNamedQuery("UserMaster.getUserCount").getSingleResult();
    }

    @Override
    public long getPlacesCount() {
        return (long) em.createNamedQuery("PlaceMaster.getPlaceCount").getSingleResult();
    }

    @Override
    public long getActiveSubsCount() {
        Date today = new Date();
//        System.out.println("Today is " + today);
        return (long) em.createNamedQuery("SubscriptionMaster.getActiveSubsCount").setParameter("today", today).getSingleResult();

    }

    @Override
    public long getTotalIncome() {
        Query obj = em.createNativeQuery("SELECT SUM(s.cost) FROM subscription_model s INNER JOIN subscription_master sm on s.id = sm.subscription_model_id;");
        long active = Long.parseLong(obj.getSingleResult().toString());
        return active;
    }

    @Override
    public Cities getCityOfStateByName(Integer state_id, String name) {
        States state = em.find(States.class, state_id);
        Collection<Cities> cities = state.getCitiesCollection();
        for (Cities c : cities) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Collection<UserMaster> getUsersByRoles(String role) {
        Collection<UserRole> roles = em.createNamedQuery("UserRole.findByRole").setParameter("role", role).getResultList();

        Collection<UserMaster> usersToReturn = new ArrayList<>();
        for (UserRole r : roles) {
            usersToReturn.add(r.getUserMaster());
        }
        return usersToReturn;
    }

    @Override
    public void deleteUser(String id) {
        UserMaster user = em.find(UserMaster.class, id);
//        Collection<UserRole> roles = user.getUserRoleCollection();
//        Collection<AppointmentMaster> appt = user.getAppointmentMasterCollection();
//        Collection<GuideMaster> guides = user.getGuideMasterCollection();
//        Collection<PaymentMaster> payments = user.getPaymentMasterCollection();
//        Collection<SubscriptionMaster> subs = user.getSubscriptionMasterCollection();
//        user.setUserRoleCollection(new ArrayList<>());
//        user.setAppointmentMasterCollection(new ArrayList<>());
//        em.createNamedQuery("UserRole.deleteByUsername").setParameter("username", user.getName());
//        em.merge(user);
        em.remove(user);
    }

    @Override
    public void insertUser(UserMaster user) {
        em.persist(user);
    }

    @Override
    public List<GraphUtils> getMonthlySubscriptionData() {
        List<Object[]> list = new ArrayList<>();

        list = em.createNativeQuery("SELECT DISTINCT MONTHNAME(s.start_date), COUNT(*) AS 'subsCount' FROM `subscription_master` s GROUP BY MONTH(s.start_date) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(s.start_date), ' 01'), '%Y %M %d') ASC;").getResultList();

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
    public List<GraphUtils> getMonthlyRevenueData() {
        List<Object[]> list = new ArrayList<>();

        list = em.createNativeQuery("SELECT MONTHNAME(sm.start_date), SUM(s.cost) FROM `subscription_model` s INNER JOIN subscription_master sm ON s.id = sm.subscription_model_id GROUP BY MONTHNAME(sm.start_date) ORDER BY STR_TO_DATE(CONCAT('0001 ', MONTHNAME(sm.start_date), ' 01'), '%Y %M %d') ASC;").getResultList();

        List<GraphUtils> graphData = new ArrayList<>();
        for (Object[] g : list) {
            GraphUtils gd = new GraphUtils();
            gd.setMonth(g[0].toString());
            gd.setAmount(Long.parseLong(g[1].toString()));
            graphData.add(gd);
        }
        return graphData;
    }

    // Transporter methods
    @Override
    public TransporterMaster getRandomTranporter() {
        List<TransporterMaster> transporters = em.createNamedQuery("TransporterMaster.randomOrder").setMaxResults(1).getResultList();
        return transporters.get(0);
    }

    @Override
    public Collection<TransporterMaster> getAllTransporters() {
        return em.createNamedQuery("TransporterMaster.findAll").getResultList();
    }

    @Override
    public void addTransporter(TransporterMaster transporter) {
        em.persist(transporter);
    }

    @Override
    public void updateTransporter(String id, TransporterMaster transporter) {
        TransporterMaster tmaster = em.find(TransporterMaster.class, id);
        tmaster.setName(transporter.getName());
        tmaster.setAmount(transporter.getAmount());
        tmaster.setPlateNo(transporter.getPlateNo());
        tmaster.setContactNo(transporter.getContactNo());
        tmaster.setCityId(transporter.getCityId());
        em.merge(tmaster);
    }

    @Override
    public void removeTransporter(String transporterId) {
        TransporterMaster tmaster = em.find(TransporterMaster.class, transporterId);
        em.remove(tmaster);
    }

    @Override
    public Collection<AppointmentMaster> getAllAppointments() {
        return em.createNamedQuery("AppointmentMaster.findAll").getResultList();
    }

    @Override
    public AppointmentMaster getAppointmentById(String id) {
        AppointmentMaster appointment = (AppointmentMaster) em.find(AppointmentMaster.class, id);
        if (appointment == null) {
            return null;
        }
        return appointment;
    }

    @Override
    public void insertIntoAppointment(AppointmentMaster appointment) {
        AppointmentMaster appt = (AppointmentMaster) em.find(AppointmentMaster.class, appointment.getId());
        if (appt == null) {
            em.persist(appointment);
        }
    }

    @Override
    public void updateAppointment(String id, AppointmentMaster appointment) {
        AppointmentMaster appt = (AppointmentMaster) em.find(AppointmentMaster.class, id);
        if (appt == null) {
            return;
        }
        em.merge(appointment);
    }

    @Override
    public void deleteAppointment(String id) {
        AppointmentMaster appt = (AppointmentMaster) em.find(AppointmentMaster.class, id);
        if (appt == null) {
            return;
        }
        em.remove(appt);
    }

    @Override
    public PaymentMethod getCardPayment() {
        return (PaymentMethod) em.find(PaymentMethod.class, "3hbk2jh3bkj2hb3");
    }

    @Override
    public void joinEvent(Integer noOfPeople, EventMaster event, String username) {
        UserMaster user = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", username).getSingleResult();
        EventMaster emaster = em.find(EventMaster.class, event.getId());
        Integer noofpeople = event.getNumberOfPeople() + noOfPeople;
        emaster.setNumberOfPeople(noofpeople);
        UserMaster umaster = em.find(UserMaster.class, user.getId());
        Collection<EventMaster> usersEvent = umaster.getEventMasterCollection();
        usersEvent.add(emaster);
        umaster.setEventMasterCollection(usersEvent);

        Collection<UserMaster> eventsUser = emaster.getUserMasterCollection();
        eventsUser.add(umaster);
        emaster.setUserMasterCollection(eventsUser);

        em.merge(umaster);
        em.merge(emaster);
    }

    @Override
    public Collection<AppointmentMaster> getUserAppointments(String userId) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, userId);
        if (user == null) {
            return null;
        }
        Collection<AppointmentMaster> userAppointments = em.createNamedQuery("AppointmentMaster.getUserAppointments").setParameter("userId", user).getResultList();
        return userAppointments;
    }

    @Override
    public Collection<EventMaster> getUserEvents(String userId) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, userId);
        if (user == null) {
            return null;
        }
        return user.getEventMasterCollection();
    }

    @Override
    public Long getTodaysAppointmentsCount() {
        return (long) em.createNativeQuery("SELECT COUNT(*) from appointment_master WHERE DATE(start_datetime) = CURRENT_DATE();").getSingleResult();

    }

    @Override
    public Long getTodaysEventsCount() {
         return (long) em.createNativeQuery("SELECT COUNT(*) from event_master e WHERE DATE(e.start_time) = CURRENT_DATE();").getSingleResult();
    }

    @Override
    public Long getTodaysSubscriptionsCount() {
       return (long) em.createNativeQuery("SELECT COUNT(*) FROM `subscription_master` WHERE DATE(start_date) = CURRENT_DATE();").getSingleResult();
    }

    @Override
    public Long getEventCancelCount() {
         return (long) em.createNativeQuery("SELECT COUNT(*) FROM `event_master` WHERE event_status  = 'Canceled'").getSingleResult();
    }

    @Override
    public Long getAppointmentCancelCount() {
      return (long) em.createNativeQuery("SELECT COUNT(*) FROM `appointment_master` WHERE appointment_status = 'Canceled'").getSingleResult();
    }

}
