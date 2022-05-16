package com.company;

import com.company.entities.*;
import com.company.services.AccountService;
import com.company.services.AuditService;
import com.company.services.BankService;
import com.company.services.CSVService;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        BankService bank = BankService.getInstance();
        CSVService csvService = CSVService.getInstance();
        AccountService accountService = AccountService.getInstance(csvService);
        AuditService auditService = AuditService.getInstance();

        Scanner scan = new Scanner(System.in);

        // Load accounts from CSV
        bank.setAccounts(accountService.readAccountsFromCSV());
        auditService.audit("Data loaded from CSV.");

        while (true){

            //Menu options
            System.out.println();
            System.out.println("Actions available:");

            //Account stuff
            System.out.println("1  - Create an account.");
            System.out.println("2  - Search an account by email.");
            System.out.println("3  - List all accounts in the bank sorted by balance.");
            System.out.println("4  - Update details of an account.");
            System.out.println("5  - Delete an account.");

            //Card stuff
            System.out.println("6  - Create a card.");
            System.out.println("7  - List all cards of an account.");
            System.out.println("8  - Add money to card.");
            System.out.println("9  - Withdraw money from card.");
            System.out.println("10 - Delete a card.");

            //Savings stuff
            System.out.println("11 - Add money to savings.");

            //Transactions stuff
            System.out.println("12 - Make a transaction.");

            //Quit
            System.out.println("13 - Quit.");
            System.out.println();

            System.out.print("Write the number of the action you want to perform: ");
            int option = scan.nextInt();

            //Do the action
            String firstName, lastName, email;
            Card card;
            switch (option){
                case 1:
                    System.out.print("Is the account premium?[y/n] ");
                    String isPremiumString = scan.next();
                    boolean isPremium = isPremiumString.equals("y");

                    System.out.print("First Name: ");
                    firstName = scan.next();

                    System.out.print("Last Name: ");
                    lastName = scan.next();

                    System.out.print("Email: ");
                    email = scan.next();

                    bank.addAccount(isPremium, firstName, lastName, email);

                    auditService.audit(String.format("Account added : %s.", email));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 2:
                    System.out.print("Email: ");
                    email = scan.next();

                    Account account = bank.getAccountByEmail(email);
                    System.out.println(account.toString());

                    auditService.audit(String.format("Account searched by email : %s.", email));
                    break;

                case 3:
                    List<Account> accounts = bank.getAllAccountsSortedByBalance();
                    for (var item: accounts) {
                        System.out.println(item.toString() + '\n');
                    }

                    auditService.audit("All accounts listed.");
                    break;

                case 4:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    System.out.print("New First Name: ");
                    firstName = scan.next();

                    System.out.print("New Last Name: ");
                    lastName = scan.next();

                    bank.updateAccount(email, firstName, lastName);

                    auditService.audit(String.format("Account updated : %s.", email));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 5:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    bank.deleteAccount(email);
                    auditService.audit(String.format("Account deleted : %s.", email));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 6:
                    System.out.print("Is this a credit card?[y/n] ");
                    String isCreditString = scan.next();
                    boolean isCredit = isCreditString.equals("y");

                    System.out.print("The email of the account: ");
                    email = scan.next();

                    System.out.print("Balance: ");
                    double balance = scan.nextDouble();

                    double limit = 0;
                    if (isCredit){
                        System.out.print("Limit: ");
                        limit = scan.nextDouble();
                    }

                    bank.addCard(isCredit, email, balance, limit);
                    auditService.audit(String.format("Card created with balance %f for account %s.", balance, email));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 7:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    List<Card> cards = bank.getCardsOfAccount(email);
                    if (cards == null) break;

                    for (var item: cards) {
                        System.out.println(item.toString() + '\n');
                    }

                    auditService.audit(String.format("All cards listed for account %s.", email));
                    break;

                case 8:
                    card = chooseCard(scan, bank);
                    if (card == null) break;

                    System.out.print("Amount of money added: ");
                    int amountAdded = scan.nextInt();

                    bank.addMoneyToCard(card, amountAdded);
                    auditService.audit(String.format("Money added to card : %d.", amountAdded));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 9:
                    card = chooseCard(scan, bank);
                    if (card == null) break;

                    System.out.print("Amount of money withdrawn: ");
                    double amountWithdrawn = scan.nextInt();

                    bank.withdrawMoneyFromCard(card, amountWithdrawn);
                    auditService.audit(String.format("Money withdrawn from card : %f.", amountWithdrawn));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 10:
                    card = chooseCard(scan, bank);
                    if (card == null) break;

                    bank.deleteCard(card);
                    auditService.audit("Card deleted.");

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 11:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    System.out.print("Amount added to savings: ");
                    double savingsAmount = scan.nextDouble();

                    try{
                        bank.addMoneyToSavings(email, savingsAmount);
                    }
                    catch (Exception e){
                        System.out.printf("Error occurred while adding money to savings : %s %n", e.getMessage());
                    }

                    auditService.audit(String.format("Money added to savings (%f) in account %s.", savingsAmount, email));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 12:
                    System.out.println("ACCOUNT WHICH PAYS THE AMOUNT:");
                    Card cardPaying = chooseCard(scan, bank);
                    if (cardPaying == null) break;

                    System.out.println("ACCOUNT WHICH RECEIVES THE AMOUNT:");
                    Card cardReceiving = chooseCard(scan, bank);
                    if (cardReceiving == null) break;

                    System.out.print("Amount payed: ");
                    double amount = scan.nextDouble();

                    if (!cardPaying.canPay(amount)){
                        System.out.println("Not enough funds to pay!");
                        break;
                    }

                    bank.makeTransaction(cardPaying, cardReceiving, amount);
                    auditService.audit(String.format("Transaction made : %s -> %s (amount = %f).",cardPaying.getAccount().getEmail() ,cardReceiving.getAccount().getEmail(), amount));

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");
                    break;

                case 13:

                    // Save accounts to CSV
                    accountService.writeAccountsToCSV(bank.getAllAccountsSortedByBalance());
                    auditService.audit("Data saved to CSV.");

                    System.out.println("Bye bye! :)");
                    return;
                default:
                    System.out.println("Option not available!%n");
            }
        }

    }

    private static Card chooseCard(Scanner scan, BankService bank){
        System.out.print("The email of the account: ");
        String email = scan.next();

        List<Card> cards = bank.getCardsOfAccount(email);
        if (cards == null || cards.size() == 0) {
            System.out.println("Account currently has no cards!");
            return null;
        }

        for (var item: cards) {
            System.out.println(item.toString() + '\n');
        }

        int indexCard = 0;
        while (true){
            System.out.print("Write the index of the card you want to use [0/1/2...]: ");
            indexCard = scan.nextInt();

            if (indexCard < 0 || indexCard >= cards.size()){
                System.out.print("Wrong index!");
            }
            else{
                break;
            }
        }

        return cards.get(indexCard);
    }
}
