package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class clientCommunicationHandler extends Thread{
	 ObjectInputStream input;
	 ObjectOutputStream output;
	 private String messageType;
	 private HashMap<String, String> message;
	 private HashMap<String, String> outboundMessage;
	 int gameID;
	 Socket socket;
	 Client client;
	      
	  
	 // Constructor 
	 public clientCommunicationHandler(Socket socket, Client client){ 
	        this.socket = socket;
	        this.client = (Client)client;
	 } 
	 
	 public void actOnMessage(Object map){
		message = (HashMap) map;
		gameID = Integer.parseInt(message.get("gameID")); 
		messageType = message.get("messageType");
		
		 switch (messageType) { 
         
         	case "Update": 
         		//unpack message to get from and to coordinates
         		handleUpdate();
         		break; 
               
         	case "Register": 
         		handleRegister();
         		break; 
         		
         	case "Login": 
         		handleLogin();
         		break;
             
         	case "Invite":
         		handleInvite();
         		break;
  
         default: 
        	 System.out.println("Messsage Failure! " + messageType + " is not a valid messageType");
		 }
		 
		 
		 
	 }
	 public void outbound(Object map) {
		 try {
			output = new ObjectOutputStream(socket.getOutputStream());
			outboundMessage = (HashMap) map;
			output.writeObject(outboundMessage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 
	 }
	 private void handleLogin() {
		gameID  = Integer.parseInt(message.get("gameID")); 
		int loginStatus = Integer.parseInt(message.get("Success"));
		
		if (loginStatus == 0) {
			client.invalidCredentials();
		}
		else {
			//sending list of players
			String players = message.get("Players");
			ArrayList<String> list = (ArrayList<String>) Arrays.asList(players.split("\\s*,\\s*"));
			client.validCredentials(list);
		}
		 
	 }
	 private void handleRegister() {
		 int registerStatus = Integer.parseInt(message.get("Success"));
		 if (registerStatus == 0) {
			client.invalidCreation();
			}
		 else {
			
			String[] Players = message.get("Players").split(", ");
			List<String> playersList = Arrays.asList(Players);  
			client.validCredentials(playersList);
		 }
	 }
	 public void update(int[] from, int[] to, char[][] board, int turn, String[] players, int gameID) {
		 HashMap<String,String> map=new HashMap<String,String>();
		 map.put("messageType", "Update");
		 map.put("From", Integer.toString(from[0]) + ", " + Integer.toString(from[1]));
		 map.put("To", Integer.toString(to[0]) + ", " + Integer.toString(to[1]));
		 map.put("turn", Integer.toString(turn));
		 map.put("gameID", Integer.toString(gameID));
		 String board1 = "";
		 for(int i = 0; i < board.length; i++) {
			 for(int j = 0; j < board[i].length; j++) {
				 board1 += board[i][j];
			 }
		 }
		 map.put("gameBoard", board1);
		 outbound(map);
	 }
	 private void handleUpdate() {
		 int gameID = Integer.parseInt(message.get("gameID")); 
		 String From = message.get("From").replace(",", " ");
		 String To = message.get("To");
		 To = To.replace(",", " ");
		 From = From.replace(",", " ");
		 
		 int[] from = new int[2];
		 int[] to = new int[2];
		 String [] fromS = From.split(" ");
		 String [] toS = To.split(" ");
		 from[0] = Integer.parseInt(fromS[0]);
		 from[1] = Integer.parseInt(fromS[1]);
		 to[0] = Integer.parseInt(toS[0]);
		 to[1] = Integer.parseInt(toS[1]);
		 try {
			client.games.get(gameID).updateGameState(from, to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 private void handleInvite() {
		 String inviteType = message.get("inviteType");
		 
		 if(inviteType == "Request") {
			 int inviteSuccess = Integer.parseInt(message.get("Success"));
			 
			 if(inviteSuccess == 0) {
				 client.inviteFail();
			 }
		 }
		 else if(inviteType == "Response") {
			 String inviteResponse = message.get("Response");
			 
			 if(inviteResponse == "Accept") {
				 int gameID =  Integer.parseInt(message.get("gameID"));
				 String to = message.get("To");
				 client.inviteAccepted(gameID, to);
			 }
			 if(inviteResponse == "Decline") {
				 
				 client.inviteDeclinced();
			 }
		 }
		 
	 }
	 
	 public void run() {
		 	
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			while(true) {
				Object in = null;
				try {
					if((in = input.readObject()) != null) {
						actOnMessage(in);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	 
	  
}










