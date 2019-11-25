package client;

import java.sql.*

public class DBHandler {
	
	static final String dbUser   = "cakitten";
	static final String dbPasswd = "83115757";
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

	DBHandler() {
        
		Connection conn = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL, dbUser, dbPasswd);

            //STEP 4: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE REGISTRATION "
                    + "(id INTEGER not NULL, "
                    + " first VARCHAR(255), "
                    + " last VARCHAR(255), "
                    + " age INTEGER, "
                    + " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
	}
	
	boolean verifyUser(string userName, string passwd) {
        Statement stmt = null;
		
        //STEP 4: Execute a query
        System.out.println("Creating table in given database...");
        stmt = conn.createStatement();

        String sql = "CREATE TABLE REGISTRATION "
                + "(id INTEGER not NULL, "
                + " first VARCHAR(255), "
                + " last VARCHAR(255), "
                + " age INTEGER, "
                + " PRIMARY KEY ( id ))";
		
		return false;
	}
	
}
