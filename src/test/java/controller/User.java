package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import model.UserModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
@Getter
@Setter
public class User extends Setup {
    public User() throws IOException {
        initConfig();

    }

    public void callLoginAPIWithInvalidCred() {
        UserModel loginModel = new UserModel("salman@roadtocareer.net", "12345");

        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(401).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        String message = jsonResponse.get("message");
        System.out.println(message);
    }


    public void callLoginAPIWithValidCred() throws ConfigurationException {
        UserModel loginModel = new UserModel("salman@roadtocareer.net", "1234");
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        String token = jsonResponse.get("token");
        String message = jsonResponse.get("message");
        Utils.setEnvVariable("token", token);

        System.out.println(token);
        System.out.println(message);

    }

    public void createUserWithExistingInfo() {

        UserModel regModel = new UserModel("Jordan Stroman MD", "alvaro.ryan@yahoo.com", "1234", "01700211618", "1100154448", "Customer");
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(regModel)
                        .when()
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void createCustomer() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();
        UserModel regModel = new UserModel(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "1100154448", "Customer");
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(regModel)
                        .when()
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());
        Utils.setEnvVariable("Customer_id", jsonResponse.get("user.id").toString());
        Utils.setEnvVariable("Customer_name", jsonResponse.get("user.name"));
        Utils.setEnvVariable("Customer_email", jsonResponse.get("user.email"));
        Utils.setEnvVariable("Customer_phone", jsonResponse.get("user.phone_number"));

    }

    public void createAgent() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();
        UserModel regModel = new UserModel(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "1100154448", "Agent");
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(regModel)
                        .when()
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());
        Utils.setEnvVariable("Agent_id", jsonResponse.get("user.id").toString());
        Utils.setEnvVariable("Agent_name", jsonResponse.get("user.name"));
        Utils.setEnvVariable("Agent_email", jsonResponse.get("user.email"));
        Utils.setEnvVariable("Agent_phone", jsonResponse.get("user.phone_number"));

    }


    public void searchCustomerByInvalidPhone_Number() {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("user/search/Phonenumber/01700211619")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void searchCustomerByValidPhone_Number() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String PhoneNumber=prop.getProperty("Customer_phone");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/user/search/Phonenumber/"+PhoneNumber)
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

}