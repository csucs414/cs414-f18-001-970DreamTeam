package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class clientCommunicationHandler {
	 DataInputStream input;
	 DataOutputStream output;
	 int gameID;
	 Socket socket;
	      
	  
	 // Constructor 
	 public clientCommunicationHandler(Socket socket, DataInputStream input, DataOutputStream output, int gameID){ 
	        this.socket = socket; 
	        this.input = input; 
	        this.output = output;
	        this.gameID = gameID;
	 } 
	 
	 public void actOnMessage() {
		 String received = null;
		try {
			received = input.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		 switch (received) { 
         
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
