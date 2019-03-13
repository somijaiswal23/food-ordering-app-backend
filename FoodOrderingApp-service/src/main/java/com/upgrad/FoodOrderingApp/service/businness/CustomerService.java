package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

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

    /**
     * This method implements the business logic for 'login' endpoint
     *
     * @param username customer contact number
     * @param password customer password
     *
     * @return CustomerAuthEntity object
     *
     * @throws AuthenticationFailedException if any of the validation fails on customer authentication
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(String username, String password) throws AuthenticationFailedException {

        CustomerEntity customerEntity = customerDao.getCustomerByContactNumber(username);

        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }

        final String encryptedPassword = PasswordCryptographyProvider.encrypt(password, customerEntity.getSalt());

        if (encryptedPassword.equals(customerEntity.getPassoword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
            customerAuthEntity.setUuid(UUID.randomUUID().toString());
            customerAuthEntity.setCustomer(customerEntity);
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime expiresAt = now.plusHours(8);

            customerAuthEntity.setLoginAt(ZonedDateTime.now());
            customerAuthEntity.setExpiresAt(expiresAt);
            customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));

            return customerDao.createCustomerAuth(customerAuthEntity);
        } else {
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }
    }
}
