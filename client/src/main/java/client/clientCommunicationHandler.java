package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class clientCommunicationHandler {
	 DataInputStream input;
	 DataOutputStream output;
	 Socket socket;
	      
	  
	 // Constructor 
	 public clientCommunicationHandler(Socket socket, DataInputStream input, DataOutputStream output){ 
	        this.socket = socket; 
	        this.input = input; 
	        this.output = output; 
	 } 
	  
}
