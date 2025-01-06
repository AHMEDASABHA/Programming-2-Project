import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private final String accountNumber;
    private String fullName;
    private String phoneNumber;
    private String password;
    private double balance;
    private final List<String> transactionHistory;

    // Constructor
    public UserAccount(String accountNumber, String fullName, String phoneNumber, String password, double balance) {
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            String transaction = "Deposited: " + amount;
            transactionHistory.add(transaction);
            FileHandler.appendTransactionHistory(accountNumber, transaction);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            String transaction = "Withdrew: " + amount;
            transactionHistory.add(transaction);
            FileHandler.appendTransactionHistory(accountNumber, transaction);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    // Check balance method
    public double checkBalance() {
        return balance;
    }

    // View transaction history method
    public void viewTransactionHistory() {
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getFullName() {
        return fullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}