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
	public DBHandler dbhandler;
	
	public ServerCommunicationHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		dbhandler = new DBHandler();
	}
	
	private void handleMessage(Object map) {
		message = (HashMap) map;

		messageType = message.get("messageType");
		System.out.println("Server Received a "+ messageType + " Message.");
		
		switch (messageType) {
			case "Login":
				handleInput("Login"); break;
			case "Register":
				handleInput("Register"); break;
			case "Update":
				handleUpdate(); break;
			case "Invite":
				handleInvite(); break;
			default:
				System.out.println("Messsage Failure! "+messageType+" is not a valid messageType");
 		}
	}
	
	public void handleInvite() {
		System.out.println("Entered handleInvite Method.");
		if (message.get("inviteType") == "Response") {
			if (message.get("Response") == "Accept") {
				String[] players = {message.get("From"), message.get("To")};
				ObjectOutputStream[] sockets = {server.playerSockets.get(message.get("From")), server.playerSockets.get(message.get("To"))};
				int gameID = server.createNewGame(players,  sockets);
				message.put("gameID", Integer.toString(gameID));
			}
		}
		
		if (!server.getOnlinePlayers().contains(message.get("To"))) {
			message.put("Success", "0");
			try {
				output.reset();
				output.writeObject(message);
			} catch (IOException e) {
				System.out.println("Request Input Failure in handleInvite!");
			}
		}
			
		else {
			message.put("Success", "1");
			System.out.println("Invite send from "+message.get("From")+ " to "+ message.get("To"));
			ObjectOutputStream outsocket = server.playerSockets.get(message.get("To"));
			try {
				outsocket.reset();
				outsocket.writeObject(message);
			} catch (IOException e) {
				System.out.println("Request Input Failure in handleInvite!");
			}
		}
	}
	
	public void refreshPlayers() {
		HashMap<String, String> refreshMessage = new HashMap<String, String>();
		refreshMessage.put("messageType", "Refresh");
		
		String players = "";
		ArrayList<String> onlinePlayers = server.getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.size(); i++) {
			players += onlinePlayers.get(i) + ", ";
		}
		
		refreshMessage.put("Players", players);
		try {
			output.writeObject(refreshMessage);
		} catch (IOException e) {
			System.out.println("Refresh player list failed!");
		}
		
	}
		
	public void handleInput(String type) {
		HashMap<String, String> inputMessage = new HashMap<String, String>();
		
		if (type == "Login") inputMessage.put("messageType", "Login");
		else inputMessage.put("messageType", "Register");
		
		boolean boolName = dbhandler.checkName(message.get("Name"));
		boolean boolEmail = true;
		if (type == "Register") boolEmail = dbhandler.checkEmail(message.get("Email"));
		
		if (type == "Login" && !boolName) {
			inputMessage.put("Success", "0");
			inputMessage.put("errorCode", "name");
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
				server.playerSockets.put(message.get("Name"), output);
				server.addPlayer(message.get("Name"));
				System.out.println("Player "+message.get("Name")+ " Connected.");
				inputMessage.put("Success", "1");
				inputMessage.put("errorCode", null);
				refreshPlayers();
			}
		}
		
		String players = "";
		ArrayList<String> onlinePlayers = server.getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.size(); i++) {
			players += onlinePlayers.get(i) + ", ";
		}
		inputMessage.put("Players", players);
		inputMessage.put("Name", message.get("Name"));
		try {
			output.writeObject(inputMessage);
			} catch(IOException e) {
				System.out.println("ERROR! Cannot write to output!");
			}
	}
	
	
	public void handleUpdate() {
		String updatedGameState = message.get("gameBoard");
		gameID = Integer.parseInt(message.get("gameID"));
		String[] Players = message.get("Players").split(", ");
		int turn = Integer.parseInt(message.get("turn"));
		ServerGame game = server.getGame(gameID);

		//update ServerGame state
		game.updateGameState(stringBoardToArray(updatedGameState), turn);

		//construct outbound Update message
		HashMap<String, String> outboundUpdate = new HashMap<String, String>();
		outboundUpdate.put("messageType", "Update");
		outboundUpdate.put("gameBoard", updatedGameState);
		outboundUpdate.put("gameID", message.get("gameID"));
		outboundUpdate.put("Players", message.get("Players"));
		outboundUpdate.put("To", message.get("To"));
		outboundUpdate.put("From", message.get("From"));


		//send updatedGameState back to both players
		//send updatedGameState back to both players
		HashMap<String, ObjectOutputStream> playerConnections = server.playerSockets;

		ObjectOutputStream player1Connection = playerConnections.get(Players[0]);
		ObjectOutputStream player2Connection = playerConnections.get(Players[1]);

		try {
			player1Connection.reset();
			player2Connection.reset();

			player1Connection.writeObject(outboundUpdate);
			player1Connection.flush();
			player2Connection.writeObject(outboundUpdate);
			player2Connection.flush();

		} catch(Exception e) {
			e.getStackTrace();
		}

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
			//handleMessage(input.readObject());
			while (true) {
				Object in =null;
				if ((in = input.readObject()) != null) {
					handleMessage(in);
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	
}
