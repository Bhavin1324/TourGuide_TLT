package com.tlt.ejb;

import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.UserMaster;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        if(data == null) 
            return;
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

}
