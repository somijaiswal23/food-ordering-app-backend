package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 * AddressDao class provides the database access for all the endpoints in address controller
 */

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps find existing State by StateID
     *
     * @param stateUUID the stateid which will be searched in database to find existing state
     *
     * @return StateEntity object if given state exists in database
     */
    public StateEntity getState(String stateUUID) {
        try {
            return entityManager.createNamedQuery("statebyUUID", StateEntity.class).setParameter("stateUUID", stateUUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
