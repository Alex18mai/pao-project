package com.company.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Account implements Comparable<Account>{
    String firstName;
    String lastName;
    String email;

    List<Card> cards; //an account can have multiple cards
    Savings savings; //an account can have savings
    List<Transaction> transactions; //here we store all the transactions associated with the account

    //constructors
    public Account() {
        this.cards = new ArrayList<>();
        this.savings = new Savings(this);
        this.transactions = new ArrayList<>();
    }

    public Account(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.cards = new ArrayList<>();
        this.savings = new Savings(this);
        this.transactions = new ArrayList<>();
    }

    //getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Savings getSavings() {
        return savings;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    //functions
    public abstract double calculateWithPayback(double amount);

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        boolean cardRemoved = this.cards.remove(card);
        if (!cardRemoved){
            System.out.println("No card was removed (it does not exist in the account)!");
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    protected double getTotalBalance(){
        double totalBalance = 0;
        for (var item : cards) {
            totalBalance += item.getBalance();
        }
        return totalBalance;
    }

    //compare to
    @Override
    public int compareTo(Account other) {
        return Double.compare(this.getTotalBalance(), other.getTotalBalance());
    }
}
