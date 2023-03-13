package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import model.TransactionModel;
import setup.Setup;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Getter
@Setter

public class Transaction extends Setup {
    public Transaction() throws IOException {
        initConfig();

    }

    public JsonPath depositMoney(String fromAccount, String toAccount, int amount) {
        RestAssured.baseURI = prop.getProperty("base_url");
        TransactionModel transactionModel = new TransactionModel(fromAccount, toAccount, amount);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit");

        return res.jsonPath();

    }

    public JsonPath checkCustomerBalance(String PhoneNumber) {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/balance/" + PhoneNumber);

        return res.jsonPath();
    }


    public JsonPath checkStatement(String TransactionId) {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/search/" + TransactionId);
        return res.jsonPath();


    }

    public JsonPath withdrawBalance(String fromAccount, String toAccount, int amount) {
        RestAssured.baseURI = prop.getProperty("base_url");
        TransactionModel transactionModel = new TransactionModel(fromAccount, toAccount, amount);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/withdraw");

        return res.jsonPath();

    }

    public JsonPath sendBalance(String fromAccount, String toAccount, int amount) {
        RestAssured.baseURI = prop.getProperty("base_url");
        TransactionModel transactionModel = new TransactionModel(fromAccount, toAccount, amount);

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/sendmoney");

        return res.jsonPath();

    }

    public JsonPath checkCustomerStatement(String PhoneNumber) {
        RestAssured.baseURI = prop.getProperty("base_url");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("/transaction/statement/" + PhoneNumber);
        return res.jsonPath();


    }
}
