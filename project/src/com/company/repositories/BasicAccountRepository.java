package com.company.repositories;

import com.company.config.DatabaseConfiguration;
import com.company.entities.BasicAccount;
import com.company.services.CSVService;

import java.sql.*;

public class BasicAccountRepository {
    static BasicAccountRepository instance = null;
    static String status = "Not created";

    //get instance
    public static BasicAccountRepository getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new BasicAccountRepository();
            instance.createTable();
        }
        return instance;
    }

    //constructor
    private BasicAccountRepository() { }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS basic_account" +
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

    public void insertBasicAccount(BasicAccount basicAccount) {
        String insertSql = "INSERT INTO basic_account(firstName, lastName, email, payback) VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, basicAccount.getFirstName());
            preparedStatement.setString(2, basicAccount.getLastName());
            preparedStatement.setString(3, basicAccount.getEmail());
            preparedStatement.setDouble(4, basicAccount.getPayback());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BasicAccount getBasicAccountByEmail(String email) {
        String selectSql = "SELECT * FROM basic_account WHERE email = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToBasicAccount(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBasicAccountName(String firstName, String lastName, String email) {
        String updateSql = "UPDATE basic_account SET firstName=?, lastName=? WHERE email=?";

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

    private BasicAccount mapToBasicAccount(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new BasicAccount(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
        }
        return null;
    }

    public void displayBasicAccounts() {
        String selectSql = "SELECT * FROM basic_account";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Basic Account:");
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

    public void deleteBasicAccount (String email) {
        String deleteSql = "DELETE FROM basic_account WHERE email = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
