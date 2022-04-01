package com.company.entities;

public class CreditCard extends Card{
    double limit; //the balance can be negative until reaching negative limit

    //constructor
    public CreditCard(Account account, double balance, double limit) {
        super(account, balance);
        this.limit = limit;
    }

    //getters
    public double getLimit() {
        return limit;
    }

    //setters
    public void setLimit(double limit) {
        this.limit = limit;
    }

    //functions
    @Override
    public boolean canPay(double amount){
        return this.balance - account.calculateWithPayback(amount) >= -limit;
    }

    @Override
    public void pay(double amount){
        if (!canPay(account.calculateWithPayback(amount))){
            System.out.printf("Cannot pay amount %f because the balance (%f) would be lower than the limit (%f)!%n", amount, this.balance, this.limit);
            return;
        }
        balance -= account.calculateWithPayback(amount);
    }

    @Override
    public void receive(double amount){
        balance += amount;
    }

    //to string
    @Override
    public String toString() {
        String str = "Credit Card:\n";
        str += "Balance: " + this.balance + '\n';
        str += "Limit: " + this.limit + '\n';
        return str;
    }
}
