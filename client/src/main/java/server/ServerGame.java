package server;

import java.time.OffsetDateTime;

public class ServerGame {
	private char[][] gameBoard;
	private String[] players;
	private int turn;
	private OffsetDateTime startTime;
	
	/**
     * Constructor
     */
	ServerGame(char[][] gameBoard, String[] players, int turn) {
		this.gameBoard = gameBoard;
		this.players = players;
		this.turn = turn;
	}
	
	ServerGame(String[] players) {
		this.players = players;
		turn = 0;
		char[][] newBoard = { { 'e', 'e', 'e', 'b', 'b', 'b', 'b', 'b', 'e', 'e', 'e' },
				{ 'e', 'e', 'e', 'e', 'e', 'b', 'e', 'e', 'e', 'e', 'e' },
				{ 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e' },
				{ 'b', 'e', 'e', 'e', 'e', 'w', 'e', 'e', 'e', 'e', 'b' },
				{ 'b', 'e', 'e', 'e', 'w', 'w', 'w', 'e', 'e', 'e', 'b' },
				{ 'b', 'b', 'e', 'w', 'w', 'k', 'w', 'w', 'e', 'b', 'b' },
				{ 'b', 'e', 'e', 'e', 'w', 'w', 'w', 'e', 'e', 'e', 'b' },
				{ 'b', 'e', 'e', 'e', 'e', 'w', 'e', 'e', 'e', 'e', 'b' },
				{ 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e' },
				{ 'e', 'e', 'e', 'e', 'e', 'b', 'e', 'e', 'e', 'e', 'e' },
				{ 'e', 'e', 'e', 'b', 'b', 'b', 'b', 'b', 'e', 'e', 'e' } };
		gameBoard = newBoard;
	}
	
	public void updateGameState(char[][] gameBoard, int turn) {
		this.gameBoard = gameBoard;
		this.turn = turn;
	}
	
	public String[] getPlayers() {
		return this.players;
	}
	
	public char[][] getBoard() {
		return this.gameBoard;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public void setStartTime() {
		this.startTime = OffsetDateTime.now();
	}
	
}
