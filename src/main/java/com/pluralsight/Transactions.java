package com.pluralsight;
// Tells Java to bring in the file packages for the File, FileReader, FileWriter, BufferReader, BufferWriter, etc. And -
// bringing in the ArrayList utility to use arraylists and the connections utility to use reverse for the list ---------
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

// =====================================================================================================================
public class Transactions {

    // Declaring these private instance variables to make them more secure. In this program they are accessed using ====
    // toString and getters. ===========================================================================================
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    // This constructor assigns values to the object's variables using the parameters provided when creating a new =====
    // Transactions object. ============================================================================================
    public Transactions(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters provide controlled access to the private fields even if some aren't currently used, they may be helpful =
    // for future access or printing. ==================================================================================
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }

    // Custom toString() method instead of using setters to format transaction details for writing to a .csv file ======
    public String toString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f",amount);
    }

    // Making a method to write to the .csv file =======================================================================
    public static void writeTransactionToFile(Transactions t, String filename) {
        try {
            // Makes a file object that is used to reference the filename ----------------------------------------------
            File csv = new File(filename);
            // Using the csv object I created to write to create and write to the file and repeatedly append to it. The
            // exact filename if in the AccountingLedgerApp class ------------------------------------------------------
            FileWriter fileWriterBoi = new FileWriter(csv, true);
            BufferedWriter buffWriterBoi = new BufferedWriter(fileWriterBoi);
            // The object is then used to validate whether it is empty at first to then print out the header format ----
            if (csv.length() == 0) {
                buffWriterBoi.write("date|time|description|vendor|amount\n");}
            // This will then write based on the transaction in the format of the toString -----------------------------
            buffWriterBoi.write(t.toString()+"\n");
            // Closes the buffer writer in case there is anything left un flushed --------------------------------------
            buffWriterBoi.close();
        // Using the catch IOException e in case there is an issue with the file ---------------------------------------
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Making a method that will read lines from a file and convert them into a transaction object and add those to an =
    // arraylist called transactions ===================================================================================
    public static ArrayList<Transactions> readTransactionsFromFile(String filename) {
        ArrayList<Transactions> transactions = new ArrayList<>();
        try {
            // FileReader will open and read the characters from the file and buffered reader will wrap it line by line
            FileReader fileReadBoi = new FileReader(filename);
            BufferedReader buffReadBoi = new BufferedReader(fileReadBoi);
            // This will skip the header format row when displaying transactions ---------------------------------------
            buffReadBoi.readLine();
            // This reads each remaining line from the file one by one using the variable aLine and then splits them ---
            String aLine;
            while ((aLine = buffReadBoi.readLine()) != null) {
                String[] lineParts = aLine.split("\\|");
                // If the line string has 5 elements then it will make a transaction object and add them to the list ---
                if (lineParts.length == 5) {
                    Transactions transaction = new Transactions(lineParts[0], lineParts[1],
                            lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]));
                    transactions.add(transaction);
                }
            }
            // This will reverse the view of the transactions array list to last made at the top of the list -----------
            Collections.reverse(transactions);
            // This will close the buffer reader to flush out anything left --------------------------------------------
            buffReadBoi.close();
        // Using the catch IOException e in case there is an issue with the file ---------------------------------------
        } catch(IOException e) {
            System.out.println("Error reading transactions file: " + e.getMessage());
        }
        // This will return the array list that has been read to whatever calls this method ----------------------------
        return transactions;
    }
}