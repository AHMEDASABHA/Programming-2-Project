import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    private final Map<String, UserAccount> userAccounts;
    private static final String USER_DATA_FILE = "user_data.txt";

    public ATMSystem() {
        userAccounts = FileHandler.readUsersData(USER_DATA_FILE);
    }

    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            // From the internet as a way to generate random number of 7 digits
            accountNumber = String.format("%07d", random.nextInt(10000000));
        } while (userAccounts.containsKey(accountNumber));
        return accountNumber;
    }

    public void createNewAccount(Scanner scanner) {
        System.out.println("Enter Full Name:");
        String fullName = scanner.nextLine();
        System.out.println("Enter Phone Number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Set Password:");
        String password = scanner.nextLine();
        System.out.println("Enter Initial Balance (default is 0):");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();

        String accountNumber = generateUniqueAccountNumber();
        UserAccount newUser = new UserAccount(accountNumber, fullName, phoneNumber, password, initialBalance);
        userAccounts.put(accountNumber, newUser);
        FileHandler.writeUsersData(USER_DATA_FILE, userAccounts);

        System.out.println("Account created successfully! Your account number is: " + accountNumber + ", Be sure to save it..");
    }

    public UserAccount login(Scanner scanner) {
        System.out.println("Enter Account Number:");
        String accountNumber = scanner.nextLine();
        UserAccount user = userAccounts.get(accountNumber);
        if (user == null) {
            System.out.println("Account not found!");
            return null;
        }
        System.out.println("Enter Password:");
        String password = scanner.nextLine();


        if (user.isAccountLocked()) {
            System.out.println("Account is locked due to multiple failed login attempts");
            return null;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            user.setFailedLoginAttempts(0);
            user.setAccountLocked(false);
            return user;
        } else {
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);
            System.out.println("Incorrect password attempt  " + attempts + " of 3");
            if (attempts >= 3) {
                user.setAccountLocked(true);
                System.out.println("Account is locked due to multiple failed login attempts");
            }
            return null;
        }
    }

    public void changePassword(Scanner scanner) {
        System.out.println("Enter Account Number:");
        String accountNumber = scanner.nextLine();

        UserAccount user = userAccounts.get(accountNumber);
        if (user == null) {
            System.out.println("Account not found!");
            return;
        }
        if (user.isAccountLocked()) {
            System.out.println("Account is locked due to multiple failed login attempts");
            return;
        }
        System.out.println("Enter your old Password:");
        String password = scanner.nextLine();
        System.out.println("Enter your new Password:");
        String newPassword = scanner.nextLine();
        if (user.getPassword().equals(password)) {
            user.setPassword(newPassword);
            FileHandler.writeUsersData(USER_DATA_FILE, userAccounts);
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Current password is incorrect. Please try again.");
        }
        return;
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nATM System Main Menu:");
            System.out.println("1. Login");
            System.out.println("2. Change Password");
            System.out.println("3. Create New Account");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    UserAccount user = login(scanner);
                    if (user != null) {
                        userMenu(user, scanner);
                    }
                    break;
                case 2:
                    changePassword(scanner);
                    break;
                case 3:
                    createNewAccount(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to display the user menu after successful login
    private void userMenu(UserAccount user, Scanner scanner) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Deposit Funds");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    user.deposit(depositAmount);
                    FileHandler.writeUsersData(USER_DATA_FILE, userAccounts);
                    break;
                case 2:
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    user.withdraw(withdrawAmount);
                    FileHandler.writeUsersData(USER_DATA_FILE, userAccounts);
                    break;
                case 3:
                    System.out.println("Current Balance: " + user.checkBalance());
                    break;
                case 4:
                    user.viewTransactionHistory();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Main method to run the ATM system
    public static void main(String[] args) {
        ATMSystem atmSystem = new ATMSystem();
        atmSystem.mainMenu();
    }
}