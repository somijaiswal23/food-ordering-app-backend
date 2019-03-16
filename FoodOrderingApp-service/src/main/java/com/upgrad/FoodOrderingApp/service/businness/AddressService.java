package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Autowired
    private StateDao stateDao;

    /**
     * This method implements the business logic for 'save address' endpoint
     *
     * @param addressEntity new address will be created from given AddressEntity object
     * @param customerEntity
     *
     * @return AddressEntity object
     *
     * @throws SaveAddressException exception if any of the validation fails on customer details
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, CustomerEntity customerEntity) throws SaveAddressException {

        // Validation for required fields
        if (
            addressEntity.getFlatNumber().equals("") ||
            addressEntity.getLocality().equals("") ||
            addressEntity.getCity().equals("") ||
            addressEntity.getPincode().equals("") ||
            addressEntity.getStateid() == null ||
            addressEntity.getActive() == null
        ) {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }

        // Validation for format of Pincode
        // This is to validate Indian Post code.
        // [1-9]: Matches exactly one digit from 1 to 9.
        // [0-9]{5}: Matches exactly five digits in the inclusive range 0-9
        if(!addressEntity.getPincode().matches("^[1-9][0-9]{5}$")){
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }

//        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
//        customerAddressEntity.setCustomer(customerEntity);
//        customerAddressEntity.setAddress(addressEntity);
//        customerAddressDao.createCustomerAddress(customerAddressEntity);

        return addressDao.createAddress(addressEntity);
    }

    public StateEntity getStateByUUID(String stateUUID) throws AddressNotFoundException {
        StateEntity stateEntity = stateDao.getStateByUUID(stateUUID);
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002", "No state by this state id");
        }
        return stateEntity;
    }

    /**
     * This method implements the business logic for 'Get All Saved Address' endpoint
     */
    public List<AddressEntity> getAllAddresses() {
        return addressDao.getAllAddresses();
    }

    /**
     * This method returns State Name
     */
    public StateEntity getstateEntity(StateEntity id){
        return stateDao.getStateByUUID(id.toString());
    }

    /**
     * This method implements the business logic for 'Get All States' endpoint
     */
    public List<StateEntity> getAllStates() {
        return stateDao.getAllStates();
    }

    /**
     * This method implements the business logic for 'Get Address by Id' endpoint
     */
    public AddressEntity getAddressById(String id) {
         return addressDao.getAddressById(id);
    }
}
