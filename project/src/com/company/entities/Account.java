package com.company.entities;

import java.util.ArrayList;

public abstract class Account implements Comparable<Account>{
    String firstName;
    String lastName;
    String email;

    ArrayList<Card> cards; //an account can have multiple cards
    Savings savings; //an account can have savings
    ArrayList<Transaction> transactions; //here we store all the transactions associated with the account

    //constructor
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Savings getSavings() {
        return savings;
    }

    public ArrayList<Transaction> getTransactions() {
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

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
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
