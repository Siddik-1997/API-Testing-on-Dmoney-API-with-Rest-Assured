package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import model.TransactionModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
@Getter
@Setter

public class Transaction extends Setup {
    public Transaction() throws IOException {
        initConfig();

    }
    private int currentBalance;
    public void depositToInvalidAgent() {
        RestAssured.baseURI = prop.getProperty("base_url");
        TransactionModel transactionModel=new TransactionModel("SYSTEM","01578475874",5000);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }


    public void depositToAgentWithInsufficientBalance() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        TransactionModel transactionModel=new TransactionModel("SYSTEM",agentPhoneNumber,0);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void depositToValidAgent() throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        TransactionModel transactionModel=new TransactionModel("SYSTEM",agentPhoneNumber,5000);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());
        Utils.setEnvVariable("Agent_TrnxId", jsonResponse.get("trnxId").toString());

    }

    public void depositToInValidCustomer() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        TransactionModel transactionModel=new TransactionModel(agentPhoneNumber,"01521332322",2000);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }


    public void depositToCustomerWithInsufficientBalance() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(agentPhoneNumber,customerPhoneNumber,5);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void depositToValidCustomer() throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(agentPhoneNumber,customerPhoneNumber,2000);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());
        Utils.setEnvVariable("Customer_TrnxId", jsonResponse.get("trnxId").toString());

    }

    public void checkCustomerBalanceByInvalidPhoneNumber() {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/balance/01521332777")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void checkCustomerBalanceByValidPhoneNumber() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String PhoneNumber=prop.getProperty("Customer_phone");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/balance/"+PhoneNumber)
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void checkStatementByInvalidTransactionId() {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/search/TXN130111")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void checkStatementByValidTransactionId() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String TransactionId=prop.getProperty("Customer_TrnxId");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/search/"+TransactionId)
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void withdrawByCustomerToInvalidAgent() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(customerPhoneNumber,"01521332333",1000);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void withdrawByCustomerWithInsufficientBalance() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(customerPhoneNumber,agentPhoneNumber,5);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void withdrawByCustomerWithSufficientBalance() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String agentPhoneNumber=prop.getProperty("Agent_phone");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(customerPhoneNumber,agentPhoneNumber,1000);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());
        int currentBalance = jsonResponse.get("currentBalance");
        setCurrentBalance(currentBalance);


    }
    public void sendToInvalidCustomer() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(customerPhoneNumber,"01700000008",0);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }
    public void sendToCustomerWithInsufficientBalance() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(customerPhoneNumber,"01700211618",0);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void sendToValidCustomerWithSufficientBalance() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String customerPhoneNumber=prop.getProperty("Customer_phone");
        TransactionModel transactionModel=new TransactionModel(customerPhoneNumber,"01700211618",500);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());
        int currentBalance = jsonResponse.get("currentBalance");
        setCurrentBalance(currentBalance);

    }

    public void checkCustomerStatementWithInvalidPhoneNumber() {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/statement/01700003384")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }

    public void checkCustomerStatementWithValidPhoneNumber() {
        RestAssured.baseURI = prop.getProperty("base_url");
        String PhoneNumber=prop.getProperty("Customer_phone");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/statement/"+PhoneNumber)
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonResponse = res.jsonPath();
        System.out.println(jsonResponse.get().toString());

    }
}
