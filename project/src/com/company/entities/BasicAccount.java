package com.company.entities;

public class BasicAccount extends Account{
    static double payback = 0.0; //basic accounts do not have a payback option on transactions

    //constructor
    public BasicAccount(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    //getters
    public static double getPayback() {
        return payback;
    }

    //functions
    @Override
    public double calculateWithPayback(double amount){
        return amount;
    }

    //to string
    @Override
    public String toString() {
        String str = "Basic Account:\n";
        str += "First Name: " + this.firstName + '\n';
        str += "Last Name: " + this.lastName + '\n';
        str += "Email: " + this.email + '\n';
        str += "Payback: " + payback + '%' + '\n';
        str += "Total Balance: " + this.getTotalBalance() + '\n';
        return str;
    }
}
