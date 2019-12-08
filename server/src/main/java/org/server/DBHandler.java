package org.server;

import java.sql.*;

public class DBHandler {
    private static Connection conn;

    public DBHandler() {
        final String dbUser      = "user";
        final String dbPasswd    = "970dream";
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String dburl       = "jdbc:mysql://160.153.47.5:3306/970dreamteam";

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(dburl, dbUser, dbPasswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // addUser will return true if user was successfully added
    // false otherwise.
    public boolean addUser(String userName, String passwd, String email){
        try {

            String query = "INSERT into users" +
                    "(Name, Password, Email)" +
                    "VALUES " +
                    "(?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, userName);
            preparedStmt.setString (2, passwd);
            preparedStmt.setString (3, email);

            preparedStmt.execute();
            return true;

        } catch (Exception e){
            System.out.print(e);
            return false;
        }
    }

    // removeUser will return true if user was successfully removed
    // false otherwise.
    public boolean removeUser(String userName){
        try {

            String query = "DELETE FROM users WHERE Name = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, userName);

            preparedStmt.execute();
            return true;

        } catch (Exception e){
            System.out.print(e);
            return false;
        }
    }

    // Checks if the user name exists. Returns true if found false otherwise
    public boolean checkName(String userName){
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();

            String query = "SELECT * FROM users"+
                           " WHERE Name = '" +
                             userName + "'";
            rs = stmt.executeQuery(query);

            int size = countResults(rs);

            return size != 0;

        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    // Checks if the user email exists. Returns true if found false otherwise
    public boolean checkEmail(String email){
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();

            String query = "SELECT * FROM users WHERE Email = '" + email + "'";
            rs = stmt.executeQuery(query);
            int size = countResults(rs);

            return size != 0;

        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    public boolean verifyPassword(String userName, String passwd) {

        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();

            String query = "SELECT * FROM users WHERE Name = '"
                           + userName + "' AND Password = '" + passwd + "'";
            rs = stmt.executeQuery(query);
            int size = countResults(rs);

            return size != 0;

        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    private int countResults(ResultSet results) throws SQLException {
        int counter = 0;
        while (results.next()) {
            counter++;
        }
        return counter;
    }
}

