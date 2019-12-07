package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class clientCommunicationHandler {
	 ObjectInputStream input;
	 ObjectOutputStream output;
	 int gameID;
	 Socket socket;
	      
	  
	 // Constructor 
	 public clientCommunicationHandler(Socket socket, ObjectInputStream input, ObjectOutputStream output, int gameID){ 
	        this.socket = socket; 
	        this.input = input; 
	        this.output = output;
	        this.gameID = gameID;
	 } 
	 
	 public void actOnMessage() throws ClassNotFoundException, IOException {
		HashMap received = (HashMap) input.readObject();
		
		String messageType = null;
		 switch (messageType) { 
         
         case "GameUpdate": 
            //unpack message to get from and to coordinates
        	 
             break; 
               
         case "Register": 
            
             break; 
         case "Login": 
             
             break;       
         
         default: 
        	 try {
				output.writeUTF("Invalid action for handler to act on");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
             break; 
		 } 
	 }
	  
}
