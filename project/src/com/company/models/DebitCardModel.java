package com.company.models;

// Used in order to have FK in DB
public class DebitCardModel extends CardModel {

    public DebitCardModel() {
    }

    public DebitCardModel(String accountEmail, double balance) {
        super(accountEmail, balance);
    }
}
