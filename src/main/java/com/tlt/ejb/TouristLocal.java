package com.tlt.ejb;

import com.tlt.entities.PaymentMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
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

    void subscribeToPlan(SubscriptionModel model, String username, String cardNumber);

    Collection<SubscriptionMaster> getUsersSubscriptions(String username);

    boolean isUserSubscribed(SubscriptionModel model, String username);

    Collection<PaymentMaster> usersPaymentHistory(String username);
}
