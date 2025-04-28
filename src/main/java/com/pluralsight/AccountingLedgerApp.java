package com.pluralsight;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
public class AccountingLedgerApp {
    // Creates a scanner under the variable name "input"
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Begins a loop that will run through the options on the home screen and back
        while (true) {
            // The home screen that asks for the user to input what they would like to do
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║  Asteway's Financial Services  ║");  // Outline design from ChatGPT 4o
            System.out.println("║   & Accounting Ledger System   ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("Welcome! What would you like to do?");
            System.out.println("======== D - Make Deposit  ========");
            System.out.println("======== P - Make Payment ========");
            System.out.println("======== L - View Ledger  ========");
            System.out.println("======== X - Exit         ========");
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
            switch (homeScreenInput) { // Make old style if needed to put breaks/continue within
                case 'D' -> makeDeposit();
                case 'P' -> makePayment();
                case 'L' -> viewLedger();
                case 'X' -> System.out.println("Thank you for using Asteway's Financial Services. Come again!");
                default -> System.out.println("Invalid option! Please try again.");
            }
            break;
        }
    }
    //
    public static void makeDeposit() {
        System.out.println("Please enter your name: ");
        String vendor = input.nextLine().trim();
        System.out.println("Please provide a description for this deposit: ");
        String description = input.nextLine().trim();
        System.out.println("Please enter the amount you would like to deposit: ");
        double amount = input.nextDouble();
        amount = (amount > 0) ? amount : -1 * amount;
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Transaction depositTransaction = new Transaction(date, time, description, vendor, amount);
        LedgerFunctions.writeTransactionToFile(depositTransaction, "src/main/resources/transactions.csv");
        System.out.println("Deposit made successfully!" +
                "\nPlease allow up to 24 hours for it to\nreflect on any statement documentations.");
    }
    //
    public static void makePayment() {
        System.out.println("Please enter the vendor's name: ");
        String vendor = input.nextLine().trim();
        System.out.println("Please provide a description for this payment: ");
        String description = input.nextLine().trim();
        System.out.println("Please enter the payment amount: ");
        double amount = input.nextDouble();
        amount = (amount > 0) ? -1 * amount : amount;
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Transaction paymentTransaction = new Transaction(date, time, description, vendor, amount);
        LedgerFunctions.writeTransactionToFile(paymentTransaction, "src/main/resources/transactions.csv");
        System.out.println("Payment made successfully!" +
                "\nPlease allow up to 24 hours for it to\nreflect on any statement documentations.");
    }
    //
    public static void viewLedger() {
        ArrayList<Transaction> transactions = LedgerFunctions.readTransactionsFromFile("src/main/resources/transactions.csv");
        System.out.println("Here are your transactions:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}

