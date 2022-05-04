package com.company.models;

// Used in order to have FK in DB
public class CreditCardModel extends CardModel {
    double limit;

    public CreditCardModel() {
    }

    public CreditCardModel(String accountEmail, double balance, double limit) {
        super(accountEmail, balance);
        this.limit = limit;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
