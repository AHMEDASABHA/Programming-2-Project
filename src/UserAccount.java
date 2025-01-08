import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private final String accountNumber;
    private final String fullName;
    private final String phoneNumber;
    private String password;
    private double balance;
    private final List<String> transactionHistory;
    private int failedLoginAttempts;
    private boolean accountLocked;

    public UserAccount(String accountNumber, String fullName, String phoneNumber, String password, double balance) {
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        this.failedLoginAttempts = 0;
        this.accountLocked = false;
    }

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

    public double checkBalance() {
        return balance;
    }

    public void viewTransactionHistory() {
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
}