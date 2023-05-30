package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.Cities;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.States;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.utils.GraphUtils;
import com.tlt.utils.UserSubscriptionMapping;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@DeclareRoles("Admin")
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
        for(PlaceMaster pm : places){
            if(!guidePlaces.contains(pm)){
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
        return em.createNamedQuery("AppointmentMaster.findAll").getResultList();
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
    public Collection<UserMaster> getUsersByRoles(UserRole role) {
        Collection<UserRole> roles = em.createNamedQuery("UserRole.findByRole").setParameter("role", role.getUserRolePK().getRole()).getResultList();

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
            gd.setCount(Long.parseLong(g[1].toString()));
            graphData.add(gd);
        }
        return graphData;
    }

}
