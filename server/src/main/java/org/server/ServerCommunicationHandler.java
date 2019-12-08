package org.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerCommunicationHandler extends Thread {
	private Socket socket;
	private Server server;
	private String messageType;
	private HashMap<String, String> message;
	private int gameID;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private DBHandler dbhandler;
	
	public ServerCommunicationHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		this.dbhandler = new DBHandler();
	}
	
	private void handleMessage(Object map) {
		message = (HashMap) map;
		gameID = Integer.parseInt(message.get("gameID")); 
		messageType = message.get("messageType");
		
		switch (messageType) {
			case "Login":
				handleInput("Login"); break;
			case "Register":
				handleInput("Register"); break;
			case "Update":
				handleUpdate(); break;
			case "Invite":
			default:
				System.out.println("Messsage Failure! "+messageType+" is not a valid messageType");
 		}
	}

	public void handleInput(String type) {
		HashMap<String, String> inputMessage = new HashMap<String, String>();
		
		if (type == "Login") inputMessage.put("messageType", "Login");
		else inputMessage.put("messageType", "Register");
		
		boolean boolName = dbhandler.checkName(message.get("Name"));
		boolean boolEmail = dbhandler.checkEmail(message.get("Email"));
		
		if (type == "Login" && !boolName) {
			inputMessage.put("Success", "0");
			inputMessage.put("errorCode", "name");
		}
		else if (type == "Login" && !boolEmail) {
			inputMessage.put("Success", "0");
			inputMessage.put("errorCode", "email");
		}
		else if (type == "Register" && boolName) {
			inputMessage.put("Success", "0");
			inputMessage.put("errorCode", "name");
		}
		else if (type == "Register" && boolEmail) {
			inputMessage.put("Success", "0");
			inputMessage.put("errorCode", "email");
		}
		
		else {
			boolean success = false;
			if (type == "Register") {
				 success = dbhandler.addUser(message.get("Name"), message.get("Password"), message.get("Email"));
			}
			else if (type == "Login") {
				success = dbhandler.verifyPassword(message.get("Name"), message.get("Password"));
			}
			
			if (success == false) {
				inputMessage.put("Success", "0");
				if (type == "Login") inputMessage.put("errorCode", "password");
				else inputMessage.put("errorCode", "database");
			}
			else {
				inputMessage.put("Success", "1");
				inputMessage.put("errorCode", null);
			}
		}
		
		String players = "";
		ArrayList<String> onlinePlayers = server.getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.size(); i++) {
			players += onlinePlayers.get(i) + ",";
		}
		inputMessage.put("Players", players);
		
		try {
			output.writeObject(inputMessage);
			} catch(IOException e) {
				System.out.println("ERROR! Cannot write to output!");
			}
	}
	
	
	public void handleUpdate() {
		String updatedGameState = message.get("gameBoard");
		int turn = Integer.parseInt(message.get("turn"));
		ServerGame game = server.getGame(gameID);
		game.updateGameState(stringBoardToArray(updatedGameState), turn);
		
		
		/*for (char[] row: b) {
			System.out.println("new row!!!!!1");
			for (char c: row) {
				System.out.println(c);
			}
		}*/		
	}
	
	public char[][] stringBoardToArray(String board) {
		CharacterIterator it = new StringCharacterIterator(board);
		char[][] formattedBoard = new char[11][11];
		
		
		for (int i=0; i<formattedBoard.length; i++) {
			for (int j=0; j<formattedBoard[i].length;j++) {
				if (i==0 && j==0) {
					formattedBoard[i][j] = it.current();
				} else {
					formattedBoard[i][j] = it.next();
				}
			}
		}
		
		
		
		return formattedBoard;
	}
	
	
	public void run() {
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			handleMessage(input.readObject());	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	
}
