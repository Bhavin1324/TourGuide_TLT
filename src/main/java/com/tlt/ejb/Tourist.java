package com.tlt.ejb;

import com.tlt.entities.UserMaster;
import java.util.Collection;
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

}
