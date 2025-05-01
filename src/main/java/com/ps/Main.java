package com.ps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            System.out.println("\n===============================");
            System.out.println("🏦  Welcome to the Bank System");
            System.out.println("===============================");
            System.out.println("💰 1. Make a Deposit");
            System.out.println("💸 2. Make a Payment");
            System.out.println("📒 3. View Ledger");
            System.out.println("🚪 0. Exit");
            System.out.print("👉 What would you like to do? ");
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

                if (fields.length < 5) {
                    System.out.println("❌ Invalid input. Going back");
                }

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

        System.out.println("✅ Deposit successful!");
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

        System.out.println("✅ Payment recorded!");
    }

    private static void displayLedgerMenu() {

        int ledgerMenuCommands;

        do {
            System.out.println("\n===============================");
            System.out.println("📒 Ledger Menu");
            System.out.println("===============================");
            System.out.println("📥 1. View Deposits");
            System.out.println("📤 2. View Payments");
            System.out.println("📊 3. View Reports");
            System.out.println("🔙 0. Return to Home");
            System.out.print("👉 Choose an option: ");
            ledgerMenuCommands = scanner.nextInt();

            switch (ledgerMenuCommands) {
                case 1:
                    viewingDeposits();
                    break;
                case 2:
                    viewingPayments();
                    break;
                case 3:
                    viewingReports();
                    break;
                case 0:
                    System.out.println("Going back...");
                    break;
                default:
                    System.out.println("❌ Invalid input, going back");
            }
        } while (ledgerMenuCommands != 0);
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
        if (transactions.isEmpty()) {
            System.out.println("No transactions were made");
            return;
        }

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            if (transaction.getAmount() < 0) {
                System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" +
                        transaction.getDescription() + "|" + transaction.getVendor()
                        + "|" + transaction.getAmount());
            }
        }
    }

    private static void viewingReports() {

        int reportMenuCommands;

        do {
            System.out.println("\n===============================");
            System.out.println("📊 Reports Menu");
            System.out.println("===============================");
            System.out.println("🗓️  1. Month to Date");
            System.out.println("📆  2. Previous Month");
            System.out.println("🗃️  3. Year to Date");
            System.out.println("📁  4. Previous Year");
            System.out.println("🔍  5. Search by Vendor");
            System.out.println("🔎 6. Custom Search");
            System.out.println("🔙  0. Return to Ledger");
            System.out.print("👉 Select a report: ");
            reportMenuCommands = scanner.nextInt();

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
                case 6:
                    customSearch();
                case 0:
                    System.out.println("Going back...");
                    break;
                default:
                    System.out.println("❌ Invalid input, going back");
            }
        } while (reportMenuCommands != 0);
    }

    private static void monthsToDate() {
        try {
            LocalDate now = LocalDate.now();
            LocalDate firstDay = now.withDayOfMonth(1);

            for (Transaction transaction : transactions) {
                LocalDate dateOfTransaction = LocalDate.parse(transaction.getDate());
                if (!dateOfTransaction.isBefore(firstDay)) {
                    System.out.println(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error: " + e.getMessage());
        }

    }

    private static void previousMonth() {
        try {
            LocalDate now = LocalDate.now();
            LocalDate firstDay = now.minusMonths(1).withDayOfMonth(1);
            LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
            boolean found = false;

            for (Transaction transaction : transactions) {
                LocalDate date = LocalDate.parse(transaction.getDate());
                if (!date.isBefore(firstDay) && !date.isAfter(lastDay)) {
                    System.out.println(transaction);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No transaction for this period");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error: " + e.getMessage());
        }
    }

    private static void yearToDate() {
        try {
            LocalDate now = LocalDate.now();
            LocalDate startOfYear = LocalDate.of(now.getYear(), 1, 1);
            boolean found = false;

            for (Transaction transaction : transactions) {
                LocalDate transactionDate = LocalDate.parse(transaction.getDate());
                if (!transactionDate.isBefore(startOfYear) && !transactionDate.isAfter(now)) {
                    System.out.println(transaction);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions for this year");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error: " + e.getMessage());
        }
    }

    private static void viewPreviousYear() {
        try {
            LocalDate now = LocalDate.now();
            int previousYear = now.getYear() - 1;

            LocalDate startOfPreviousYear = LocalDate.of(previousYear, 1, 1);
            LocalDate endOfPreviousYear = LocalDate.of(previousYear, 12, 31);
            boolean found = false;

            for (Transaction transaction : transactions) {
                LocalDate transactionDate = LocalDate.parse(transaction.getDate());
                if (!transactionDate.isBefore(startOfPreviousYear) && !transactionDate.isAfter(endOfPreviousYear)) {
                    System.out.println(transaction);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions from the previous year.");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error: " + e.getMessage());
        }
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

    private static void customSearch() {
        scanner.nextLine(); // clear buffer

        System.out.println("Enter start date (yyyy-MM-dd) or leave blank:");
        String startDateInput = scanner.nextLine().trim();

        System.out.println("Enter end date (yyyy-MM-dd) or leave blank:");
        String endDateInput = scanner.nextLine().trim();

        System.out.println("Enter description or leave blank:");
        String descriptionInput = scanner.nextLine().trim().toLowerCase();

        System.out.println("Enter vendor or leave blank:");
        String vendorInput = scanner.nextLine().trim().toLowerCase();

        System.out.println("Enter amount or leave blank:");
        String amountInput = scanner.nextLine().trim();

        LocalDate startDate = null;
        LocalDate endDate = null;
        Double amount = null;

        try {
            if (!startDateInput.isEmpty()) {
                startDate = LocalDate.parse(startDateInput);
            }
            if (!endDateInput.isEmpty()) {
                endDate = LocalDate.parse(endDateInput);
            }
            if (!amountInput.isEmpty()) {
                amount = Double.parseDouble(amountInput);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        boolean found = false;
        for (Transaction t : transactions) {
            LocalDate transactionDate = LocalDate.parse(t.getDate());

            if (startDate != null && transactionDate.isBefore(startDate)) continue;
            if (endDate != null && transactionDate.isAfter(endDate)) continue;
            if (!descriptionInput.isEmpty() && !t.getDescription().toLowerCase().contains(descriptionInput)) continue;
            if (!vendorInput.isEmpty() && !t.getVendor().toLowerCase().contains(vendorInput)) continue;
            if (amount != null && t.getAmount() != amount) continue;

            System.out.println(t);
            found = true;
        }

        if (!found) {
            System.out.println("No transactions match your search.");
        }
    }

}