package com.pluralsight;
// Importing the Scanner for input, the FileReader and FileWriter
// to connect to the files and Buffer to efficiently read/write
import java.util.Scanner;
import java.io.FileReader; import java.io.FileWriter;
import java.io.BufferedReader; import java.io.BufferedWriter;
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
            System.out.println("======== D - Add Deposit  ========");
            System.out.println("======== P - Make Payment ========");
            System.out.println("======== L - View Ledger  ========");
            System.out.println("======== X - Exit         ========");
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
                case 'D' -> {
                    // Call addDeposit();
                }case 'P' -> {
                    // Call makePayment();
                }case 'L' -> {
                    // Call viewLedger();
                }case 'X' -> {
                    System.out.println("Thank you for using Asteway's Financial Services. Come again!");
                }default -> {
                    System.out.println("Invalid option! Please try again.");
                    continue;}
            }
            // Breaks the loop if 'D', 'P', or 'L' are selected and executes what they call
            break;
        } 
    }
}
