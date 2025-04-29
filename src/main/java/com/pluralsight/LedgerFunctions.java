package com.pluralsight;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class LedgerFunctions {

    // Creates a scanner under the variable name "input"
    static Scanner input = new Scanner(System.in);

    //
    public static void viewLedger() {
        while (true) {
            System.out.println("\n══║ WELCOME TO THE LEDGER ║══");
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
                    ArrayList<Transactions> transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions:");
                    for (Transactions t : transactions) {
                        System.out.println(t);
                    }
                    break;
                case 'D':
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions:");
                    for (Transactions t : transactions) {
                        if (t.getAmount() > 0) {
                            System.out.println(t);
                        }
                    }
                    break;
                case 'P':
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions:");
                    for (Transactions t : transactions) {
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
            System.out.println("\n   ══║ LEDGER REPORTS ║══");
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
                    ArrayList<Transactions> transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the beginning of the month: ");
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getMonth() == now.getMonth() && actionDate.getYear() == now.getYear())
                        { System.out.println(t); }
                    }
                    break;
                case 2:
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the previous month: ");
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getMonth() == now.minusMonths(1).getMonth()
                                && actionDate.getYear() == now.minusMonths(1).getYear());
                        { System.out.println(t); }
                    }
                    break;
                case 3:
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the current year: ");
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getYear() == now.getYear())
                        { System.out.println(t); }
                    }
                    break;
                case 4:
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are your transactions from the previous year: ");
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getYear() == now.minusMonths(1).getYear())
                        { System.out.println(t); }
                    }
                    break;
                case 5:
                    System.out.println("Please enter the vendor/name: ");
                    String vendorChoice = input.nextLine().trim();
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("Here are the transactions for vendor: " + vendorChoice);
                    for (Transactions t : transactions) {
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