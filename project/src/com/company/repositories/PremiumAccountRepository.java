package com.company.repositories;

import com.company.config.DatabaseConfiguration;
import com.company.entities.PremiumAccount;

import java.sql.*;

public class PremiumAccountRepository {
    static PremiumAccountRepository instance = null;
    static String status = "Not created";

    //get instance
    public static PremiumAccountRepository getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new PremiumAccountRepository();
            instance.createTable();
        }
        return instance;
    }

    //constructor
    private PremiumAccountRepository() { }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS premium_account" +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "firstName varchar(255), " +
                "lastName varchar(255), " +
                "email varchar(255)," +
                "payback double(6, 2))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPremiumAccount(PremiumAccount premiumAccount) {
        String insertSql = "INSERT INTO premium_account(firstName, lastName, email, payback) VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, premiumAccount.getFirstName());
            preparedStatement.setString(2, premiumAccount.getLastName());
            preparedStatement.setString(3, premiumAccount.getEmail());
            preparedStatement.setDouble(4, premiumAccount.getPayback());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PremiumAccount getPremiumAccountByEmail(String email) {
        String selectSql = "SELECT * FROM premium_account WHERE email = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToPremiumAccount(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePremiumAccountName(String firstName, String lastName, String email) {
        String updateSql = "UPDATE premium_account SET firstName=?, lastName=? WHERE email=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PremiumAccount mapToPremiumAccount(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new PremiumAccount(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
        }
        return null;
    }

    public void displayPremiumAccounts() {
        String selectSql = "SELECT * FROM premium_account";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Premium Account:");
                System.out.println("First Name: " + resultSet.getString(2));
                System.out.println("Last Name: " + resultSet.getString(3));
                System.out.println("Email: " + resultSet.getString(4));
                System.out.println("Payback: " + resultSet.getDouble(5) + "%");
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePremiumAccount (String email) {
        String deleteSql = "DELETE FROM premium_account WHERE email = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
