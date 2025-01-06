import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {

    public static Map<String, UserAccount> readUsersData(String filePath) {
        Map<String, UserAccount> userAccounts = new HashMap<>();
        File file = new File(filePath);


        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. Starting with an empty user database.");
        }


        return userAccounts;
    }

    public static void writeUsersData(String filePath, Map<String, UserAccount> userAccounts) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (UserAccount user : userAccounts.values()) {
                writer.printf("%s,%s,%s,%s,%.2f%n",
                        user.getAccountNumber(),
                        user.getFullName(),
                        user.getPhoneNumber(),
                        user.getPassword(),
                        user.getBalance());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing user data to file: " + e.getMessage());
        }
    }

    public static void appendTransactionHistory(String accountNumber, String transaction) {
        String filePath = "transaction_history.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(accountNumber + ": " + transaction);
        } catch (IOException e) {
            System.out.println("Error appending transaction record to file: " + e.getMessage());
        }
    }
}