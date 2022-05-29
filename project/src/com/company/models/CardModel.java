package com.company.models;

// Used in order to have FK in DB
public abstract class CardModel {
    String accountEmail; //FK
    double balance;

    public CardModel() {
    }

    public CardModel(String accountEmail, double balance) {
        this.accountEmail = accountEmail;
        this.balance = balance;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
