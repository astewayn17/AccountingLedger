package com.pluralsight;
// Tells java to bring in the LocalDate java.time package, the array list utility to use an array list and the scanner -
// to record input so it can be used in the code. ----------------------------------------------------------------------
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
// =====================================================================================================================
public class LedgerFunctions {

    // Creates a static Scanner object named "input" that can be used anywhere in the class without creating an instance
    static Scanner input = new Scanner(System.in);

    // Method called viewLedger that will handle ledger menu functions =================================================
    public static void viewLedger() {
        // Begins a loop that will run through the options on the ledger home screen and back --------------------------
        while (true) {
            System.out.println("\n══║ WELCOME TO THE LEDGER ║══");
            System.out.println("What would you like to do view?");
            System.out.println("======== A - All      ========");
            System.out.println("======== D - Deposit  ========");
            System.out.println("======== P - Payments ========");
            System.out.println("======== R - Reports  ========");
            System.out.println("======== H - Home Screen =====");
            System.out.print("\nPlease enter a letter (A, D, P, R, or H): ");
            String prelimLedgerScreenInput = input.nextLine().toUpperCase().trim();
            // If the preliminary input is not equal to 1, then it will throw an error and restart the loop ------------
            if (prelimLedgerScreenInput.length() != 1) {
                System.out.println("\nInvalid option! Please enter only one letter (A, D, L, X, or H).");
                continue;
            }
            char ledgerScreenInput = prelimLedgerScreenInput.charAt(0);
            // The character at the first element of the string will be what the switch condition runs through ---------
            switch (ledgerScreenInput) {
                // If the user picks 'A', they will be able to view all transactions using the readTransactionsFromFile
                // method, which loads them from the .csv file and cycles through them using a for each loop and prints
                // them to the console. Which then asks the user to click 'enter' if they want to return to the ledger.
                case 'A':
                    ArrayList<Transactions> transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("\nHere are all of your transactions:\n");
                    for (Transactions t : transactions) {
                        System.out.println(t);
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Menu...");
                    input.nextLine();
                    break;
                // Same as case A, but only shows deposits by using a conditional that makes sure the amounts are ------
                // positive numbers using the getAmount getter ---------------------------------------------------------
                case 'D':
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("\nHere are your deposit transactions:\n");
                    for (Transactions t : transactions) {
                        if (t.getAmount() > 0) {
                            System.out.println(t);
                        }
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Menu...");
                    input.nextLine();
                    break;
                // Same as case A, but only shows payments by using a conditional that makes sure the amounts are ------
                // negative numbers using the getAmount getter ---------------------------------------------------------
                case 'P':
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    System.out.println("\nHere are your payment transactions:\n");
                    for (Transactions t : transactions) {
                        if (t.getAmount() < 0) {
                            System.out.println(t);
                        }
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Menu...");
                    input.nextLine();
                    break;
                // This will go to the reports screen and prompt the user there ----------------------------------------
                case 'R':
                    reports();
                    break;
                // Takes the user back to the home screen with a return statement --------------------------------------
                case 'H':
                    System.out.println("\nReturning to the home screen...");
                    return;
                // If none of the cases above are met, this error will print -------------------------------------------
                default:
                    System.out.println("\nInvalid option! Please enter a letter and try again.");
            }
        }
    }

    // This is the reports method that will be used when the user wants to see it ======================================
    public static void reports() {
        // Begins a loop that will run through the options on the ledger reports screen and back -----------------------
        while (true) {
            // Initializing reportChoice outside the try and showing the reports screen which will also verify if the --
            // input for it is blank -----------------------------------------------------------------------------------
            int reportChoice;
            try {
                System.out.println("\n    ══║ LEDGER REPORTS ║══");
                System.out.println("What format would like to view?");
                System.out.println("~~~~~ 1 Month to Date    ~~~~~");
                System.out.println("~~~~~ 2 Previous Month   ~~~~~");
                System.out.println("~~~~~ 3 Year to Date     ~~~~~");
                System.out.println("~~~~~ 4 Previous Year    ~~~~~");
                System.out.println("~~~~~ 5 Search by Vendor ~~~~~");
                System.out.println("~~~~~ 6 Back             ~~~~~");
                System.out.print("\nPlease enter a number (1, 2, 3, 4, 5, or 6): ");
                String prelimReportChoice = input.nextLine().trim();
                if (prelimReportChoice.isEmpty()) {
                    System.out.println("\nInput cannot be blank! Please try again.");
                    continue;
                }
                // This will change the input of the user to a number and the catch will throw an error if anything else
                // is inputted other than a number ---------------------------------------------------------------------
                reportChoice = Integer.parseInt(prelimReportChoice);
            } catch (NumberFormatException r) {
                System.out.println("\nInvalid input! Please enter a number.");
                continue;
            }
            // Getting the current date under the variable 'now' to use later in the switch ----------------------------
            LocalDate now = LocalDate.now();
            // Beginning a switch based on the report choice input -----------------------------------------------------
            switch (reportChoice) {
                // If the user picks '1', they will be able to view all transactions using the readTransactionsFromFile
                // method, which loads them from the .csv file and cycles through them using a for each loop and prints
                // them to the console only if the month from the getter and the month now match and the years match. I
                // am also using LocalDate to parse the string from the getter for getDate to make it a date format.
                case 1:
                    ArrayList<Transactions> transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    boolean found = false;
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getMonth() == now.getMonth() && actionDate.getYear() == now.getYear()) {
                            if (!found) {
                                System.out.println("\nHere are your transactions from the beginning of the month: \n");
                            }
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("\nReturned no transactions for this month.");
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Reports Menu...");
                    input.nextLine();
                    break;
                // Similar to case 1, just using the minusMonths built in method to get the month previous -------------
                case 2:
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    found = false;
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getMonth() == now.minusMonths(1).getMonth() &&
                                actionDate.getYear() == now.minusMonths(1).getYear()) {
                            if (!found) {
                                System.out.println("\nHere are your transactions from the previous month: \n");
                            }
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("\nReturned no transactions from the previous month.");
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Reports Menu...");
                    input.nextLine();
                    break;
                // Similar to case 1, but this time, only considering that the year matches the current one ------------
                case 3:
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    found = false;
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getYear() == now.getYear()) {
                            if (!found) {
                                System.out.println("\nHere are your transactions for the current year: \n");
                            }
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("\nReturned no transactions for the current year.");
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Reports Menu...");
                    input.nextLine();
                    break;
                // Similar to case 1, just using the current year and subtracting that by one to compare to the previous
                case 4:
                    transactions = Transactions.readTransactionsFromFile
                            ("src/main/resources/transactions.csv");
                    found = false;
                    for (Transactions t : transactions) {
                        LocalDate actionDate = LocalDate.parse(t.getDate());
                        if (actionDate.getYear() == (now.getYear() - 1)) {
                            if (!found) {
                                System.out.println("\nHere are your transactions from the previous year: \n");
                            }
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("\nReturned no transactions from the previous year.");
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Reports Menu...");
                    input.nextLine();
                    break;
                // Similar to case 1, but this time, matches up a vendor name that the user inputs to find it in the ---
                // list. If the vendor name matches, it will print out a statement and that condition will close since
                // found will now be true and then just keeps printing any vendors that match --------------------------
                case 5:
                    System.out.print("\nPlease enter the vendor/name: ");
                    String vendorChoice = input.nextLine().trim();
                    transactions = Transactions.readTransactionsFromFile("src/main/resources/transactions.csv");
                    found = false;
                    for (Transactions t : transactions) {
                        if (t.getVendor().equalsIgnoreCase(vendorChoice)) {
                            if (!found) {
                                System.out.println("\nHere are the transactions for vendor/name: " + vendorChoice + "\n");
                            }
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("\nReturned no transactions for this vendor.");
                    }
                    System.out.print("\nPress 'Enter' to return to the Ledger Reports Menu...");
                    input.nextLine();
                    break;
                // Simply returns to the ledger screen -----------------------------------------------------------------
                case 6:
                    System.out.println("\nReturning to the ledger screen...");
                    return;
                // If none of the cases above are met, this error will print -------------------------------------------
                default:
                    System.out.println("\nInvalid option! Please try again.");
            }
        }
    }
}