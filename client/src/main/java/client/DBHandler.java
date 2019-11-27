package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {
	
	static final String dbUser   = "cakitten";
	static final String dbPasswd = "83115757";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection conn;

	public DBHandler() {

        try {
            Class.forName(JDBC_DRIVER);
            String url = "jdbc:mysql://faure/" + dbUser + "?serverTimezone=UTC";

            conn = DriverManager.getConnection(url, dbUser, dbPasswd);

            System.out.println("URL: " + url);
            System.out.println("Connection: " + conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public boolean verifyUser(String userName, String passwd) {

        Statement stmt;
		
        //STEP 4: Execute a query
        System.out.println("Creating table in given database...");
        try {
            stmt = conn.createStatement();

            String sql = "CREATE TABLE REGISTRATION "
                    + "(id INTEGER not NULL, "
                    + " first VARCHAR(255), "
                    + " last VARCHAR(255), "
                    + " age INTEGER, "
                    + " PRIMARY KEY ( id ))";
            stmt.execute(sql);

        } catch (Exception e) {
            System.out.print(e);

        } finally {
            return false;
        }
	}

	public static void main(String[] args){
	    DBHandler db = new DBHandler();
	    db.verifyUser("Colin", "1234");
    }

}
