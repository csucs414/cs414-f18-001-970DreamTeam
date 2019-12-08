package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
			String players = message.get("Players");
			ArrayList<String> list = (ArrayList<String>) Arrays.asList(players.split("\\s*,\\s*"));
			client.validCredentials(list);
		 }
	 }
	 private void handleUpdate() {
		 
	 }
	 private void handleInvite() {
		 
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










