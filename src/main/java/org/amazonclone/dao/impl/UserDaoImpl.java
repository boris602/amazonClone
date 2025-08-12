package org.amazonclone.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.amazonclone.dao.UserDao;
import org.amazonclone.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Long theId) {
        return entityManager.find(User.class, theId);
    }

    @Override
    public User findByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User save(User u) {
        if (u.getId() == null) {
            entityManager.persist(u);   // INSERT for new entity
            return u;                   // u now has the generated id
        } else {
            return entityManager.merge(u); // UPDATE for existing entity
        }
    }


    @Override
    public void deleteById(Long theId) {
        entityManager.remove(entityManager.getReference(User.class, theId));
    }
}

