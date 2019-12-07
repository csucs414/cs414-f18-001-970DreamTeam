package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

	private HashMap<Integer, ServerGame> games = new HashMap<Integer, ServerGame>();
	private ArrayList<String> onlinePlayers = new ArrayList<String>();
	private static int nextGameID = 0;
	private Socket serverSocket;
	private PrintWriter serverOutput;
	private BufferedReader serverInput;

	public void createNewGame(String[] players) {
		ServerGame newGame = new ServerGame(players);
		int newGameID = nextGameID;
		nextGameID += 1;
		games.put(newGameID, newGame);
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
			ServerSocket test = new ServerSocket(portNumber);
			System.out.println("Server listening on port: "+ portNumber);
			while (true) {
				server.serverSocket = test.accept();
				System.out.println("Server listening on port: "+ portNumber);
				server.serverOutput = new PrintWriter(server.serverSocket.getOutputStream(), true);
				server.serverInput = new BufferedReader(new InputStreamReader(server.serverSocket.getInputStream()));
				String input = server.serverInput.readLine();
				if (input != null) {
					//System.out.println(input);
					//communicationHandler would take input here and decide what to do with it
				}
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
		/*while ((input = server.serverInput.readLine()) != null) {
			
		}*/
	}
}
