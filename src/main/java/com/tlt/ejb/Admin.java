package com.tlt.ejb;

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
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public Collection<PlaceMaster> getPlacesByCategory(String categoryId) {
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
    public Collection<SubscriptionMaster> getAllSubscriptions() {
        Collection<SubscriptionMaster> subscriptions = em.createNamedQuery("SubscriptionMaster.findAll").getResultList();
        return subscriptions;
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
    public Collection<SubscriptionMaster> getSubscriptionCount() {
        return em.createNativeQuery("Select s.subscriptionModelId Count(*) from SubscriptionMaster s").getResultList();
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

}
