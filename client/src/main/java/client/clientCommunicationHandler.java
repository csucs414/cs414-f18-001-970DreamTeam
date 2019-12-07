package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class clientCommunicationHandler {
	 ObjectInputStream input;
	 ObjectOutputStream output;
	 private String messageType;
	 private HashMap<String, String> message;
	 int gameID;
	 Socket socket;
	      
	  
	 // Constructor 
	 public clientCommunicationHandler(Socket socket){ 
	        this.socket = socket;
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
	 
	 private void handleLogin() {
		gameID  = Integer.parseInt(message.get("gameID")); 
		int loginStatus = Integer.parseInt(message.get("Success"));
		
		if (loginStatus == 0) {
			
		}
		 
	 }
	 private void handleRegister() {
		 
	 }
	 private void handleUpdate() {
		 
	 }
	 private void handleInvite() {
		 
	 }
	 
	 public void run() {
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				actOnMessage(input.readObject());	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	  
}










