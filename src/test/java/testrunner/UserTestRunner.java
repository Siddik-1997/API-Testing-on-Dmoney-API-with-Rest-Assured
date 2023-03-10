package testrunner;

import controller.User;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import setup.Setup;

import java.io.IOException;

public class UserTestRunner extends Setup {
    User user;

    // Negative Test Case
    @Test(priority = 1, description = "calling login API for invalid user")
    public void doLoginWithInvalidCred() throws IOException {
        user=new User();
        user.callLoginAPIWithInvalidCred();
    }

    // Positive Test Case
    @Test(priority = 2, description = "calling login API for valid user")
    public void doLoginWithValidCred() throws ConfigurationException, IOException {
        user=new User();
        user.callLoginAPIWithValidCred();

    }

    // Negative Test Case
    @Test(priority = 3, description = "calling API for creating user with existing information")
    public void createUserWithExistingInfo() throws IOException {
        user=new User();
        user.createUserWithExistingInfo();
    }

    // Positive Test Case
    @Test(priority = 4, description = "calling API for creating new user: Customer and Agent.")
    public void createUser() throws ConfigurationException, IOException {
        user=new User();
        user.createCustomer();
        user.createAgent();
    }

    // Negative Test Case
    @Test(priority = 5,description = "calling API for searching customer by invalid phone number")
    public void searchCustomerByInValidPhoneNumber() throws IOException {
        user=new User();
        user.searchCustomerByInvalidPhone_Number();
    }

    // Positive Test Case
    @Test(priority = 6,description = "calling API for searching customer by valid Phone number")
    public void searchCustomerByValidPhoneNumber() throws IOException {
        user=new User();
        user.searchCustomerByValidPhone_Number();
    }
}
