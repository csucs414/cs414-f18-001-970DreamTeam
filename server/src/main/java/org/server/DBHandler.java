package org.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class DBHandler {
	
	static final String dbUser   = "cakitten";
	static final String dbPasswd = "83115757";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection conn;
    private Session session;

    // TODO: figure out connecting to DB
	public DBHandler() {
        final String keypath = "~/.ssh/id_rsa";
        final int port = 22;
        String host = "denver.cs.colostate.edu";
//	    String host = "129.82.44.141";

        try {
            JSch jsch = new JSch();
            jsch.addIdentity(keypath);
            jsch.setKnownHosts("~/.ssh/known_hosts");
            session = jsch.getSession(dbUser, host, port);
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            session.setPortForwardingL(12345, "localhost", 54321);
System.out.println(session.isConnected());
            System.out.println(session.getPortForwardingL()[0]);
            System.out.println("IP of my system is := "+session.getHost());

        }
        catch (JSchException e) {
            throw new RuntimeException("Failed to create Jsch Session object.", e);
        }
        dbConnect();
//        System.out.println("DB Connected");
	}

	private void dbConnect(){
        try {

            Class.forName(JDBC_DRIVER);
            String url = "jdbc:mysql://faure/" + dbUser + "?serverTimezone=UTC";

            System.out.println("before conn");
            conn = DriverManager.getConnection(url, dbUser, dbPasswd);
            System.out.println("after conn");

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

	public static void main(String args[]){
	    DBHandler db = new DBHandler();
	    db.addUser("cakitten", "pass","colin@email.com");
    }

}

