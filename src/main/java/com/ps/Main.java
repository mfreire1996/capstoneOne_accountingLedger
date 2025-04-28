package com.ps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        loadTransactions();

        int mainMenuCommand;
        do {
            System.out.println("Welcome to the bank!");
            System.out.println("1. Make a deposit");
            System.out.println("2. Make a payment");
            System.out.println("3. Go to Ledger");
            System.out.println("0. Exit");
            System.out.print("What would you like to do? ");
            mainMenuCommand = scanner.nextInt();

            switch (mainMenuCommand) {
                case 1:
                    displayDepositMenu();
                    break;
                case 2:
                    displayPaymentMenu();
                    break;
                case 3:
                    displayLedgerMenu();
                    break;
                default:
                    System.out.println("Command not found! Try again!");
            }
        } while (mainMenuCommand != 0);
    }

    private static void loadTransactions() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));

            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] fields = input.split("\\|");

                String date = fields[0];
                String time = fields[1];
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }

            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayDepositMenu() {
        System.out.println("----Display Deposit----");
        System.out.println("0. Back");
        System.out.print("What would you like to do? ");
    }

    private static void displayPaymentMenu() {
    }

    private static void displayLedgerMenu() {
    }
}