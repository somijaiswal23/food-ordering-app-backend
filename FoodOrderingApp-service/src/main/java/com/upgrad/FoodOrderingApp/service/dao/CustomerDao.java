package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * CustomerDao class provides the database access for all the endpoints in customer controller
 */
@Repository
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method creates new customer from given CustomerEntity object
     *
     * @param customerEntity the CustomerEntity object from which new customer will be created
     *
     * @return CustomerEntity object
     */
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    /**
     * This method helps find existing customer by contact number
     *
     * @param contactNumber the contact number which will be searched in database to find existing customer
     *
     * @return CustomerEntity object if given contact number exists in database
     */
    public CustomerEntity getCustomerByContactNumber(String contactNumber) {
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", contactNumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method creates new authorization from given CustomerAuthEntity object
     *
     * @param customerAuthEntity the CustomerAuthEntity object from which new authorization will be created
     *
     * @return CustomerAuthEntity object
     */
    public CustomerAuthEntity createCustomerAuth(CustomerAuthEntity customerAuthEntity) {
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }
}
