package com.tlt.ejb;

import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.entities.UserMaster;
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
    public void subscribeToPlan(SubscriptionModel model,UserMaster user) {

        UserMaster usermaster = em.find(UserMaster.class,user.getId());
        Collection<SubscriptionMaster> usersubs = usermaster.getSubscriptionMasterCollection();
        
//        create new sub
        SubscriptionMaster smaster = new SubscriptionMaster();
//        smaster.setStartDate(new Date());
//        smaster.setEndDate();
        smaster.setSubscriptionModelId(model);
        
        //persist new sub
        em.persist(smaster);
        
        //add this sub to user's subscription collection
        usersubs.add(smaster);
        
        SubscriptionMaster sm  = em.find(SubscriptionMaster.class,smaster.getId());
        
        //get all users from submaster's usercollection
        Collection<UserMaster> users = sm.getUserMasterCollection();
        
        //add that user to the collection
        users.add(usermaster);
        
        em.merge(usermaster);
        em.merge(sm);
//        usermaster.setSubscriptionMasterCollection();
        
    }
    
}
