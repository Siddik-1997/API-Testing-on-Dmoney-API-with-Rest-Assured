package testrunner;

import controller.User;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class UserTestRunner extends Setup {
    User user;

    // Negative Test Case
    @Test(priority = 1, description = "calling login API for invalid Password")
    public void doLoginWithInvalidCred() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.callLoginAPI("salman@roadtocareer.net", "12345");
        String message = jsonResponse.get("message");
        System.out.println(message);

        // Assertion
        Assert.assertTrue(message.contains("Password incorrect"));
    }

    // Positive Test Case
    @Test(priority = 2, description = "calling login API for valid user")
    public void doLoginWithValidCred() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse = user.callLoginAPI("salman@roadtocareer.net", "1234");
        String token = jsonResponse.get("token");
        String message = jsonResponse.get("message");
        Utils.setEnvVariable("token", token);
        System.out.println(token);
        System.out.println(message);

        // Assertion
        Assert.assertTrue(message.contains("Login successfully"));

    }

    // Negative Test Case
    @Test(priority = 3, description = "calling API for creating user with existing information")
    public void createUserWithExistingInfo() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.createUser("Jordan Stroman MD", "alvaro.ryan@yahoo.com", "1234", "01700211618", "1100154448", "Customer");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("already exists"));
    }

    // Positive Test Case
    @Test(priority = 4, description = "calling API for creating new user: Customer and Agent.")
    public void createUser() throws ConfigurationException, IOException {
        user = new User();
        Utils utils = new Utils();
        utils.generateRandomUser();
        JsonPath jsonResponse = user.createUser(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "1100154448", "Customer");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");
        Utils.setEnvVariable("Customer_id", jsonResponse.get("user.id").toString());
        Utils.setEnvVariable("Customer_name", jsonResponse.get("user.name"));
        Utils.setEnvVariable("Customer_email", jsonResponse.get("user.email"));
        Utils.setEnvVariable("Customer_phone", jsonResponse.get("user.phone_number"));

        // Assertion
        Assert.assertTrue(message.contains("User created"));

        utils.generateRandomUser();
        JsonPath jsonResponse1 = user.createUser(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "1100154450", "Agent");
        System.out.println(jsonResponse1.get().toString());
        String message1 = jsonResponse1.get("message");
        Utils.setEnvVariable("Agent_id", jsonResponse1.get("user.id").toString());
        Utils.setEnvVariable("Agent_name", jsonResponse1.get("user.name"));
        Utils.setEnvVariable("Agent_email", jsonResponse1.get("user.email"));
        Utils.setEnvVariable("Agent_phone", jsonResponse1.get("user.phone_number"));

        // Assertion
        Assert.assertTrue(message1.contains("User created"));
    }

    // Negative Test Case
    @Test(priority = 5, description = "calling API for searching customer by invalid phone number")
    public void searchCustomerByInValidPhoneNumber() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.searchCustomerByPhoneNumber("01700211619");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("User not found"));
    }

    // Positive Test Case
    @Test(priority = 6, description = "calling API for searching customer by valid Phone number")
    public void searchCustomerByValidPhoneNumber() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.searchCustomerByPhoneNumber(prop.getProperty("Customer_phone"));
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("User found"));
    }
}
