package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHandler {
	
	static final String dbUser   = "cakitten";
	static final String dbPasswd = "83115757";
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private Connection conn;

    // TODO: figure out connecting to DB
	public DBHandler() {

        try {
            Class.forName(JDBC_DRIVER);
            String url = "jdbc:mariadb://faure/" + dbUser + "?serverTimezone=UTC";

            conn = DriverManager.getConnection(url, dbUser, dbPasswd);

            System.out.println("URL: " + url);
            System.out.println("Connection: " + conn);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
	}

    // addUser will return true if user was successfully removed
    // false otherwise.
	public boolean addUser(String userName, String passwd, String email){
	    try {

	        String query = "INSERT into users" +
                         "(username, password, email)" +
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

            String query = "DELETE FROM users WHERE username = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, userName);

            preparedStmt.execute();
            return true;

        } catch (Exception e){
            System.out.print(e);
            return false;
        }
    }
	
	public boolean verifyPassword(String userName, String passwd) {

        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();

            String query = "SELECT FROM users WHERE username = " + userName + " AND password = " + passwd;
            rs = stmt.executeQuery(query);

            if(rs != null){
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            System.out.print(e);
        }
        return false;
	}

}
