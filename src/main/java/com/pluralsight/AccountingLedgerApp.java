package com.pluralsight;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
//
import static com.pluralsight.LedgerFunctions.viewLedger;
public class AccountingLedgerApp {

    // Creates a scanner under the variable name "input"
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        // Begins a loop that will run through the options on the home screen and back
        while (true) {
            // The home screen that asks for the user to input what they would like to do
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║  ASTEWAY'S FINANCIAL SERVICES  ║");  // Outline design from ChatGPT 4o
            System.out.println("║   & ACCOUNTING LEDGER SYSTEM   ║");
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
                continue; }
            // Then this will store the first character of the preliminary choice as a single character
            char homeScreenInput = prelimHomeScreenInput.charAt(0);
            // The letter selected will be run through the switch condition to execute what was desired
            switch (homeScreenInput) {
                case 'D': makeDeposit();
                    break;
                case 'P': makePayment();
                    break;
                case 'L': viewLedger();
                    break;
                case 'X': System.out.println("Thank you for using Asteway's Financial Services. Come again!");
                    break;
                default: System.out.println("Invalid option! Please try again.");
            }
        }
    }

    //
    public static void makeDeposit() {
        boolean makingADeposit = true;
        while (makingADeposit) {
            System.out.print("Please enter your name: ");
            String vendor = input.nextLine().trim();
            System.out.print("Please provide a deposit description: ");
            String description = input.nextLine().trim();
            System.out.print("Please enter the deposit amount: $");
            double amount = input.nextDouble();

            input.nextLine();

            amount = (amount > 0) ? amount : -1 * amount;

            LocalDateTime dateTime = LocalDateTime.now();
            String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Transactions depositTransaction = new Transactions(date, time, description, vendor, amount);

            Transactions.writeTransactionToFile(depositTransaction, "src/main/resources/transactions.csv");
            System.out.println("\nDeposit made successfully!");
            System.out.print("\nWhat would you like to do next? \nMake another deposit (D) or return (X): ");

            String prelimAfterDepositChoice = input.nextLine().toUpperCase().trim();
            if (prelimAfterDepositChoice.length() != 1) {
                System.out.println("Invalid option! Please enter only one letter (D or X).");
            }
            char afterDepositChoice = prelimAfterDepositChoice.charAt(0);

            switch (afterDepositChoice) {
                case 'D': continue;
                case 'X': makingADeposit = false; break;
                default: System.out.println("Invalid option! Please try again.");
            }
        }
    }

    //
    public static void makePayment() {
        boolean makingAPayment = true;
        while (makingAPayment) {
            System.out.print("Please enter the vendor's name: ");
            String vendor = input.nextLine().trim();
            System.out.print("Please provide a payment description: ");
            String description = input.nextLine().trim();
            System.out.print("Please enter the payment amount: $");
            double amount = input.nextDouble();
            input.nextLine();
            amount = (amount > 0) ? -1 * amount : amount;
            LocalDateTime dateTime = LocalDateTime.now();
            String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Transactions paymentTransaction = new Transactions(date, time, description, vendor, amount);
            Transactions.writeTransactionToFile(paymentTransaction, "src/main/resources/transactions.csv");
            System.out.println("\nPayment made successfully!");
            System.out.print("\nWhat would you like to do next? \nMake another payment (P) or return (X): ");
            String prelimAfterPaymentChoice = input.nextLine().toUpperCase().trim();
            if (prelimAfterPaymentChoice.length() != 1) {
                System.out.println("Invalid option! Please enter only one letter (P or X).");
            }
            char afterPaymentChoice = prelimAfterPaymentChoice.charAt(0);

            switch (afterPaymentChoice) {
                case 'D': continue;
                case 'X': makingAPayment = false; break;
                default: System.out.println("Invalid option! Please try again.");
            }
        }
    }
}

