import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    private final Map<String, UserAccount> userAccounts;
    private static final String USER_DATA_FILE = "/Users/ahmedsabha/MyLife/University/Java Labs/Final Project/user_data.txt";
    private static final int ACCOUNT_NUMBER_LENGTH = 7;
    private static final int MAX_ACCOUNT_NUMBER = 9999999;
    private static final int MIN_ACCOUNT_NUMBER = 1000000;

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
        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        UserAccount user = userAccounts.get(accountNumber);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Invalid account number or password.");
            return null;
        }
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nATM System Main Menu:");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
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
                    createNewAccount(scanner);
                    break;
                case 3:
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