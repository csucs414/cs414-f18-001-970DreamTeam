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
	
	}
	
}
