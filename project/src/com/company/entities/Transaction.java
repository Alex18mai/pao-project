package com.company.entities;

public class Transaction {
    Account from_account;
    Card from_card;

    Account to_account;
    Card to_card;

    double amount;

    //constructor
    public Transaction(Account from_account, Card from_card, Account to_account, Card to_card, double amount) {
        this.from_account = from_account;
        this.from_card = from_card;
        this.to_account = to_account;
        this.to_card = to_card;
        this.amount = amount;
    }

    //getters
    public Account getFrom_account() {
        return from_account;
    }

    public Card getFrom_card() {
        return from_card;
    }

    public Account getTo_account() {
        return to_account;
    }

    public Card getTo_card() {
        return to_card;
    }

    public double getAmount() {
        return amount;
    }

    //setters
    public void setFrom_account(Account from_account) {
        this.from_account = from_account;
    }

    public void setFrom_card(Card from_card) {
        this.from_card = from_card;
    }

    public void setTo_account(Account to_account) {
        this.to_account = to_account;
    }

    public void setTo_card(Card to_card) {
        this.to_card = to_card;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
