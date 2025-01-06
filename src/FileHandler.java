import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {

    // Method to read user data from a file and return a map of UserAccount objects
    public static Map<String, UserAccount> readUserData(String filePath) {
        Map<String, UserAccount> userAccounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line is formatted as:
                // accountNumber,fullName,phoneNumber,password,balance
                String[] userData = line.split(",");
                if (userData.length == 5) {
                    String accountNumber = userData[0];
                    String fullName = userData[1];
                    String phoneNumber = userData[2];
                    String password = userData[3];
                    double balance = Double.parseDouble(userData[4]);

                    UserAccount user = new UserAccount(accountNumber, fullName, phoneNumber, password, balance);
                    userAccounts.put(accountNumber, user);
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAccounts;
    }

    // Method to write user data to a file
    public static void writeUserData(String filePath, Map<String, UserAccount> userAccounts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (UserAccount user : userAccounts.values()) {
                String userData = String.join(",",
                        user.getAccountNumber(),
                        user.getFullName(),
                        user.getPhoneNumber(),
                        user.getPassword(),
                        String.valueOf(user.getBalance()));
                bw.write(userData);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to append a transaction record to the transaction history file
    public static void appendTransactionHistory(String accountNumber, String transaction) {
        String filePath = "transaction_history.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(accountNumber + ": " + transaction);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}