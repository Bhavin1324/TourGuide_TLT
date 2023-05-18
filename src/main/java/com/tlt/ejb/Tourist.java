package com.tlt.ejb;

import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.UserMaster;
import com.tlt.utils.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class Tourist implements TouristLocal {

    @PersistenceContext(unitName = "TLT_Persistance")
    EntityManager em;

    @Override
    public Collection<UserMaster> getAllUser() {
        return em.createNamedQuery("UserMaster.findAll").getResultList();
    }

    @Override
    public UserMaster getUserById(String id) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public void insertUser(UserMaster userMaster) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, userMaster.getId());
        if (user == null) {
            em.persist(userMaster);
        }
    }

    @Override
    public void deleteUser(String id) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public void updateUser(String id, UserMaster newUser) {
        UserMaster user = (UserMaster) em.find(UserMaster.class, id);
        if (user != null) {
            em.merge(newUser);
        }
    }

    @Override
    public UserMaster findUserByUsername(String username) {
        Collection<UserMaster> users = em.createNamedQuery("UserMaster.findByUsername").setParameter("username", username).getResultList();
        for (UserMaster u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public UserMaster findUserByEmail(String email) {
        Collection<UserMaster> users = em.createNamedQuery("UserMaster.findByEmail").setParameter("email", email).getResultList();
        for (UserMaster u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void subscribeToPlan(SubscriptionModel model, String username) {

        UserMaster usermaster = (UserMaster) em.createNamedQuery("UserMaster.findByUsername").setParameter("username", username).getSingleResult();
        
        //get all user's subscription list
        Collection<SubscriptionMaster> usersubs = usermaster.getSubscriptionMasterCollection();

        // create A New Subscription
        SubscriptionMaster submaster = new SubscriptionMaster();
        String id = Utils.getUUID();
        submaster.setId(id);

        Date startDate = new Date();
        submaster.setStartDate(startDate);

        //add months of subscription model 
        Calendar calEndDate = Calendar.getInstance();;
        calEndDate.add(Calendar.MONTH, model.getDurationInMonth());
        Date endDate = calEndDate.getTime();
        submaster.setEndDate(endDate);
        submaster.setSubscriptionModelId(model);
        submaster.setUserMasterCollection(new ArrayList<>());
        
        //add the subcription to user's subscriptionCollection
        usersubs.add(submaster);
        
        //add the user to the subscriptionMaster's user collection
        Collection<UserMaster> userColl = submaster.getUserMasterCollection();
        userColl.add(usermaster);
        em.persist(submaster);
        em.merge(usermaster);
    }

}
