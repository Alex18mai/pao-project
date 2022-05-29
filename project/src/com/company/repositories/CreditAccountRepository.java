package com.company.repositories;

import com.company.config.DatabaseConfiguration;
import com.company.entities.CreditCard;
import com.company.models.CreditCardModel;

import java.sql.*;

public class CreditAccountRepository {
    static CreditAccountRepository instance = null;
    static String status = "Not created";

    //get instance
    public static CreditAccountRepository getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new CreditAccountRepository();
            instance.createTable();
        }
        return instance;
    }

    //constructor
    private CreditAccountRepository() { }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS credit_card" +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "accountEmail varchar(255), " +
                "balance double(15, 2), " +
                "cardLimit double(15, 2))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCreditCard(CreditCardModel creditCard) {
        String insertSql = "INSERT INTO credit_card(accountEmail, balance, cardLimit) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, creditCard.getAccountEmail());
            preparedStatement.setDouble(2, creditCard.getBalance());
            preparedStatement.setDouble(3, creditCard.getLimit());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CreditCardModel getCreditCardByAccountEmail(String accountEmail) {
        String selectSql = "SELECT * FROM credit_card WHERE accountEmail = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, accountEmail);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCreditCard(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCreditCardBalance(double balance, String accountEmail) {
        String updateSql = "UPDATE credit_card SET balance=? WHERE accountEmail=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, accountEmail);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CreditCardModel mapToCreditCard(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new CreditCardModel(resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4));
        }
        return null;
    }

    public void displayCreditCards() {
        String selectSql = "SELECT * FROM credit_card";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Credit Card:");
                System.out.println("Account Email: " + resultSet.getString(2));
                System.out.println("Balance: " + resultSet.getDouble(3));
                System.out.println("Limit: " + resultSet.getDouble(4));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCreditCard (String accountEmail) {
        String deleteSql = "DELETE FROM credit_card WHERE accountEmail = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, accountEmail);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
