package com.company.entities;

public class Savings {
    Account account;
    double balance;

    //constructors
    public Savings() { }

    public Savings(Account account) {
        this.account = account;
    }

    public Savings(Account account, double balance) {
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
    public void addSavings(double amount){
        this.balance += amount;
    }

    public void removeSavings(double amount){
        if (this.balance < amount){
            System.out.printf("Cannot remove %f from savings because the balance is lower (%f)!%n", amount, this.balance);
            return;
        }
        balance -= amount;
    }
}
