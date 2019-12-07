package client;

import java.io.IOException;
import java.lang.String;
import javax.swing.*;


public class ClientGame {
	private int gameID;
	private char[][] gameBoard;
	private int turn;
	private String[] players;
	public ClientGUI gameGUI;
	

	/**
	 * Constructor
	 */
	ClientGame(int gameID, int turn, String opponent) {
		this.gameID = gameID;
		this.turn = turn;

		// TODO: Add self top players list
		String[] playerArray = { "self", opponent };
		players = playerArray;
		this.gameBoard = buildBoard();
		gameGUI = new ClientGUI(this);
	}

	public char[][] buildBoard() {
		char[][] board = { { 'e', 'e', 'e', 'b', 'b', 'b', 'b', 'b', 'e', 'e', 'e' },
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

		return board;
	}
	
	public char[][] getBoard(){
		return this.gameBoard;
	}
	
	public boolean MoveValidator(int[] from, int[] to) {
		// checks if piece is current player's piece
		if (!this.validPiece(from[0], from[1])) return false;
		// check corners and throne
		///*
		if ((this.gameBoard[from[0]][from[1]] != 'k') && ((to[0] == 0 && to[1] == 0) || (to[0] == 10 && to[1] == 0)
			|| (to[0] == 0 && to[1] == 10) || (to[0] == 10 && to[1] == 10) || (to[0] == 5 && to[1] == 5))) return false;
		//*/
		// check if piece is not moving to an empty space or is an empty space
		if (this.gameBoard[to[0]][to[1]] != 'e' || this.gameBoard[from[0]][from[1]] == 'e') return false;
		
		// check if move is in same row or column
		if (!(from[0] == to[0] || from[1] == to[1])) return false;
		
		// check if piece in between from and to
		if (from[0] == to[0]) { // same row
			if (from[0] < to[0]) {
				for (int i = from[0] + 1; i <= to[0]; i++) {
					if (this.gameBoard[i][from[1]] != 'e') return false;
				}
			}
			else if (from[0] > to[0]) {
				for (int i = from[0] - 1; i >= to[0]; i--) {
					if (this.gameBoard[i][from[1]] != 'e') return false;
				}
			}
		}
		else if (from[1] == to[1]) { // same column
			if (from[1] < to[1]) {
				for (int i = from[1] + 1; i <= to[1]; i++) {
					if (this.gameBoard[from[0]][i] != 'e') return false;
				}
			}
			else if (from[1] > to[1]) {
				for (int i = from[1] - 1; i >= to[1]; i--) {
					if (this.gameBoard[from[0]][i] != 'e') return false;
				}
			}
		}
		
		return true;
	}
	
	private void movePiece(int[] from, int[] to) {
		char temp = this.gameBoard[to[0]][to[1]];
		this.gameBoard[to[0]][to[1]] = this.gameBoard[from[0]][from[1]];
		this.gameBoard[from[0]][from[1]] = temp;
	}

	public void updateGameState(int[] from, int[] to) throws IOException {

		// TODO: check move and update board.
		if (this.MoveValidator(from, to)) {
			this.movePiece(from, to);
			
			// check regular pieces
			if (to[0] > 1) { // check to make sure it wont go out of bounds
				char twoUp = this.gameBoard[to[0] - 2][to[1]];
				char oneUp = this.gameBoard[to[0] - 1][to[1]];
				char current = this.gameBoard[to[0]][to[1]];
				
				if ((oneUp != current) && (oneUp != 'e') && (oneUp != 'k')) { // checks if there is an enemy piece next to moved piece
					
					if (current == twoUp || (current == 'w' && twoUp == 'k')) { // checks if enemy piece is capturable  and it isnt a king
						this.gameBoard[to[0] - 1][to[1]] = 'e';
					}
					
					else if ((to[0] - 2 == 0 && to[1] == 0) || (to[0] - 2 == 0 && to[1] == 10)) { // checks corners
						this.gameBoard[to[0] - 1][to[1]] = 'e';
					}
					
					else if ((to[0] - 2 == 5 && to[1] == 5) && twoUp != 'k') { // checks throne
						this.gameBoard[to[0] - 1][to[1]] = 'e';
					}
				}
			}
			
			if (to[1] < 9) { // check to make sure it wont go out of bounds 
				char twoRight = this.gameBoard[to[0]][to[1] + 2];
				char oneRight = this.gameBoard[to[0]][to[1] + 1];
				char current = this.gameBoard[to[0]][to[1]];
				
				if ((oneRight != current) && (oneRight != 'e') && (oneRight != 'k')) { // checks if there is an enemy piece next to moved piece
					
					if (current == twoRight || (current == 'w' && twoRight == 'k')) { // checks if enemy piece is capturable  and it isnt a king
						this.gameBoard[to[0]][to[1] + 1] = 'e';
					}
					
					else if ((to[0] == 0 && to[1] + 2 == 10) || (to[0] == 10 && to[1] + 2 == 10)) { // checks corners
						this.gameBoard[to[0]][to[1] + 1] = 'e';
					}
					
					else if ((to[0] == 5 && to[1] + 2 == 5) && twoRight != 'k') { // checks throne
						this.gameBoard[to[0]][to[1] + 1] = 'e';
					}
				}
			}
			
			if (to[0] < 9) { // check to make sure it wont go out of bounds
				char twoDown = this.gameBoard[to[0] + 2][to[1]];
				char oneDown = this.gameBoard[to[0] + 1][to[1]];
				char current = this.gameBoard[to[0]][to[1]];
				
				if ((oneDown != current) && (oneDown != 'e') && (oneDown != 'k')) { // checks if there is an enemy piece next to moved piece
					
					if (current == twoDown || (current == 'w' && twoDown == 'k')) { // checks if enemy piece is capturable  and it isnt a king
						this.gameBoard[to[0] + 1][to[1]] = 'e';
					}
					
					else if ((to[0] + 2 == 10 && to[1] == 0) || (to[0] + 2 == 10 && to[1] == 10)) { // checks corners
						this.gameBoard[to[0] + 1][to[1]] = 'e';
					}
					
					else if ((to[0] + 2 == 5 && to[1] == 5) && twoDown != 'k') { // checks throne
						this.gameBoard[to[0] + 1][to[1]] = 'e';
					}
				}
			}
			
			if (to[1] > 1) { // check to make sure it wont go out of bounds
				char twoLeft = this.gameBoard[to[0]][to[1] - 2];
				char oneLeft = this.gameBoard[to[0]][to[1] - 1];
				char current = this.gameBoard[to[0]][to[1]];
				
				if ((oneLeft != current) && (oneLeft != 'e') && (oneLeft != 'k')) { // checks if there is an enemy piece next to moved piece
					
					if (current == twoLeft || (current == 'w' && twoLeft == 'k')) { // checks if enemy piece is capturable and it isnt a king
						this.gameBoard[to[0]][to[1] - 1] = 'e';
					}
					
					else if ((to[0] == 0 && to[1] - 2 == 0) || (to[0] == 10 && to[1] - 2 == 0)) { // checks corners
						this.gameBoard[to[0]][to[1] - 1] = 'e';
					}
					
					else if ((to[0] == 5 && to[1] - 2 == 5) && twoLeft != 'k') { // checks throne
						this.gameBoard[to[0]][to[1] - 1] = 'e';
					}
				}
			}
			
			gameGUI.setPieceLocations();
			
			// check win condition
			if (this.checkWinCondition()) {
			    JOptionPane.showMessageDialog(gameGUI.gameWindow, "Player "+Integer.toString(turn+1)+" Wins!");
				return;
			}
			
			// Switch player
			if (this.turn == 0) {
				this.turn = 1;
				gameGUI.playerDisplay.setText("Turn: Player "+ Integer.toString(turn+1));
			} else
				this.turn = 0;
				gameGUI.playerDisplay.setText("Turn: Player "+ Integer.toString(turn+1));
		}
	}
	
	private boolean validPiece(int row, int column) {
        char piece = gameBoard[row][column];
	    if (this.turn == 0 && piece == 'b') {
	        return true;
        }
	    if (this.turn == 1 && (piece == 'k' || piece == 'w')){
	        return true;
        }
	    return false;
    }
	
	//TODO: ADD JUNIT TEST FOR THIS METHOD
	private boolean checkWinCondition() {
		//check if king is in a corner
		if(this.gameBoard[0][0] == 'k' || this.gameBoard[0][10] == 'k' || this.gameBoard[10][10] == 'k'||this.gameBoard[10][0] == 'k') {
			return true;
		}
		//check if king is captured by 4 pieces when king is not at an edge/against a wall
		int[] kingLocation = this.findKingLocation();
		int y = kingLocation[0];
		int x = kingLocation[1];
		
		if((y != 10 && y != 0) && (x != 10 && x != 0)) {
			if(this.gameBoard[y+1][x] == 'b' && this.gameBoard[y-1][x] == 'b' && this.gameBoard[y][x+1] == 'b' && this.gameBoard[y][x-1] == 'b') {
				return true;
			}
		}
		//check if enemy has the enough pieces to capture king
		if(this.countBlackPieces()<4) {
			return true;
		}
		
		return false;
	}
	//find the location of the king
	private int[] findKingLocation() {
		int[] location = new int[2];
		//iterates through board looking for king location
		for(int y = 0; y < this.gameBoard.length;y++) {
			for(int x = 0; x < this.gameBoard[y].length; x++) {
				if(this.gameBoard[y][x]=='k') {
					location[0]= y;
					location[1] = x;
					return location;
				}
			}
		}
		return location;
	}
	//returns how many black pieces are left 
	public int countBlackPieces() {
		int numOfPieces = 0;
		
		for(int y = 0; y < this.gameBoard.length;y++) {
			for(int x = 0; x < this.gameBoard[y].length; x++) {
				if(this.gameBoard[y][x]=='b') {
					numOfPieces += 1;
				}
			}
		}
		
		return numOfPieces;
	}
	
	public int getTurn() {
		return turn;
	}
	
}