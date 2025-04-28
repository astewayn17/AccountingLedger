package com.pluralsight;
//
import java.io.*;
import java.util.ArrayList;

public class LedgerFunctions {

    //
    public static ArrayList<Transaction> readTransactionsFromFile(String filename) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            FileReader fileReadBoi = new FileReader(filename);
            BufferedReader buffReadBoi = new BufferedReader(fileReadBoi);

            String aLine;
            while ((aLine = buffReadBoi.readLine()) != null) {
                String[] lineParts = aLine.split("\\|");

                if (lineParts.length == 5) {
                    Transaction transaction = new Transaction(lineParts[0], lineParts[1],
                            lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]));
                    transactions.add(transaction);
                }
            }
            buffReadBoi.close();
        } catch(IOException e) {
            System.out.println("Error reading transactions file: \" + e.getMessage()");
        }
        return transactions;
    }

    //
    public static void writeTransactionToFile(Transaction t, String filename) {
        try {
            FileWriter fileWriterBoi = new FileWriter(filename, true);
            BufferedWriter buffWriterBoi = new BufferedWriter(fileWriterBoi);

            buffWriterBoi.write(t.toString()+"\n");

            buffWriterBoi.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}