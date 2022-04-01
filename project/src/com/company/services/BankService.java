package com.company.services;

import com.company.entities.*;

import java.util.Collections;
import java.util.Objects;
import java.util.Vector;

public class BankService {
    static BankService instance = null;
    static String status = "Not created";
    Vector<Account> accounts;

    //get instance
    public static BankService getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new BankService();
        }
        return instance;
    }

    //constructor
    public BankService() {
        this.accounts = new Vector<Account>();
    }

    //Create
    public void addAccount(boolean is_premium, String first_name, String last_name, String email){
        Account account = null;
        if (is_premium)
            account = new PremiumAccount(first_name, last_name, email);
        else
            account = new BasicAccount(first_name, last_name, email);

        this.accounts.add(account);
    }

    public void addCard(boolean is_credit, String account_email, double balance, double limit){
        Account account = this.getAccountByEmail(account_email);

        Card card = null;
        if (is_credit)
            card = new CreditCard(account, balance, limit);
        else
            card = new DebitCard(account, balance);

        account.addCard(card);
    }

    public void makeTransaction(Card card_paying, Card card_receiving, double amount){
        Transaction transaction = new Transaction(card_paying.getAccount(), card_paying, card_receiving.getAccount(), card_receiving, amount);
        card_paying.getAccount().addTransaction(transaction);
        card_receiving.getAccount().addTransaction(transaction);

        card_paying.pay(amount);
        card_receiving.receive(amount);
    }

    //Read
    public Account getAccountByEmail(String email){
        Account account = null;

        for (var item : this.accounts){
            if (email.equals(item.getEmail())){
                account = item;
                break;
            }
        }

        if (account == null){
            System.out.printf("There are no accounts with the %s email!%n", email);
        }

        return account;
    }

    public Vector<Account> getAllAccountsSortedByBalance(){
        Collections.sort(this.accounts);
        return this.accounts;
    }

    public Vector<Card> getCardsOfAccount(String email){
        Account account = getAccountByEmail(email);

        if (account == null) return null;

        return account.getCards();
    }

    //Update
    public void updateAccount(String email, String new_first_name, String new_last_name){
        Account account = this.getAccountByEmail(email);
        if (account == null) return;
        account.setFirst_name(new_first_name);
        account.setLast_name(new_last_name);
    }

    public void addMoneyToCard(Card card, double amount){
        card.receive(amount);
    }

    public void withdrawMoneyFromCard(Card card, double amount){
        card.pay(amount);
    }

    public void addMoneyToSavings(String email, double amount){
        Account account = this.getAccountByEmail(email);
        if (account == null) return;
        account.getSavings().addSavings(amount);
    }

    //Delete
    public void deleteAccount(String email){
        Account account = this.getAccountByEmail(email);
        if (account == null) return;
        this.accounts.remove(account);
    }

    public void deleteCard(Card card){
        Account account = card.getAccount();
        account.removeCard(card);
    }

}
