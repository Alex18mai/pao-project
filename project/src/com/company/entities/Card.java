package com.company.entities;

public abstract class Card {
    Account account;
    double balance;

    //constructors
    public Card() { }

    public Card(Account account, double balance) {
        this.account = account;
        this.balance = balance;
    }

    //getters
    public Account getAccount() {
        return account;
    }

    public double getBalance() {
        return balance;
    }

    //setters
    public void setAccount(Account account) {
        this.account = account;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //functions
    public abstract boolean canPay(double amount);

    public abstract void pay(double amount);

    public abstract void receive(double amount);
}
