package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    /**
     * This method implements the business logic for 'signup' endpoint
     *
     * @param customerEntity new customer will be created from given CustomerEntity object
     *
     * @return CustomerEntity object
     *
     * @throws SignUpRestrictedException if any of the validation fails on customer details
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {

        // validation for required fields
        if (
                customerEntity.getUuid().equals("") ||
                customerEntity.getFirstName().equals("") ||
                customerEntity.getEmail().equals("") ||
                customerEntity.getContactNumber().equals("") ||
                customerEntity.getPassoword().equals("")
        ) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }

        // validation for unique contact number
        if (customerDao.getCustomerByContactNumber(customerEntity.getContactNumber()) != null) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
        }

        // validation for email id format
        if (!customerEntity.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}")) {
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }

        // validation for contact number format
        if (!customerEntity.getContactNumber().matches("^[0][1-9]\\d{9}$|^[1-9]\\d{9}")) {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }

        // validation for password strength
        if (!customerEntity.getPassoword().matches("^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@$%&*!^-]).{8,}$")) {
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }

        // encrypt salt and password
        String[] encryptedText = passwordCryptographyProvider.encrypt(customerEntity.getPassoword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassoword(encryptedText[1]);

        return customerDao.createCustomer(customerEntity);
    }
}
