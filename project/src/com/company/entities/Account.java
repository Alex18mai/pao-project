package com.company.entities;

import java.util.Vector;

public abstract class Account implements Comparable<Account>{
    String first_name;
    String last_name;
    String email;

    Vector<Card> cards; //an account can have multiple cards
    Savings savings; //an account can have savings
    Vector<Transaction> transactions; //here we store all the transactions associated with the account

    //constructor
    public Account(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;

        this.cards = new Vector<Card>();
        this.savings = new Savings(this);
        this.transactions = new Vector<Transaction>();
    }

    //getters
    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public Vector<Card> getCards() {
        return cards;
    }

    public Savings getSavings() {
        return savings;
    }

    public Vector<Transaction> getTransactions() {
        return transactions;
    }

    //setters
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCards(Vector<Card> cards) {
        this.cards = cards;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public void setTransactions(Vector<Transaction> transactions) {
        this.transactions = transactions;
    }

    //functions
    public abstract double calculateWithPayback(double amount);

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    protected double getTotalBalance(){
        double total_balance = 0;
        for (var item : cards) {
            total_balance += item.getBalance();
        }
        return total_balance;
    }

    //compare to
    @Override
    public int compareTo(Account other) {
        return Double.compare(this.getTotalBalance(), other.getTotalBalance());
    }
}
