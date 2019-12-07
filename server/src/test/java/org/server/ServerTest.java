package org.server;

import static org.junit.jupiter.api.Assertions.*;

import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ServerTest {
	
	@Test
	public void createNewGameTest() {
		Server server = new Server();
		int[] players = {1231, 3435};
		Socket[] socks = {};
		server.createNewGame(players, socks);
		assertNotNull(server.getGame(0));
	}

	@Test
	public void deleteGameTest() {
		Server server = new Server();
		int[] players = {1231, 3435};
		Socket[] socks = {};
		server.createNewGame(players, socks);
		server.deleteGame(0);
		assertNull(server.getGame(0));
	}
	
	@Test
	public void addPlayerTest() {
		Server server = new Server();
		server.addPlayer("TestPlayer");
		ArrayList<String> players = server.getOnlinePlayers();
		assertEquals(1, players.size());
		assertEquals("TestPlayer", players.get(0));
	}
	
	@Test
	public void removePlayerTest() {
		Server server = new Server();
		server.addPlayer("TestPlayer");
		server.addPlayer("OtherPlayer");
		server.removePlayer("TestPlayer");
		ArrayList<String> players = server.getOnlinePlayers();
		assertTrue(players.size()==1);
	}
}
