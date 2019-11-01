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
	
	public void updateGameState(char[][] gameBoard, int turn) {
		
	}
	
	public String[] getPlayers() {
		return new String[0];
	}
	
	public char[][] getBoard() {
		return new char[0][0];
	}
	
	public int getTurn() {
		return 0;
	}
	
	public void setStartTime() {
	
	}
	
}
