package com.company.entities;

public class PremiumAccount extends Account{
    static double payback = 1.5; //premium accounts have a 1.5% payback

    //constructors
    public PremiumAccount() { super(); }

    public PremiumAccount(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    //getters
    public static double getPayback() {
        return payback;
    }

    //functions
    @Override
    public double calculateWithPayback(double amount){
        return amount - amount * (payback / 100);
    }

    @Override
    public String toString() {
        String str = "Premium Account:\n";
        str += "First Name: " + this.firstName + '\n';
        str += "Last Name: " + this.lastName + '\n';
        str += "Email: " + this.email + '\n';
        str += "Payback: " + payback + '%' + '\n';
        str += "Total Balance: " + this.getTotalBalance() + '\n';
        return str;
    }
}