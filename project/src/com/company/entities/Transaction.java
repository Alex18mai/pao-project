package com.company.entities;

public class Transaction {
    Account fromAccount;
    Card fromCard;

    Account toAccount;
    Card toCard;

    double amount;

    //constructors
    public Transaction() { }

    public Transaction(Account fromAccount, Card fromCard, Account toAccount, Card toCard, double amount) {
        this.fromAccount = fromAccount;
        this.fromCard = fromCard;
        this.toAccount = toAccount;
        this.toCard = toCard;
        this.amount = amount;
    }

    //getters
    public Account getFromAccount() {
        return fromAccount;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public Card getToCard() {
        return toCard;
    }

    public double getAmount() {
        return amount;
    }

    //setters
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setFromCard(Card fromCard) {
        this.fromCard = fromCard;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
