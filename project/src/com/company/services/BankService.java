package com.company.services;

import com.company.entities.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class BankService {
    static BankService instance = null;
    static String status = "Not created";
    List<Account> accounts;

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
    private BankService() {
        this.accounts = new ArrayList<Account>();
    }

    //setter
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    //Create
    public void addAccount(boolean isPremium, String firstName, String lastName, String email){
        Account account;
        if (isPremium)
            account = new PremiumAccount(firstName, lastName, email);
        else
            account = new BasicAccount(firstName, lastName, email);

        this.accounts.add(account);
    }

    public void addCard(boolean isCredit, String accountEmail, double balance, double limit){
        Account account = this.getAccountByEmail(accountEmail);
        if (account == null){
            System.out.printf("There are no accounts with the %s email!%n", accountEmail);
            return;
        }

        Card card;
        if (isCredit)
            card = new CreditCard(account, balance, limit);
        else
            card = new DebitCard(account, balance);

        account.addCard(card);
    }

    public void makeTransaction(Card cardPaying, Card cardReceiving, double amount){
        Transaction transaction = new Transaction(cardPaying.getAccount(), cardPaying, cardReceiving.getAccount(), cardReceiving, amount);
        cardPaying.getAccount().addTransaction(transaction);
        cardReceiving.getAccount().addTransaction(transaction);

        cardPaying.pay(amount);
        cardReceiving.receive(amount);
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

    public List<Account> getAllAccountsSortedByBalance(){
        Collections.sort(this.accounts);
        return this.accounts;
    }

    public List<Card> getCardsOfAccount(String email){
        Account account = getAccountByEmail(email);

        if (account == null) return null;

        return account.getCards();
    }

    //Update
    public void updateAccount(String email, String newFirstName, String newLastName){
        Account account = this.getAccountByEmail(email);
        if (account == null) return;
        account.setFirstName(newFirstName);
        account.setLastName(newLastName);
    }

    public void addMoneyToCard(Card card, double amount){
        card.receive(amount);
    }

    public void withdrawMoneyFromCard(Card card, double amount){
        card.pay(amount);
    }

    public void addMoneyToSavings(String email, double amount) throws Exception{
        Account account = this.getAccountByEmail(email);
        if (account == null)
            throw new Exception("Acest cont nu exista!");
        if (amount < 0)
            throw new Exception("In savings se poate adauga doar o suma pozitiva!");
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
