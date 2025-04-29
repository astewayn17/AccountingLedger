package com.pluralsight;
//
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
//
public class AccountingLedgerApp {

    // Creates a scanner under the variable name "input"
    static Scanner input = new Scanner(System.in);

    //
    public static void main(String[] args) {
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
                case 'L': LedgerFunctions.viewLedger();
                    break;
                case 'X': System.out.println("\nThank you for using Asteway's Financial Services. Goodbye!");
                    break;
                default: System.out.println("Invalid option! Please try again.");
            }
        }
    }

    //
    public static void makeDeposit() {
        boolean makingADeposit = true;
        while (makingADeposit) {
            String vendor = "";
            boolean vendorConfirmed = false;
            while (!vendorConfirmed) {
                //
                try {
                    System.out.print("\nPlease enter your name (first and last): ");
                    String inputVendor = input.nextLine().trim();
                    if (inputVendor.isEmpty()) {
                        System.out.println("Name cannot be blank. Please try again.");
                        continue;
                    }
                    //
                    String[] vendorArray = inputVendor.toLowerCase().trim().split("\\s+");
                    vendor = vendorArray[0].substring(0, 1).toUpperCase() + vendorArray[0].substring(1)
                            + " " + vendorArray[1].substring(0, 1).toUpperCase() + vendorArray[1].substring(1);
                    System.out.println("\nIs [" + vendor + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String vendorResponse = input.nextLine().trim().toUpperCase();
                    if (vendorResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Try again.");
                        continue;
                    }
                    //
                    char vendorAnswer = vendorResponse.trim().toUpperCase().charAt(0);
                    if (vendorAnswer == 'Y') {
                        vendorConfirmed = true;
                    } else if (vendorAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input! Try again.");
                    }
                //
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            }
            //
            String description = "";
            double amount = 0.0;
            boolean descriptionConfirmed = false;
            while (!descriptionConfirmed) {
                //
                System.out.print("\nPlease provide a deposit description: ");
                description = input.nextLine().trim();
                if (description.isEmpty()) {
                    System.out.println("Description cannot be blank. Please try again.");
                    continue;
                }
                //
                System.out.println("\nIs [" + description + "] correct?");
                System.out.print("Yes (Y) or No (N)? ");
                String descriptionResponse = input.nextLine().trim().toUpperCase();
                if (descriptionResponse.isEmpty()) {
                    System.out.println("\nInvalid input! Try again.");
                    continue;
                }
                //
                char descriptionAnswer = descriptionResponse.trim().toUpperCase().charAt(0);
                if (descriptionAnswer == 'Y') {
                    descriptionConfirmed = true;
                } else if (descriptionAnswer == 'N') {
                } else {
                    System.out.println("\nInvalid input! Try again.");
                }
            }
            //
            boolean amountConfirmed = false;
            while (!amountConfirmed) {
                try {
                    //
                    System.out.print("\nPlease enter the deposit amount: $");
                    String prelimAmount = input.nextLine().trim();
                    if (prelimAmount.isEmpty()) {
                        System.out.println("Error. Please enter a number.");
                        continue;
                    }
                    //
                    amount = Double.parseDouble(prelimAmount);
                    System.out.println("\nIs [$" + String.format("%.2f", amount) + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String amountResponse = input.nextLine().trim().toUpperCase();
                    if (amountResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Try again.");
                        continue;
                    }
                    //
                    char amountAnswer = amountResponse.trim().toUpperCase().charAt(0);
                    if (amountAnswer == 'Y') {
                        amountConfirmed = true;
                    } else if (amountAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input! Try again.");
                    }
                    //
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
            //
            amount = (amount > 0) ? amount : -1 * amount;
            //
            LocalDateTime dateTime = LocalDateTime.now();
            String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            //
            Transactions depositTransaction = new Transactions(date, time, description, vendor, amount);
            Transactions.writeTransactionToFile(depositTransaction, "src/main/resources/transactions.csv");
            //
            try {
                //
                System.out.println("\nDeposit made successfully!");
                System.out.println("\nWhat would you like to do next?");
                System.out.print("Make another deposit (D) or return (X): ");
                String prelimAfterDepositChoice = input.nextLine().toUpperCase().trim();
                //
                if (prelimAfterDepositChoice.isEmpty()) {
                    System.out.println("\nInvalid input! Returning to home screen.");
                    return;
                }
                //
                char afterDepositChoice = prelimAfterDepositChoice.trim().toUpperCase().charAt(0);
                switch (afterDepositChoice) {
                    case 'D':
                        continue;
                    case 'X':
                        makingADeposit = false;
                        break;
                    default:
                        System.out.println("Invalid option! Returning to home screen.");
                        return;
                }
            //
            } catch (Exception e) {
            }
        }
    }

    //
    public static void makePayment() {
        boolean makingAPayment = true;
        while (makingAPayment) {
            String vendor = "";
            boolean vendorConfirmed = false;
            while (!vendorConfirmed) {
                try {
                    //
                    System.out.print("\nPlease enter the vendor's name: ");
                    vendor = input.nextLine().trim();
                    if (vendor.isEmpty()) {
                        System.out.println("Vendor name cannot be blank. Please try again.");
                        continue;
                    }
                    //
                    System.out.println("\nIs [" + vendor + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String vendorResponse = input.nextLine().trim().toUpperCase();
                    //
                    if (vendorResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Please try again.");
                        continue;
                    }
                    //
                    char vendorAnswer = vendorResponse.trim().toUpperCase().charAt(0);
                    if (vendorAnswer == 'Y') {
                        vendorConfirmed = true;
                    } else if (vendorAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input. Please enter the name again");
                    }
                //
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            }
            //
            String description = "";
            double amount = 0.0;
            boolean descriptionConfirmed = false;
            while (!descriptionConfirmed) {
                //
                System.out.print("\nPlease provide a payment description: ");
                description = input.nextLine().trim();
                if (description.isEmpty()) {
                    System.out.println("Description cannot be blank. Please try again.");
                    continue;
                }
                //
                System.out.println("\nIs [" + description + "] correct?");
                System.out.print("Yes (Y) or No (N)? ");
                String descriptionResponse = input.nextLine().trim().toUpperCase();
                if (descriptionResponse.isEmpty()) {
                    System.out.println("\nInvalid input! Try again.");
                    continue;
                }
                //
                char descriptionAnswer = descriptionResponse.trim().toUpperCase().charAt(0);
                if (descriptionAnswer == 'Y') {
                    descriptionConfirmed = true;
                } else if (descriptionAnswer == 'N') {
                } else {
                    System.out.println("\nInvalid input. Try again.");
                }
            }
            //
            boolean amountConfirmed = false;
            while (!amountConfirmed) {
                try {
                    //
                    System.out.print("\nPlease enter the payment amount: $");
                    String prelimAmount = input.nextLine().trim();
                    if (prelimAmount.isEmpty()) {
                        System.out.println("Error. Please enter a number.");
                        continue;
                    }
                    //
                    amount = Double.parseDouble(prelimAmount);
                    System.out.println("\nIs [$" + String.format("%.2f", amount) + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String amountResponse = input.nextLine().trim().toUpperCase();
                    if (amountResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Try again.");
                        continue;
                    }
                    //
                    char amountAnswer = amountResponse.trim().toUpperCase().charAt(0);
                    if (amountAnswer == 'Y') {
                        amountConfirmed = true;
                    } else if (amountAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input! Try again.");
                    }
                    //
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
            //
            amount = (amount > 0) ? -1 * amount : amount;
            //
            LocalDateTime dateTime = LocalDateTime.now();
            String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            //
            Transactions paymentTransaction = new Transactions(date, time, description, vendor, amount);
            Transactions.writeTransactionToFile(paymentTransaction, "src/main/resources/transactions.csv");
            //
            try {
                //
                System.out.println("\nPayment made successfully!");
                System.out.println("\nWhat would you like to do next?");
                System.out.print("Make another payment (P) or return (X): ");
                String prelimAfterPaymentChoice = input.nextLine().toUpperCase().trim();
                //
                if (prelimAfterPaymentChoice.isEmpty()) {
                    System.out.println("\nInvalid input! Returning to home screen.");
                    return;
                }
                //
                char afterDepositChoice = prelimAfterPaymentChoice.trim().toUpperCase().charAt(0);
                switch (afterDepositChoice) {
                    case 'P':
                        continue;
                    case 'X':
                        makingAPayment = false;
                        break;
                    default:
                        System.out.println("Invalid option! Returning to home screen.");
                        return;
                }
            //
            } catch (Exception e) {
            }
        }
    }
}

