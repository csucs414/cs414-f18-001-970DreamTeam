package server;

import java.util.ArrayList;
import java.util.HashMap;


public class Server {

	private HashMap<Integer, ServerGame> games = new HashMap<>();
	private ArrayList<String> onlinePlayers = new ArrayList<>();
	private static int nextGameID = 0;
	
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
}
