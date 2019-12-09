package org.server;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

	public HashMap<Integer, ServerGame> games = new HashMap<Integer, ServerGame>();
	public HashMap<String, ObjectOutputStream> playerSockets = new HashMap<String, ObjectOutputStream>();
	private ArrayList<String> onlinePlayers = new ArrayList<String>();
	public static int nextGameID = 0;
	private Socket serverSocket;
	

	public int createNewGame(String[] playerIDs,  ObjectOutputStream[] playerSockets) {
		ServerGame newGame = new ServerGame(playerIDs, playerSockets);
		int newGameID = nextGameID;
		nextGameID += 1;
		games.put(newGameID, newGame);
		return newGameID;
	}

	public void deleteGame(int gameID) {
		games.remove(gameID);
	}

	public ServerGame getGame(int gameID) {
		return games.get(gameID);
	}

	public void addPlayer(String player) {
		onlinePlayers.add(player);
	}

	public ArrayList<String> getOnlinePlayers() {
		return onlinePlayers;
	}

	public void removePlayer(String player) {
		onlinePlayers.remove(player);
	}
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		try {
			int portNumber = 20001;
			ServerSocket openSocket = new ServerSocket(portNumber);
			System.out.println("Server listening on port: "+ portNumber);
			while (true) {
				new ServerCommunicationHandler(server, openSocket.accept()).start();
			}
		} catch (Exception e) {
				e.printStackTrace();
		} finally {
			try {
			server.serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
