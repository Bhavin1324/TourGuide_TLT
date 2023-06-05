package com.tlt.ejb;

import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.Cities;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.TransporterMaster;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.utils.GraphUtils;
import com.tlt.utils.UserSubscriptionMapping;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AdminLocal {

    // Place
    void insertPlace(PlaceMaster data);

    void deletePlace(String id);

    void updatePlace(String id, PlaceMaster newPlaceData);

    Collection<PlaceMaster> getAllPlaces();

    PlaceMaster getPlaceById(String id);

    Collection<PlaceMaster> getPlacesByCategory(PlaceCategory categoryId);

    Collection<PlaceMaster> getPlacesByName(String Name);

    // Guide
    void insertGuide(GuideMaster data);

    void deleteGuide(String id);

    void updateGuide(String id, GuideMaster newGuideData);

    GuideMaster getGuideById(String id);

    Collection<GuideMaster> getAllGuides();

    Collection<GuideMaster> getGuideByPlace(String PlaceId);

    Collection<AppointmentMaster> getAppointmentsOfAllGuides();

    Collection<GuideMaster> getAllGuidesOfPlaces(String placeId);

    Collection<GuideMaster> getAvailableGuidesOfPlace(String placeId);

    void mapGuideWithPlaces(GuideMaster guide, Collection<PlaceMaster> places);

    //SubscriptionModel management
    void insertSubscriptionModel(SubscriptionModel data);

    void deleteSubscriptionModel(String id);

    void updateSubscriptionModel(String id, SubscriptionModel newSubscriptionModel);

    SubscriptionModel getSubscriptionModelById(String id);

    Collection<SubscriptionModel> getSubscriptionModelByName(String modelName);

    Collection<SubscriptionModel> getAllSubscriptionModel();

    //Subscription 
    Collection<UserSubscriptionMapping> getAllSubscriptions();

    Collection<SubscriptionMaster> getSubscriptionsbyUser(String userId);

    //Payment
    Collection<PaymentMaster> getAllPaymentDetails();

    Collection<PaymentMaster> getPaymentDetailsByUser(String username);

    //Users
    Collection<UserMaster> getAllUsers();

    void deleteUser(String id);

    void insertUser(UserMaster user);

    Collection<UserMaster> getUsersByRoles(UserRole role);

    //Roles
    Collection<UserRole> getAllRoles();

    UserRole getRoleByName(String role);

    void addUserRole(UserRole userRole);

    //Category
    void insertPlaceCategory(PlaceCategory category);

    void updatePlaceCategory(String id, PlaceCategory category);

    void deletePlaceCategory(String id);

    Collection<PlaceCategory> getAllPlaceCategories();

    PlaceCategory getPlaceCategoryById(String catid);

    //City
    Collection<Cities> getCityByStateId(Integer stateId);

    Cities getCityById(Integer cityid);

    Cities getCityOfStateByName(Integer state_id, String name);

    //get counts 
    long getPlacesCount();

    long getActiveSubsCount();

    long getUserCount();

    long getTotalIncome();

    //graphs
    List<GraphUtils> getMonthlySubscriptionData();

    List<GraphUtils> getMonthlyRevenueData();
    
    //Transports
    TransporterMaster getRandomTranporter();

}
