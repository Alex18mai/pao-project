package com.company.entities;

public class DebitCard extends Card{

    //constructor
    public DebitCard(Account account, double balance) {
        super(account, balance);
    }

    //functions
    @Override
    public boolean canPay(double amount){
        return this.balance >= account.calculateWithPayback(amount);
    }

    @Override
    public void pay(double amount){
        if (!canPay(account.calculateWithPayback(amount))){
            System.out.printf("Cannot pay amount %f because the balance is lower (%f)!%n", amount, this.balance);
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
        String str = "Debit Card:\n";
        str += "Balance: " + this.balance + '\n';
        return str;
    }
}
