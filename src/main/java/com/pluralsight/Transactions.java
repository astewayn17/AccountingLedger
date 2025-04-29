package com.pluralsight;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
public class Transactions {

    //
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    //
    public Transactions(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }

    // Using toString
    public String toString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    //
    public static void writeTransactionToFile(Transactions t, String filename) {
        try {
            File csv = new File(filename);

            FileWriter fileWriterBoi = new FileWriter(csv, true);
            BufferedWriter buffWriterBoi = new BufferedWriter(fileWriterBoi);


            if (csv.length() == 0) {
                buffWriterBoi.write("date|time|description|vendor|amount\n");}

            buffWriterBoi.write(t.toString()+"\n");

            buffWriterBoi.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //
    public static ArrayList<Transactions> readTransactionsFromFile(String filename) {
        ArrayList<Transactions> transactions = new ArrayList<>();
        try {
            FileReader fileReadBoi = new FileReader(filename);
            BufferedReader buffReadBoi = new BufferedReader(fileReadBoi);

            String aLine;
            while ((aLine = buffReadBoi.readLine()) != null) {
                String[] lineParts = aLine.split("\\|");

                if (lineParts.length == 5) {
                    Transactions transaction = new Transactions(lineParts[0], lineParts[1],
                            lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]));
                }
            }
            Collections.reverse(transactions);
            buffReadBoi.close();
        } catch(IOException e) {
            System.out.println("Error reading transactions file: " + e.getMessage());
        }
        return transactions;
    }
}
