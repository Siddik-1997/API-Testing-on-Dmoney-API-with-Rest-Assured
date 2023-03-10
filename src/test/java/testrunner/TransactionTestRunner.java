package testrunner;

import controller.Transaction;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.testng.annotations.Test;
import setup.Setup;

import java.io.IOException;

public class TransactionTestRunner extends Setup {
    Transaction transaction;

    // Negative Test Case
    @Test(priority = 1,description = "calling API for deposit money from system to invalid Agent")
    public void depositToInvalidAgent() throws IOException {
        transaction=new Transaction();
        transaction.depositToInvalidAgent();
    }

    // Negative Test Case
    @Test(priority = 2,description = "calling API for deposit insufficient balance: 0 TK from System to Agent")
    public void depositToAgentWithInsufficientBalance() throws IOException {
        transaction=new Transaction();
        transaction.depositToAgentWithInsufficientBalance();
    }

    // Positive Test Case
    @Test(priority = 3,description = "calling API for deposit 5000 TK from System to valid Agent")
    public void depositToValidAgentWithSufficientBalance() throws IOException, ConfigurationException {
        transaction=new Transaction();
        transaction.depositToValidAgent();
    }

    // Negative Test Case
    @Test(priority = 4, description = "calling API for deposit money from Agent to invalid customer")
    public void depositToInvalidCustomer() throws IOException {
        transaction=new Transaction();
        transaction.depositToInValidCustomer();
    }

    // Negative Test Case
    @Test(priority = 5, description = "calling API for deposit insufficient balance: 0 TK from Agent to customer")
    public void depositToCustomerWithInsufficientBalance() throws IOException {
        transaction=new Transaction();
        transaction.depositToCustomerWithInsufficientBalance();
    }

    // Positive Test Case
    @Test(priority = 6, description = "calling API for deposit 2000 TK from Agent to Customer")
    public void depositToCustomerWithSufficientBalance() throws IOException, ConfigurationException {
        transaction=new Transaction();
        transaction.depositToValidCustomer();
    }

    // Negative Test Case
    @Test(priority = 7, description = "calling API for searching balance with invalid Phone Number")
    public void checkCustomerBalanceWithInvalidPhoneNumber() throws IOException {
        transaction=new Transaction();
        transaction.checkCustomerBalanceByInvalidPhoneNumber();
    }

    // Positive Test Case
    @Test(priority = 8, description = "calling API for searching balance with valid Phone number")
    public void checkCustomerBalanceWithValidPhoneNumber() throws IOException {
        transaction=new Transaction();
        transaction.checkCustomerBalanceByValidPhoneNumber();
    }

    // Negative Test Case
    @Test(priority = 9, description = "calling API for checking statement of user with invalid Transaction ID")
    public void checkStatementByInvalidTransactionId() throws IOException {
        transaction=new Transaction();
        transaction.checkStatementByInvalidTransactionId();
    }

    // Positive Test Case
    @Test(priority = 10, description = "calling API for checking statement of user with valid Transaction ID")
    public void checkStatementByValidTransactionId() throws IOException {
        transaction=new Transaction();
        transaction.checkStatementByValidTransactionId();
    }

    // Negative Test Case
    @Test(priority = 11, description = "calling API for withdraw money from Customer to invalid Agent")
    public void withdrawByCustomerToInvalidAgent() throws IOException {
        transaction=new Transaction();
        transaction.withdrawByCustomerToInvalidAgent();
    }

    // Negative Test Case
    @Test(priority = 12, description = "calling API for withdraw insufficient balance: 0 TK from Customer to Agent ")
    public void withdrawByCustomerWithInsufficientBalance() throws IOException {
        transaction=new Transaction();
        transaction.withdrawByCustomerWithInsufficientBalance();
    }

    // Positive Test Case
    @Test(priority = 13, description = "calling API for withdraw 1000 TK from customer to Agent")
    public void withdrawByCustomerWithSufficientBalance() throws IOException {
        transaction=new Transaction();
        transaction.withdrawByCustomerWithSufficientBalance();

        //Assertion
        Assert.assertEquals(990,transaction.getCurrentBalance());
    }

    // Negative Test Case
    @Test(priority = 14, description = "calling API for sending money from one customer to another invalid customer")
    public void sendToInvalidCustomer() throws IOException {
        transaction=new Transaction();
        transaction.sendToInvalidCustomer();
    }

    // Negative Test Case
    @Test(priority = 15, description = "calling API for sending insufficient balance: 0 TK from one customer to another.")
    public void sendToCustomerWithInsufficientBalance() throws IOException {
        transaction=new Transaction();
        transaction.sendToCustomerWithInsufficientBalance();
    }

    // Positive Test Case
    @Test(priority = 16, description = "calling API for sending 500 TK from one customer to another valid customer.")
    public void sendToValidCustomer() throws IOException {
        transaction=new Transaction();
        transaction.sendToValidCustomerWithSufficientBalance();

        //Assertion
        Assert.assertEquals(485,transaction.getCurrentBalance());
    }

    // Negative Test Case
    @Test(priority = 17, description = "calling API for checking customer statement by invalid Phone number")
    public void checkCustomerStatementWithInvalidPhoneNumber() throws IOException {
        transaction=new Transaction();
        transaction.checkCustomerStatementWithInvalidPhoneNumber();
    }

    // Positive Test Case
    @Test(priority = 18, description = "calling API for checking customer statement by valid Phone number")
    public void checkCustomerStatement() throws IOException {
        transaction=new Transaction();
        transaction.checkCustomerStatementWithValidPhoneNumber();
    }
}
