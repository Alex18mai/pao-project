package com.company.services;

import com.company.entities.*;
import com.company.models.CardModel;
import com.company.models.CreditCardModel;
import com.company.models.DebitCardModel;
import com.company.models.SavingsModel;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    static AccountService instance = null;
    static String status = "Not created";

    //get instance
    public static AccountService getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new AccountService();
        }
        return instance;
    }

    //constructor
    private AccountService() { }

    public List<Account> readAccountsFromCSV() {

        List <Account> accounts = new ArrayList<>();

        // Get Basic and Premium Accounts
        List <BasicAccount> basicAccounts = CSVService.read("src/com/company/data/basicAccount.csv", BasicAccount.class);
        List <PremiumAccount> premiumAccounts = CSVService.read("src/com/company/data/premiumAccount.csv", PremiumAccount.class);

        // Get Credit and Debit Cards (we use models in order to retrieve the FK and map it to accounts)
        List <CreditCardModel> creditCardModels = CSVService.read("src/com/company/data/creditCardModel.csv", CreditCardModel.class);
        List <DebitCardModel> debitCardModels = CSVService.read("src/com/company/data/debitCardModel.csv", DebitCardModel.class);

        // Get Savings (we use models in order to retrieve the FK and map it to accounts)
        List <SavingsModel> savingsModels = CSVService.read("src/com/company/data/savingsModel.csv", SavingsModel.class);

        accounts.addAll(basicAccounts);
        accounts.addAll(premiumAccounts);

        // Card Model to Card + add to accounts
        for (var creditCardModel : creditCardModels){
            Account accountFromEmail = null;
            for (var account : accounts){
                if (creditCardModel.getAccountEmail().equals(account.getEmail())) {
                    accountFromEmail = account;
                    break;
                }
            }

            if (accountFromEmail == null){
                System.out.printf("Error while reading the credit cards of the CSV -> There are no accounts with the %s email!%n", creditCardModel.getAccountEmail());
            }
            else {
                Card card = new CreditCard(accountFromEmail, creditCardModel.getBalance(), creditCardModel.getLimit());
                accountFromEmail.addCard(card);
            }
        }

        for (var debitCardModel : debitCardModels){
            Account accountFromEmail = null;
            for (var account : accounts){
                if (debitCardModel.getAccountEmail().equals(account.getEmail())) {
                    accountFromEmail = account;
                    break;
                }
            }

            if (accountFromEmail == null){
                System.out.printf("Error while reading the debit cards of the CSV -> There are no accounts with the %s email!%n", debitCardModel.getAccountEmail());
            }
            else {
                Card card = new DebitCard(accountFromEmail, debitCardModel.getBalance());
                accountFromEmail.addCard(card);
            }
        }

        // SavingsModel to Savings + add to accounts
        for (var savingsModel : savingsModels){
            Account accountFromEmail = null;
            for (var account : accounts){
                if (savingsModel.getAccountEmail().equals(account.getEmail())) {
                    accountFromEmail = account;
                    break;
                }
            }

            if (accountFromEmail == null){
                System.out.printf("Error while reading the savings of the CSV -> There are no accounts with the %s email!%n", savingsModel.getAccountEmail());
            }
            else {
                Savings savings = new Savings(accountFromEmail, savingsModel.getBalance());
                accountFromEmail.setSavings(savings);
            }
        }

        return accounts;
    }

    public void writeAccountsToCSV(List<Account> accounts) {

        //Create Basic and Premium account lists
        List <BasicAccount> basicAccounts = new ArrayList<>();
        List <PremiumAccount> premiumAccounts = new ArrayList<>();

        // Create Credit and Debit Card Models (map FK to account)
        List <CreditCardModel> creditCardModels = new ArrayList<>();
        List <DebitCardModel> debitCardModels = new ArrayList<>();

        // Create Savings Models (map FK to account)
        List <SavingsModel> savingsModels = new ArrayList<>();

        for (var account: accounts){
            if (account instanceof BasicAccount){
                basicAccounts.add((BasicAccount) account);
            }
            else if (account instanceof PremiumAccount){
                premiumAccounts.add((PremiumAccount) account);
            }

            for (var card: account.getCards()){
                if (card instanceof CreditCard){
                    CreditCardModel creditCardModel = new CreditCardModel(account.getEmail(), card.getBalance(), ((CreditCard) card).getLimit());
                    creditCardModels.add(creditCardModel);
                }
                else if (card instanceof DebitCard){
                    DebitCardModel debitCardModel = new DebitCardModel(account.getEmail(), card.getBalance());
                    debitCardModels.add(debitCardModel);
                }
            }

            SavingsModel savingsModel = new SavingsModel(account.getEmail(), account.getSavings().getBalance());
            savingsModels.add(savingsModel);
        }

        // Write data to CSV
        CSVService.write(basicAccounts,"src/com/company/data/basicAccount.csv", BasicAccount.class);
        CSVService.write(premiumAccounts,"src/com/company/data/premiumAccount.csv", PremiumAccount.class);

        CSVService.write(creditCardModels,"src/com/company/data/creditCardModel.csv", CreditCardModel.class);
        CSVService.write(debitCardModels,"src/com/company/data/debitCardModel.csv", DebitCardModel.class);

        CSVService.write(savingsModels,"src/com/company/data/savingsModel.csv", SavingsModel.class);

    }

}
