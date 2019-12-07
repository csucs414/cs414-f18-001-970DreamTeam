package org.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
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
			case "Register":
			case "Update":
				handleUpdate(); break;
			case "Invite":
			default:
				System.out.println("Messsage Failure! "+messageType+" is not a valid messageType");
 		}
	}
	
	public void handleLogin() {
		
	}
	
	public void handleRegister() {
		if (!dbhandler.addUser(message.get("Name"), message.get("Password"), message.get("Email"))) {
			HashMap<String, String> failure = new HashMap<String, String>();
			failure.put("messageType", "Register");
			failure.put("Success", "0");
			
			try {
			output.writeObject(failure);
			} catch(IOException e) {
				System.out.println("ERROR! Cannot write to output!");
			}
		}
		
		handleLogin();
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
