package com.company.repositories;

import com.company.config.DatabaseConfiguration;
import com.company.entities.DebitCard;
import com.company.models.DebitCardModel;

import java.sql.*;

public class DebitCardRepository {
    static DebitCardRepository instance = null;
    static String status = "Not created";

    //get instance
    public static DebitCardRepository getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new DebitCardRepository();
            instance.createTable();
        }
        return instance;
    }

    //constructor
    private DebitCardRepository() { }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS debit_card" +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "accountEmail varchar(255), " +
                "balance double(15, 2))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDebitCard(DebitCardModel debitCard) {
        String insertSql = "INSERT INTO debit_card(accountEmail, balance) VALUES(?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, debitCard.getAccountEmail());
            preparedStatement.setDouble(2, debitCard.getBalance());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DebitCardModel getDebitCardByAccountEmail(String accountEmail) {
        String selectSql = "SELECT * FROM debit_card WHERE accountEmail = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, accountEmail);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDebitCard(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDebitCardBalance(double balance, String accountEmail) {
        String updateSql = "UPDATE debit_card SET balance=? WHERE accountEmail=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, accountEmail);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DebitCardModel mapToDebitCard(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new DebitCardModel(resultSet.getString(2), resultSet.getDouble(3));
        }
        return null;
    }

    public void displayDebitCards() {
        String selectSql = "SELECT * FROM debit_card";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Debit Card:");
                System.out.println("Account Email: " + resultSet.getString(2));
                System.out.println("Balance: " + resultSet.getDouble(3));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayDebitCards(String accountEmail) {
        String selectSql = "SELECT * FROM debit_card where accountEmail = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) { //try with resources
            preparedStatement.setString(1, accountEmail);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Debit Card:");
                System.out.println("Account Email: " + resultSet.getString(2));
                System.out.println("Balance: " + resultSet.getDouble(3));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDebitCard (String accountEmail) {
        String deleteSql = "DELETE FROM debit_card WHERE accountEmail = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, accountEmail);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
