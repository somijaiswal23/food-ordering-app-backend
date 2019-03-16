package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;
    /**
     * This method implements the business logic for 'save address' endpoint
     *
     * @param address new address will be created from given AddressEntity object
     *
     * @return AddressEntity object
     *
     * @throws SaveAddressException exception if any of the validation fails on customer details
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity address) throws SaveAddressException, AddressNotFoundException{

        /** Validation for format of Pincode
         * This is to validate Indian Post code.
         * [1-9]: Matches exactly one digit from 1 to 9.
         * [0-9]{5}: Matches exactly five digits in the inclusive range 0-9
         */
        if(!address.getPincode().matches("^[1-9][0-9]{5}$"){
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }

        //Validation for state
        if(addressDao.getState(address.getStateid().toString()) == null){
            throw new AddressNotFoundException("ANF-002", "No state by this id");
        }
    }

    /**
     * This method implements the business logic for 'Get All Saved Address' endpoint
     *
     */

    public List<AddressEntity> getAllAddresses() {

        return addressDao.getAllAddresses();
    }

    /**
     * This method returns State Name
     *
     */

    public StateEntity getstateEntity(StateEntity id){
        return addressDao.getState(id.toString());
    }


    /**


     * This method implements the business logic for 'Get All States' endpoint
     *
     */

    public List<StateEntity> getAllStates() {

        return addressDao.getAllStates();
    }

    /**
     * This method implements the business logic for 'Get Address by Id' endpoint
     *
     */

    public AddressEntity getAddressById(String id) {

         return addressDao.getAddressById(id);
    }

}