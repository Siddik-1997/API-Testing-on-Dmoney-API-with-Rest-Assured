package testrunner;

import controller.Transaction;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class TransactionTestRunner extends Setup {
    Transaction transaction;

    // Negative Test Case
    @Test(priority = 1, description = "calling API for deposit money from system to invalid Agent")
    public void depositToInvalidAgent() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.depositMoney("SYSTEM", "01521332777", 5000);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Account does not exist"));
    }

    // Negative Test Case
    @Test(priority = 2, description = "calling API for deposit insufficient balance: 0 TK from System to Agent")
    public void depositToAgentWithInsufficientBalance() throws IOException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        JsonPath jsonResponse = transaction.depositMoney("SYSTEM", agentAccount, 0);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Minimum deposit amount 10 tk and maximum deposit amount 10000 tk"));
    }

    // Positive Test Case
    @Test(priority = 3, description = "calling API for deposit 5000 TK from System to valid Agent")
    public void depositToValidAgentWithSufficientBalance() throws IOException, ConfigurationException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        JsonPath jsonResponse = transaction.depositMoney("SYSTEM", agentAccount, 5000);
        System.out.println(jsonResponse.get().toString());
        Utils.setEnvVariable("Agent_TrnxId", jsonResponse.get("trnxId").toString());

        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Deposit successful"));
    }

    // Negative Test Case
    @Test(priority = 4, description = "calling API for deposit money from Agent to invalid customer")
    public void depositToInvalidCustomer() throws IOException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        JsonPath jsonResponse = transaction.depositMoney(agentAccount, "01521332777", 2000);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Account does not exist"));
    }

    // Negative Test Case
    @Test(priority = 5, description = "calling API for deposit insufficient balance: 0 TK from Agent to customer")
    public void depositToCustomerWithInsufficientBalance() throws IOException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.depositMoney(agentAccount, customerAccount, 0);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Minimum deposit amount 10 tk and maximum deposit amount 10000 tk"));
    }

    // Positive Test Case
    @Test(priority = 6, description = "calling API for deposit 2000 TK from Agent to Customer")
    public void depositToCustomerWithSufficientBalance() throws IOException, ConfigurationException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.depositMoney(agentAccount, customerAccount, 2000);
        System.out.println(jsonResponse.get().toString());
        Utils.setEnvVariable("Customer_TrnxId", jsonResponse.get("trnxId").toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Deposit successful"));
    }

    // Negative Test Case
    @Test(priority = 7, description = "calling API for searching balance with invalid Phone Number")
    public void checkCustomerBalanceWithInvalidPhoneNumber() throws IOException {
        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkCustomerBalance("01521332777");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("User not found"));
    }

    // Positive Test Case
    @Test(priority = 8, description = "calling API for searching balance with valid Phone number")
    public void checkCustomerBalanceWithValidPhoneNumber() throws IOException {
        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkCustomerBalance(prop.getProperty("Customer_phone"));
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("User balance"));
    }

    // Negative Test Case
    @Test(priority = 9, description = "calling API for checking statement of user with invalid Transaction ID")
    public void checkStatementByInvalidTransactionId() throws IOException {
        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkStatement("TXN130111");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Transaction not found"));

    }

    // Positive Test Case
    @Test(priority = 10, description = "calling API for checking statement of user with valid Transaction ID")
    public void checkStatementByValidTransactionId() throws IOException {
        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkStatement(prop.getProperty("Customer_TrnxId"));
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Transaction list"));

    }

    // Negative Test Case
    @Test(priority = 11, description = "calling API for withdraw money from Customer to invalid Agent")
    public void withdrawByCustomerToInvalidAgent() throws IOException {
        transaction = new Transaction();
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.withdrawBalance(customerAccount, "01521332777", 1000);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Account does not exist"));
    }

    // Negative Test Case
    @Test(priority = 12, description = "calling API for withdraw insufficient balance: 0 TK from Customer to Agent ")
    public void withdrawByCustomerWithInsufficientBalance() throws IOException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.withdrawBalance(customerAccount, agentAccount, 0);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        // Assertion
        Assert.assertTrue(message.contains("Minimum withdraw amount 10 tk"));
    }

    // Positive Test Case
    @Test(priority = 13, description = "calling API for withdraw 1000 TK from customer to Agent")
    public void withdrawByCustomerWithSufficientBalance() throws IOException {
        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.withdrawBalance(customerAccount, agentAccount, 1000);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");
        int currentBalance = jsonResponse.get("currentBalance");

        // Assertion
        Assert.assertTrue(message.contains("Withdraw successful"));
        Assert.assertEquals(990, currentBalance);
    }

    // Negative Test Case
    @Test(priority = 14, description = "calling API for sending money from one customer to another invalid customer")
    public void sendToInvalidCustomer() throws IOException {
        transaction = new Transaction();
        String customerPhoneNumber = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.sendBalance(customerPhoneNumber, "01521332777", 500);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");


        //Assertion
        Assert.assertTrue(message.contains("From/To Account does not exist"));
    }

    // Negative Test Case
    @Test(priority = 15, description = "calling API for sending insufficient balance: 0 TK from one customer to another.")
    public void sendToCustomerWithInsufficientBalance() throws IOException {
        transaction = new Transaction();
        String customerPhoneNumber = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.sendBalance(customerPhoneNumber, "01700211618", 0);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");


        //Assertion
        Assert.assertTrue(message.contains("Minimum amount 10 tk"));
    }

    // Positive Test Case
    @Test(priority = 16, description = "calling API for sending 500 TK from one customer to another valid customer.")
    public void sendToValidCustomerWithSufficientBalance() throws IOException {
        transaction = new Transaction();
        String customerPhoneNumber = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.sendBalance(customerPhoneNumber, "01700211618", 500);
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");
        int currentBalance = jsonResponse.get("currentBalance");


        //Assertion
        Assert.assertTrue(message.contains("Send money successful"));
        Assert.assertEquals(485, currentBalance);
    }

    // Negative Test Case
    @Test(priority = 17, description = "calling API for checking customer statement by invalid Phone number")
    public void checkCustomerStatementWithInvalidPhoneNumber() throws IOException {
        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkCustomerStatement("01521332777");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        //Assertion
        Assert.assertTrue(message.contains("User not found"));
    }

    // Positive Test Case
    @Test(priority = 18, description = "calling API for checking customer statement by valid Phone number")
    public void checkCustomerStatement() throws IOException {
        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkCustomerStatement(prop.getProperty("Customer_phone"));
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        //Assertion
        Assert.assertTrue(message.contains("Transaction list"));
    }
}
