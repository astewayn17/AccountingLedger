package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
public class AccountingLedgerApp {
    // Creates a scanner under the variable name "input"
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        // Begins a loop that will run through the options on the home screen and back
        while (true) {
            // The home screen that asks for the user to input what they would like to do
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║  Asteway's Financial Services  ║");  // Outline design from ChatGPT 4o
            System.out.println("║   & Accounting Ledger System   ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("Welcome! What would you like to do?");
            System.out.println("======== D - Make Deposit ========");
            System.out.println("======== P - Make Payment ========");
            System.out.println("======== L - View Ledger  ========");
            System.out.println("======== X - Exit         ========");
            System.out.print("\nPlease enter a letter (D, P, L, or X): ");
            // Stores the preliminary choice in the home screen as a string in upper case and trimmed spaces
            String prelimHomeScreenInput = input.nextLine().toUpperCase().trim();

            // If the preliminary choice is greater than one character, it restarts the loop as an error
            if (prelimHomeScreenInput.length() != 1) {
                System.out.println("Invalid option! Please enter only one letter (D, P, L, or X).");
                continue;
            }
            // Then this will store the first character of the preliminary choice as a single character
            char homeScreenInput = prelimHomeScreenInput.charAt(0);
            // The letter selected will be run through the switch condition to execute what was desired
            switch (homeScreenInput) {
                case 'D':
                    makeDeposit();
                    break;
                case 'P':
                    makePayment();
                    break;
                case 'L':
                    viewLedger();
                    break;
                case 'X':
                    System.out.println("Thank you for using Asteway's Financial Services. Come again!");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    //
    public static void makeDeposit() {
        System.out.print("Please enter your name: ");
        String vendor = input.nextLine().trim();
        System.out.print("Please provide a description for this deposit: ");
        String description = input.nextLine().trim();
        System.out.print("Please enter the amount you would like to deposit: $");
        double amount = input.nextDouble();
        input.nextLine();
        amount = (amount > 0) ? amount : -1 * amount;
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Transaction depositTransaction = new Transaction(date, time, description, vendor, amount);
        LedgerFunctions.writeTransactionToFile(depositTransaction, "src/main/resources/transactions.csv");
        System.out.println("\nDeposit made successfully!");

    }

    //
    public static void makePayment() {
        System.out.print("Please enter the vendor's name: ");
        String vendor = input.nextLine().trim();
        System.out.print("Please provide a description for this payment: ");
        String description = input.nextLine().trim();
        System.out.print("Please enter the payment amount: $");
        double amount = input.nextDouble();
        input.nextLine();
        amount = (amount > 0) ? -1 * amount : amount;
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Transaction paymentTransaction = new Transaction(date, time, description, vendor, amount);
        LedgerFunctions.writeTransactionToFile(paymentTransaction, "src/main/resources/transactions.csv");
        System.out.println("\nPayment made successfully!");
    }

    //
    public static void viewLedger() {
        while (true) {
            System.out.println("\n══║ Welcome to the Ledger ║══");
            System.out.println("What would you like to do view?");
            System.out.println("======== A - All      =========");
            System.out.println("======== D - Deposit  ========");
            System.out.println("======== P - Payments ========");
            System.out.println("======== R - Reports  ========");
            System.out.println("======== H - Home Screen =====");
            System.out.print("\nPlease enter a letter (A, D, P, R, or H): ");

            String prelimLedgerScreenInput = input.nextLine().toUpperCase().trim();

            if (prelimLedgerScreenInput.length() != 1) {
                System.out.println("Invalid option! Please enter only one letter (A, D, L, X, or H).");
                continue;
            }
            char ledgerScreenInput = prelimLedgerScreenInput.charAt(0);

            switch (ledgerScreenInput) {
                case 'A':
                    ArrayList<Transaction> transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions:");
                    for (Transaction t : transactions) {
                        System.out.println(t);
                    }
                    break;
                case 'D':
                    transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions:");
                    for (Transaction t : transactions) {
                        if (t.getAmount() > 0) {
                            System.out.println(t);
                        }
                    }
                    break;
                case 'P':
                    transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions:");
                    for (Transaction t : transactions) {
                        if (t.getAmount() < 0) {
                            System.out.println(t);
                        }
                    }
                    break;
                case 'R':
                    reports();
                    break;
                case 'H':
                    System.out.println("Returning to the home screen...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    //
    public static void reports() {
        while (true) {
            System.out.println("\n   ══   Ledger Reports   ══");
            System.out.println("What format would like to view?");
            System.out.println("~~~~~ 1 Month to Date    ~~~~~");
            System.out.println("~~~~~ 2 Previous Month   ~~~~~");
            System.out.println("~~~~~ 3 Year to Date     ~~~~~");
            System.out.println("~~~~~ 4 Previous Year    ~~~~~");
            System.out.println("~~~~~ 5 Search by Vendor ~~~~~");
            System.out.println("~~~~~ 6 Back             ~~~~~");
            System.out.print("\nPlease enter a number (1, 2, 3, 4, 5, or 6): ");

            int reportChoice = input.nextInt();
            input.nextLine();
            LocalDate now = LocalDate.now();

            switch (reportChoice) {
                case 1:
                    ArrayList<Transaction> transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the beginning of the month: ");
                    for (Transaction t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getMonth() == now.getMonth() && actionDate.getYear() == now.getYear())
                        { System.out.println(t); }
                    }
                    break;
                case 2:
                    transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the previous month: ");
                    for (Transaction t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getMonth() == now.minusMonths(1).getMonth()
                                && actionDate.getYear() == now.minusMonths(1).getYear());
                            { System.out.println(t); }
                    }
                    break;
                case 3:
                    transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the current year: ");
                    for (Transaction t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getYear() == now.getYear())
                            { System.out.println(t); }
                    }
                    break;
                case 4:
                    transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the previous year: ");
                    for (Transaction t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getYear() == now.minusMonths(1).getYear())
                            { System.out.println(t); }
                    }
                    break;
                case 5:
                    System.out.println("Please enter the vendor/name: ");
                    String vendorChoice = input.nextLine().trim();
                    transactions = LedgerFunctions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are the transactions for vendor: " + vendorChoice);
                    for (Transaction t : transactions) {
                        if (t.getVendor().equalsIgnoreCase(vendorChoice))
                            { System.out.println(t); }
                    }
                    break;
                case 6:
                    System.out.println("Returning to the ledger screen...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}

