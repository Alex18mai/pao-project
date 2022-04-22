package com.company;

import com.company.entities.*;
import com.company.services.BankService;

import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        BankService bank = BankService.getInstance();
        Scanner scan = new Scanner(System.in);

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
            String first_name, last_name, email;
            Card card;
            switch (option){
                case 1:
                    System.out.print("Is the account premium?[y/n] ");
                    String is_premium_string = scan.next();
                    boolean is_premium = is_premium_string.equals("y");

                    System.out.print("First Name: ");
                    first_name = scan.next();

                    System.out.print("Last Name: ");
                    last_name = scan.next();

                    System.out.print("Email: ");
                    email = scan.next();

                    bank.addAccount(is_premium, first_name, last_name, email);
                    break;

                case 2:
                    System.out.print("Email: ");
                    email = scan.next();

                    Account account = bank.getAccountByEmail(email);
                    System.out.println(account.toString());
                    break;

                case 3:
                    Vector<Account> accounts = bank.getAllAccountsSortedByBalance();
                    for (var item: accounts) {
                        System.out.println(item.toString() + '\n');
                    }
                    break;

                case 4:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    System.out.print("New First Name: ");
                    first_name = scan.next();

                    System.out.print("New Last Name: ");
                    last_name = scan.next();

                    bank.updateAccount(email, first_name, last_name);
                    break;

                case 5:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    bank.deleteAccount(email);
                    break;

                case 6:
                    System.out.print("Is this a credit card?[y/n] ");
                    String is_credit_string = scan.next();
                    boolean is_credit = is_credit_string.equals("y");

                    System.out.print("The email of the account: ");
                    email = scan.next();

                    System.out.print("Balance: ");
                    double balance = scan.nextDouble();

                    double limit = 0;
                    if (is_credit){
                        System.out.print("Limit: ");
                        limit = scan.nextDouble();
                    }

                    bank.addCard(is_credit, email, balance, limit);
                    break;

                case 7:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    Vector<Card> cards = bank.getCardsOfAccount(email);
                    if (cards == null) break;

                    for (var item: cards) {
                        System.out.println(item.toString() + '\n');
                    }
                    break;

                case 8:
                    card = chooseCard(scan, bank);
                    if (card == null) break;

                    System.out.print("Amount of money added: ");
                    int amount_added = scan.nextInt();

                    bank.addMoneyToCard(card, amount_added);
                    break;

                case 9:
                    card = chooseCard(scan, bank);
                    if (card == null) break;

                    System.out.print("Amount of money withdrawn: ");
                    double amount_withdrawn = scan.nextInt();

                    bank.withdrawMoneyFromCard(card, amount_withdrawn);
                    break;

                case 10:
                    card = chooseCard(scan, bank);
                    if (card == null) break;

                    bank.deleteCard(card);
                    break;

                case 11:
                    System.out.print("The email of the account: ");
                    email = scan.next();

                    System.out.print("Amount added to savings: ");
                    double savings_amount = scan.nextDouble();

                    bank.addMoneyToSavings(email, savings_amount);
                    break;

                case 12:
                    System.out.println("ACCOUNT WHICH PAYS THE AMOUNT:");
                    Card card_paying = chooseCard(scan, bank);
                    if (card_paying == null) break;

                    System.out.println("ACCOUNT WHICH RECEIVES THE AMOUNT:");
                    Card card_receiving = chooseCard(scan, bank);
                    if (card_receiving == null) break;

                    System.out.print("Amount payed: ");
                    double amount = scan.nextDouble();

                    if (!card_paying.canPay(amount)){
                        System.out.println("Not enough funds to pay!");
                        break;
                    }

                    bank.makeTransaction(card_paying, card_receiving, amount);
                    break;

                case 13:
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

        Vector<Card> cards = bank.getCardsOfAccount(email);
        if (cards == null || cards.size() == 0) {
            System.out.println("Account currently has no cards!");
            return null;
        }

        for (var item: cards) {
            System.out.println(item.toString() + '\n');
        }

        int index_card = 0;
        while (true){
            System.out.print("Write the index of the card you want to use [0/1/2...]: ");
            index_card = scan.nextInt();

            if (index_card < 0 || index_card >= cards.size()){
                System.out.print("Wrong index!");
            }
            else{
                break;
            }
        }

        return cards.get(index_card);
    }
}
