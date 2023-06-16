package com.tlt.ejb;

import com.tlt.entities.EventMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.TransportMaster;
import com.tlt.entities.UserMaster;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface TouristLocal {

    Collection<UserMaster> getAllUser();

    UserMaster getUserById(String id);

    void insertUser(UserMaster userMaster);

    void deleteUser(String id);

    void updateUser(String id, UserMaster newUser);

    UserMaster findUserByUsername(String username);

    UserMaster findUserByEmail(String email);

    void subscribeToPlan(SubscriptionModel model, String username, String cardNumber, Integer cost);

    Collection<SubscriptionMaster> getUsersSubscriptions(String username);

    boolean isUserSubscribed(SubscriptionModel model, String username);

    Collection<PaymentMaster> usersPaymentHistory(String username);

    void updatePayment(PaymentMaster payment);

    void reserveYourPlace(PaymentMaster payment);

    // Transport methods
    Collection<TransportMaster> getAllTransports();

    TransportMaster findTransportById(String id);

    void updateTransportInfo(String id, TransportMaster tranport);

    void insertTransport(TransportMaster transport);

    void removeTransportInfo(String id);

    Collection<PaymentMaster> getUsersSubscriptionHistory(String username);

    Collection<PaymentMaster> getUsersAppointmentHistory(String username);

    Collection<PaymentMaster> getUsersEventsHistory(String username);

    void joinEvent(EventMaster event, String username, PaymentMaster payment);

}
