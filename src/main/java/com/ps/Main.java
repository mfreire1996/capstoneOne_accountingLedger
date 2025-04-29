package com.ps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
                    depositing();
                    break;
                case 2:
                    makingPayment();
                    break;
                case 3:
                    displayLedgerMenu();
                    break;
                case 0:
                    System.out.println("Exiting");
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

    private static void depositing() {
        scanner.nextLine();

        System.out.println("----Make a deposit----");

        System.out.println("Enter date yyyy-mm-dd: ");
        String date = scanner.nextLine();

        System.out.println("Enter time: ");
        String time = scanner.nextLine();

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        System.out.println("Successfully deposited!");
    }

    private static void makingPayment() {
        scanner.nextLine();
        System.out.println("----Make a payment----");

        System.out.println("Enter date yyyy-mm-dd:");
        String date = scanner.nextLine();

        System.out.println("Enter time: ");
        String time = scanner.nextLine();

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            amount = -amount;
        }

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        System.out.println("Successfully made a payment!");
    }

    private static void displayLedgerMenu() {
        System.out.println("----Welcome to Ledger----");
        System.out.println("1. View deposits");
        System.out.println("2. View payments");
        System.out.println("3. View reports");
        System.out.println("0. Go back to home page");
        System.out.print("What would you like to do? ");

        int ledgerMenuCommands = scanner.nextInt();

        switch (ledgerMenuCommands) {
            case 1:
                viewingDeposits();
            case 2:
                viewingPayments();
            case 3:
                viewingReports();
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input, going back");
        }
    }

    private static void viewingDeposits() {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            double amountDeposited = transaction.getAmount();
            if (amountDeposited > 0) {
                System.out.println(transaction);
            }
        }
    }

    private static void viewingPayments() {
        for (int i = transactions.size() - 1; i >= 0; i++) {
            Transaction transaction = transactions.get(i);
            double amoutPaid = transaction.getAmount();
            if (amoutPaid < 0) {
                System.out.println(transaction);
            }
        }
    }

    private static void viewingReports() {
        System.out.println("----Reports Menu----");
        System.out.println("1. Months to date");
        System.out.println("2. Previous Month");
        System.out.println("3. Year to Date");
        System.out.println("4. Previous Year");
        System.out.println("5. Search by Vendor");
        System.out.println("0. Go Back");
        System.out.print("What would you like to do? ");

        int reportMenuCommands = scanner.nextInt();

        switch (reportMenuCommands) {
            case 1:
                monthsToDate();
                break;
            case 2:
                previousMonth();
                break;
            case 3:
                yearToDate();
                break;
            case 4:
                viewPreviousYear();
                break;
            case 5:
                searchByVendor();
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input, going back");
        }
    }

    private static void monthsToDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date today = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(today);
        int currentMonth = currentCal.get(Calendar.MONTH);
        int currentYear = currentCal.get(Calendar.YEAR);

        for (Transaction transaction : transactions) {
            try {
                Date transactionDate = formatter.parse(transaction.getDate());
                Calendar transactionCal = Calendar.getInstance();
                transactionCal.setTime(transactionDate);
                int transactionMonth = transactionCal.get(Calendar.MONTH);
                int transactionYear = transactionCal.get(Calendar.YEAR);

                if (transactionMonth == currentMonth && transactionYear == currentYear) {
                    System.out.println(transaction);
                }

            } catch (Exception e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        }

    }

    private static void previousMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Transaction transaction : transactions) {
            try {
                Date transactionDate = formatter.parse(transaction.getDate());
                Calendar transactionCal = Calendar.getInstance();
                transactionCal.setTime(transactionDate);
                int transactionMonth = transactionCal.get(Calendar.MONTH);
                int transactionYear = transactionCal.get(Calendar.YEAR);

                if (transactionMonth == currentMonth && transactionYear == currentYear) {
                    System.out.println(transaction);
                }

            } catch (Exception e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        }
    }

    private static void yearToDate() {
    }

    private static void viewPreviousYear() {
    }

    private static void searchByVendor() {
        System.out.println("Please provide a vendor name: ");
        scanner.nextLine();
        String vendorInput = scanner.nextLine();

        boolean found = false;

        for (Transaction transaction : transactions) {
            String currentVendor = transaction.getVendor();
            if (currentVendor.equalsIgnoreCase(vendorInput)) {
                System.out.println(transaction);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Vendor not found!");
        }
    }
}