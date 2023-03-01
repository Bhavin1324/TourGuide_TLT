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
    
    //-------------places crud-------

    void insertPlace(PlaceMaster data);

    void deletePlace(String id);

    void updatePlace(String id, PlaceMaster newPlaceData);

    Collection<PlaceMaster> getAllPlaces();

    PlaceMaster getPlaceById(String id);

    Collection<PlaceMaster> getPlacesByCategory(PlaceCategory categoryId);

    PlaceMaster getPlacesByName(String Name);

    //-------------Guide crud--------
    void insertGuide(GuideMaster data);

    void deleteGuide(String id);

    void updateGuide(String id, GuideMaster newGuideData);

    GuideMaster getGuideById(String id);

    Collection<GuideMaster> getAllGuides();

    Collection<GuideMaster> getGuideByPlace(String PlaceId);

    //SubscriptionModel management
    void insertSubscriptionModel(SubscriptionModel newSubModel);

    void deleteSubscriptionModel(String id);

    void updateSubscriptionModel(String id, SubscriptionModel subModel);

    SubscriptionModel getSubModelById(String id);

    Collection<SubscriptionModel> getSubModelByName(String modelName);

    //Subscription 
    Collection<SubscriptionMaster> getAllSubscriptions();

    SubscriptionMaster getSubscriptionsbyUser(String Username);

    //Payment
    Collection<PaymentMaster> getAllPaymentDetails();

    PaymentMaster getPaymentDetailsByUser(String Username);
    
    //Users
    Collection<UserMaster> getAllUsers();
    
}
