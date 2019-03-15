package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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

        //Validation for format of Pincode
        if(!address.getPincode().matches("^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@$%&*!^-]).{8,}$") ||
            address.getPincode().length() != 6){
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }

        //Validation for state
        if(addressDao.getState(address.getStateid()) == null){
            throw new AddressNotFoundException("ANF-002", "No state by this id");
        }


    }


}
