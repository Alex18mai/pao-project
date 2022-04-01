package com.company.entities;

public class PremiumAccount extends Account{
    static double payback = 1.5; //premium accounts have a 1.5% payback

    //constructor
    public PremiumAccount(String first_name, String last_name, String email) {
        super(first_name, last_name, email);
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
        str += "First Name: " + this.first_name + '\n';
        str += "Last Name: " + this.last_name + '\n';
        str += "Email: " + this.email + '\n';
        str += "Payback: " + payback + '%' + '\n';
        str += "Total Balance: " + this.getTotalBalance() + '\n';
        return str;
    }
}