package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * RestaurantDao class provides the database access for all the endpoints in restaurant controller
 */
@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps fetch all restaurants
     *
     * @return List<RestaurantEntity> object
     */
    public List<RestaurantEntity> restaurantsByRating() {
        try {
            return entityManager.createNamedQuery("allRestaurantsByRating", RestaurantEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps fetch all categories for a particular restaurant
     *
     * @return List<RestaurantCategory> object
     */
    public List<RestaurantCategory> getCategories(String id){
        try {
            return entityManager.createNamedQuery("categoriesMethods", RestaurantCategory.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public RestaurantEntity getRestaurantByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("restaurantByUUID", RestaurantEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantByName(final String restaurantName){
        try {
            return entityManager.createNamedQuery("restaurantByName", RestaurantEntity.class).setParameter("restaurantName", "%" + restaurantName + "%").getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantsByCategoryId(final String categoryId){
        try {
            return entityManager.createNamedQuery("restaurantByCategoryId", RestaurantEntity.class).setParameter("categoryId", categoryId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public RestaurantEntity updateRestaurantEntity(RestaurantEntity restaurantEntity) {
        return entityManager.merge(restaurantEntity);
    }
}
