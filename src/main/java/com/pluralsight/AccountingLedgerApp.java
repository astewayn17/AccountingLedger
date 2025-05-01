package com.pluralsight;
// Tells java to bring in the LocalDateTime and Formatter class from the java.time package so it can be used in the code
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// =====================================================================================================================
public class AccountingLedgerApp {

    // Creates a static Scanner object named "input" that can be used anywhere in the class without creating an instance
    static Scanner input = new Scanner(System.in);

    // The main method to run the code =================================================================================
    public static void main(String[] args) {
        // Begins a loop that will run through the options on the home screen and back ---------------------------------
        while (true) {
            // The home screen that asks for the user to input what they would like to do ------------------------------
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
            // Stores the preliminary choice in the home screen as a string in upper case and trimmed spaces -----------
            String prelimHomeScreenInput = input.nextLine().toUpperCase().trim();
            // If the preliminary choice is greater than one character, it restarts the loop as an error ---------------
            if (prelimHomeScreenInput.length() != 1) {
                System.out.println("Invalid option! Please enter only one letter (D, P, L, or X).");
                continue; }
            // Then this will store the first character of the preliminary choice as a single character ----------------
            char homeScreenInput = prelimHomeScreenInput.charAt(0);
            // The letter selected will be run through the switch condition and execute the method or print ------------
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

    // This is the make deposit method that will be executed once the user selects 'D' =================================
    public static void makeDeposit() {
        // Starts off with 2 while loops that run until the deposit is done and the vendor/name is confirmed -----------
        boolean makingADeposit = true;
        while (makingADeposit) {
            // // Have to initialize vendor before the loop until it is updated based on user input --------------------
            String vendor = "";
            boolean vendorConfirmed = false;
            while (!vendorConfirmed) {
                // Confirming the name (vendor) of the person depositing -----------------------------------------------
                System.out.print("\nPlease enter your name: ");
                String inputVendor = input.nextLine().trim();
                // If the input is empty, it will print out this error and restart the loop ----------------------------
                if (inputVendor.isEmpty()) {
                    System.out.println("Name cannot be blank! Please try again.");
                    continue;
                }
                // Converts the name input into an array while trimming, lower casing and splitting by the spaces. Then
                // runs through conditionals that print out based on how many parts of the name there are --------------
                String[] vendorArray = inputVendor.toLowerCase().trim().split("\\s+");
                if (vendorArray.length == 1) {
                    vendor = vendorArray[0].substring(0, 1).toUpperCase() + vendorArray[0].substring(1);
                } else if (vendorArray.length >= 2) {
                    vendor = vendorArray[0].substring(0, 1).toUpperCase() + vendorArray[0].substring(1)
                            + " " + vendorArray[1].substring(0, 1).toUpperCase() + vendorArray[1].substring(1);
                } else {
                    System.out.println("Invalid input! Try again.");
                    continue;
                }
                // Asks the user to confirm that what they entered is correct. Will then validate that Y or N input ----
                System.out.println("\nIs [" + vendor + "] correct?");
                System.out.print("Yes (Y) or No (N)? ");
                String vendorResponse = input.nextLine().trim().toUpperCase();
                if (vendorResponse.isEmpty()) {
                    System.out.println("\nInvalid input! Try again.");
                    continue;
                }
                // If the answer is Y, it will end the loop and the vendorChoice will be confirmed. If no, it restarts -
                char vendorAnswer = vendorResponse.trim().toUpperCase().charAt(0);
                if (vendorAnswer == 'Y') {
                    vendorConfirmed = true;
                } else if (vendorAnswer == 'N') {
                } else {
                    System.out.println("\nInvalid input! Try again.");
                }
            }
            // Initializing description and amount since they are inside a loop and will be modified once that loop is -
            // complete. The loop is meant to confirm the description with validation. ---------------------------------
            String description = "";
            double amount = 0.0;
            boolean descriptionConfirmed = false;
            while (!descriptionConfirmed) {
                // Asks user to input the description for the deposit and if the description is empty, the loop restarts
                System.out.print("\nPlease provide a deposit description: ");
                description = input.nextLine().trim();
                if (description.isEmpty()) {
                    System.out.println("Description cannot be blank. Please try again.");
                    continue;
                }
                // Asks the user to confirm if their description is correct and validates if it is empty or not --------
                System.out.println("\nIs [" + description + "] correct?");
                System.out.print("Yes (Y) or No (N)? ");
                String descriptionResponse = input.nextLine().trim().toUpperCase();
                if (descriptionResponse.isEmpty()) {
                    System.out.println("\nInvalid input! Try again.");
                    continue;
                }
                // If it is 'Y' then the loop will close and move on to the deposit amount, if not, the loop restarts --
                char descriptionAnswer = descriptionResponse.trim().toUpperCase().charAt(0);
                if (descriptionAnswer == 'Y') {
                    descriptionConfirmed = true;
                } else if (descriptionAnswer == 'N') {
                } else {
                    System.out.println("\nInvalid input! Try again.");
                }
            }
            // Using another while loop to confirm if the amount is correct --------------------------------------------
            boolean amountConfirmed = false;
            while (!amountConfirmed) {
                try {
                    // Asks the user to input a deposit amount and validates if it's empty or not ----------------------
                    System.out.print("\nPlease enter the deposit amount: $");
                    String prelimAmount = input.nextLine().trim();
                    if (prelimAmount.isEmpty()) {
                        System.out.println("Error. Please enter a number.");
                        continue;
                    }
                    // Converting the string amount to a double which then is asked to the user to confirm if correct --
                    amount = Double.parseDouble(prelimAmount);
                    System.out.println("\nIs [$" + String.format("%.2f", amount) + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String amountResponse = input.nextLine().trim().toUpperCase();
                    if (amountResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Try again.");
                        continue;
                    }
                    // If the user selects 'Y' then the loop will break and continue, if 'N' or anything else, restarts
                    char amountAnswer = amountResponse.trim().toUpperCase().charAt(0);
                    if (amountAnswer == 'Y') {
                        amountConfirmed = true;
                    } else if (amountAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input! Try again.");
                    }
                    // If the user enters an amount that isn't a number, this catch will throw an error and restart ----
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
            // This ternary will make sure the amount is listed as a positive double -----------------------------------
            amount = (amount > 0) ? amount : -1 * amount;
            // This brings in the LocalDateTime method and stores the current time as integer 'dateTime' and then date -
            // will be formatted using the DateTimeFormatter based on the pattern I chose. Same with the time. ---------
            LocalDateTime dateTime = LocalDateTime.now();
            String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            // This is creating a new transaction object called depositTransaction based on the transaction class ------
            // constructor using the parameters shown then it is calling a static method called writeTransactionToFile -
            // and passing the depositTransaction object into it to write into the file that is in the parameter -------
            Transactions depositTransaction = new Transactions(date, time, description, vendor, amount);
            Transactions.writeTransactionToFile(depositTransaction, "src/main/resources/transactions.csv");
            // Once the deposit/transaction is written, it will ask the user if they want to make another deposit ------
            System.out.println("\nDeposit made successfully!");
            System.out.println("\nWhat would you like to do next?");
            System.out.print("Make another deposit (D) or return (X): ");
            String prelimAfterDepositChoice = input.nextLine().toUpperCase().trim();
            // If they enter an empty value instead of D or X then they will be forced to go back to the home screen ---
            if (prelimAfterDepositChoice.isEmpty()) {
                System.out.println("\nInvalid input! Returning to home screen.");
                return;
            }
            // After the deposit choice, it is run through a switch condition to make another deposit or return home ---
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
        }
    }

    // Similar to the deposit method above in many ways ================================================================
    public static void makePayment() {
        boolean makingAPayment = true;
        while (makingAPayment) {
            String vendor = "";
            boolean vendorConfirmed = false;
            while (!vendorConfirmed) {
                try {
                    // Vendor only this time since it's a payment to them ----------------------------------------------
                    System.out.print("\nPlease enter the vendor's name: ");
                    vendor = input.nextLine().trim();
                    if (vendor.isEmpty()) {
                        System.out.println("Vendor name cannot be blank. Please try again.");
                        continue;
                    }
                    // -------------------------------------------------------------------------------------------------
                    System.out.println("\nIs [" + vendor + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String vendorResponse = input.nextLine().trim().toUpperCase();
                    // -------------------------------------------------------------------------------------------------
                    if (vendorResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Please try again.");
                        continue;
                    }
                    // -------------------------------------------------------------------------------------------------
                    char vendorAnswer = vendorResponse.trim().toUpperCase().charAt(0);
                    if (vendorAnswer == 'Y') {
                        vendorConfirmed = true;
                    } else if (vendorAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input. Please enter the vendor name again.");
                    }
                // If the vendorResponse is empty, it will throw this exception ----------------------------------------
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            }
            // ---------------------------------------------------------------------------------------------------------
            String description = "";
            double amount = 0.0;
            boolean descriptionConfirmed = false;
            while (!descriptionConfirmed) {
                // -----------------------------------------------------------------------------------------------------
                System.out.print("\nPlease provide a payment description: ");
                description = input.nextLine().trim();
                if (description.isEmpty()) {
                    System.out.println("Description cannot be blank. Please try again.");
                    continue;
                }
                // -----------------------------------------------------------------------------------------------------
                System.out.println("\nIs [" + description + "] correct?");
                System.out.print("Yes (Y) or No (N)? ");
                String descriptionResponse = input.nextLine().trim().toUpperCase();
                if (descriptionResponse.isEmpty()) {
                    System.out.println("\nInvalid input! Try again.");
                    continue;
                }
                // -----------------------------------------------------------------------------------------------------
                char descriptionAnswer = descriptionResponse.trim().toUpperCase().charAt(0);
                if (descriptionAnswer == 'Y') {
                    descriptionConfirmed = true;
                } else if (descriptionAnswer == 'N') {
                } else {
                    System.out.println("\nInvalid input. Try again.");
                }
            }
            // ---------------------------------------------------------------------------------------------------------
            boolean amountConfirmed = false;
            while (!amountConfirmed) {
                try {
                    // -------------------------------------------------------------------------------------------------
                    System.out.print("\nPlease enter the payment amount: $");
                    String prelimAmount = input.nextLine().trim();
                    if (prelimAmount.isEmpty()) {
                        System.out.println("Error. Please enter a number.");
                        continue;
                    }
                    // -------------------------------------------------------------------------------------------------
                    amount = Double.parseDouble(prelimAmount);
                    System.out.println("\nIs [$" + String.format("%.2f", amount) + "] correct?");
                    System.out.print("Yes (Y) or No (N)? ");
                    String amountResponse = input.nextLine().trim().toUpperCase();
                    if (amountResponse.isEmpty()) {
                        System.out.println("\nInvalid input! Try again.");
                        continue;
                    }
                    // -------------------------------------------------------------------------------------------------
                    char amountAnswer = amountResponse.trim().toUpperCase().charAt(0);
                    if (amountAnswer == 'Y') {
                        amountConfirmed = true;
                    } else if (amountAnswer == 'N') {
                    } else {
                        System.out.println("\nInvalid input! Try again.");
                    }
                    // Will throw this catch if the payment amount isn't a number --------------------------------------
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
            // Since a payment is negative, this ternary will be opposite of the deposit ternary -----------------------
            amount = (amount > 0) ? -1 * amount : amount;
            // ---------------------------------------------------------------------------------------------------------
            LocalDateTime dateTime = LocalDateTime.now();
            String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            // ---------------------------------------------------------------------------------------------------------
            Transactions paymentTransaction = new Transactions(date, time, description, vendor, amount);
            Transactions.writeTransactionToFile(paymentTransaction, "src/main/resources/transactions.csv");
            // -----------------------------------------------------------------------------------------------------
            System.out.println("\nPayment made successfully!");
            System.out.println("\nWhat would you like to do next?");
            System.out.print("Make another payment (P) or return (X): ");
            String prelimAfterPaymentChoice = input.nextLine().toUpperCase().trim();
            // -----------------------------------------------------------------------------------------------------
            if (prelimAfterPaymentChoice.isEmpty()) {
                System.out.println("\nInvalid input! Returning to home screen.");
                return;
            }
            // -----------------------------------------------------------------------------------------------------
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
        }
    }
}

