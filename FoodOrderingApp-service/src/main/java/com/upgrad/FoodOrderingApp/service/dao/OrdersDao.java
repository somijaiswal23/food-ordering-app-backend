package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrdersDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrdersEntity> getOrdersByAddress(AddressEntity addressEntity) {
        try {
            return entityManager.createNamedQuery("ordersByAddress", OrdersEntity.class).setParameter("address", addressEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
