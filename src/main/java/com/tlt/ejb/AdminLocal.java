package com.tlt.ejb;

import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.UserMaster;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface AdminLocal {

    // Place
    void insertPlace(PlaceMaster data);

    void deletePlace(String id);

    void updatePlace(String id, PlaceMaster newPlaceData);

    Collection<PlaceMaster> getAllPlaces();

    PlaceMaster getPlaceById(String id);

    Collection<PlaceMaster> getPlacesByCategory(String categoryId);

    Collection<PlaceMaster> getPlacesByName(String Name);

    // Guide
    void insertGuide(GuideMaster data);

    void deleteGuide(String id);

    void updateGuide(String id, GuideMaster newGuideData);

    GuideMaster getGuideById(String id);

    Collection<GuideMaster> getAllGuides();

    Collection<GuideMaster> getGuideByPlace(String PlaceId);

    //SubscriptionModel management
    void insertSubscriptionModel(SubscriptionModel data);

    void deleteSubscriptionModel(String id);

    void updateSubscriptionModel(String id, SubscriptionModel newSubscriptionModel);

    SubscriptionModel getSubscriptionModelById(String id);

    Collection<SubscriptionModel> getSubscriptionModelByName(String modelName);

    //Subscription 
    Collection<SubscriptionMaster> getAllSubscriptions();

    Collection<SubscriptionMaster> getSubscriptionsbyUser(String userId);

    //Payment
    Collection<PaymentMaster> getAllPaymentDetails();

    Collection<PaymentMaster> getPaymentDetailsByUser(String username);

    //Users
    Collection<UserMaster> getAllUsers();

}
