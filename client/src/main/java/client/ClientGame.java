package client;

import java.awt.*;

import java.lang.String;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class ClientGame {
	private int gameID;
	private char[][] gameBoard;
	private int turn;
	private String[] players;

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
	}

	private char[][] buildBoard() {
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

	private void updateGameState() {

		// Switch player
		if (this.turn == 0) {
			this.turn = 1;
		} else
			this.turn = 0;

		// TODO: check move and update board.

	}

	private void displayGame() {

		// get host screen size to setup starting window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width * 2 / 3;
		int height = screenSize.height * 2 / 3;

		JFrame gameWindow = new JFrame("Hnefatafl");
		JPanel toolBarPanel = initializeToolBarPanel();
		JPanel boardPanel = initializeBoardPanel();

		gameWindow.setPreferredSize(new Dimension(width, height));

		toolBarPanel.add(boardPanel);
		gameWindow.add(toolBarPanel);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.pack();
		gameWindow.setMinimumSize(gameWindow.getSize());
		gameWindow.setLocationByPlatform(true);
		gameWindow.setVisible(true);
	}

	private void setPieceLocations(JButton[][] boardSquares) {
    	for (int i=0; i<gameBoard.length; i++) {
    		for (int j=0; j<gameBoard[i].length; j++) {
    			char piece = gameBoard[i][j];
    			String image = "";
    			if (piece == 'b') {
    				//black pawn
    				image = "\u265F";
    			} else if (piece == 'w') {
    				//white pawn
    				image = "\u2659";
    			} else if (piece == 'k') {
    				//white king
    				image = "\u2654";
    			}
    			
    			boardSquares[i][j].setText(image);
    			boardSquares[i][j].setFont(new Font("utf-8", Font.PLAIN, 50));
    		}
    	}
    }

	private JPanel initializeToolBarPanel() {
		JPanel toolBarPanel = new JPanel(new BorderLayout(3, 3));
		toolBarPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolBarPanel.add(getToolBar(), BorderLayout.PAGE_START);
		return toolBarPanel;
	}

	private JPanel initializeBoardPanel() {
		JPanel boardPanel = new JPanel(new GridLayout(0, 11));
		JButton[][] boardSquares = buildBoardBackground();
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		setPieceLocations(boardSquares);
		fillGUIBoard(boardPanel, boardSquares);
		return boardPanel;
	}

	private JToolBar getToolBar() {
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		tools.add(new JButton("New Game")); // TODO add functionality
		tools.add(new JButton("Quit")); // TODO add functionality
		return tools;
	}

	private void fillGUIBoard(JPanel board, JButton[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				board.add(squares[i][j]);
			}
		}
	}

	private JButton[][] buildBoardBackground() {
		JButton[][] boardSquares = new JButton[11][11];
		for (int i = 0; i < boardSquares.length; i++) {
			boardSquares[i] = setWhiteRow();
		}
		colorBackground(boardSquares);
		return boardSquares;
	}

	private JButton[] setWhiteRow() {
		JButton[] rowSquares = new JButton[11];
		Insets margin = new Insets(0, 0, 0, 0);
		for (int i = 0; i < rowSquares.length; i++) {
			rowSquares[i] = new JButton();
			rowSquares[i].setMargin(margin);
			rowSquares[i].setBackground(Color.WHITE);
		}
		return rowSquares;
	}

	private void colorBackground(JButton[][] baseBackground) {
		baseBackground[0][0].setBackground(Color.MAGENTA);
		baseBackground[0][3].setBackground(Color.DARK_GRAY);
		baseBackground[0][4].setBackground(Color.DARK_GRAY);
		baseBackground[0][5].setBackground(Color.DARK_GRAY);
		baseBackground[0][6].setBackground(Color.DARK_GRAY);
		baseBackground[0][7].setBackground(Color.DARK_GRAY);
		baseBackground[0][10].setBackground(Color.MAGENTA);

		baseBackground[1][5].setBackground(Color.DARK_GRAY);

		baseBackground[3][0].setBackground(Color.DARK_GRAY);
		baseBackground[3][5].setBackground(Color.DARK_GRAY);
		baseBackground[3][10].setBackground(Color.DARK_GRAY);

		baseBackground[4][0].setBackground(Color.DARK_GRAY);
		baseBackground[4][4].setBackground(Color.DARK_GRAY);
		baseBackground[4][5].setBackground(Color.DARK_GRAY);
		baseBackground[4][6].setBackground(Color.DARK_GRAY);
		baseBackground[4][10].setBackground(Color.DARK_GRAY);

		baseBackground[5][0].setBackground(Color.DARK_GRAY);
		baseBackground[5][1].setBackground(Color.DARK_GRAY);
		baseBackground[5][3].setBackground(Color.DARK_GRAY);
		baseBackground[5][4].setBackground(Color.DARK_GRAY);
		baseBackground[5][5].setBackground(Color.MAGENTA);
		baseBackground[5][6].setBackground(Color.DARK_GRAY);
		baseBackground[5][7].setBackground(Color.DARK_GRAY);
		baseBackground[5][9].setBackground(Color.DARK_GRAY);
		baseBackground[5][10].setBackground(Color.DARK_GRAY);

		baseBackground[6][0].setBackground(Color.DARK_GRAY);
		baseBackground[6][4].setBackground(Color.DARK_GRAY);
		baseBackground[6][5].setBackground(Color.DARK_GRAY);
		baseBackground[6][6].setBackground(Color.DARK_GRAY);
		baseBackground[6][10].setBackground(Color.DARK_GRAY);

		baseBackground[7][0].setBackground(Color.DARK_GRAY);
		baseBackground[7][5].setBackground(Color.DARK_GRAY);
		baseBackground[7][10].setBackground(Color.DARK_GRAY);

		baseBackground[9][5].setBackground(Color.DARK_GRAY);

		baseBackground[10][0].setBackground(Color.MAGENTA);
		baseBackground[10][3].setBackground(Color.DARK_GRAY);
		baseBackground[10][4].setBackground(Color.DARK_GRAY);
		baseBackground[10][5].setBackground(Color.DARK_GRAY);
		baseBackground[10][6].setBackground(Color.DARK_GRAY);
		baseBackground[10][7].setBackground(Color.DARK_GRAY);
		baseBackground[10][10].setBackground(Color.MAGENTA);
	}
	
	private boolean checkWinCondition() {
		//check if king is in a corner
		if(this.gameBoard[0][0] == 'k' || this.gameBoard[0][10] == 'k' || this.gameBoard[0][10] == 'k'||this.gameBoard[10][0] == 'k') {
			return true;
		}
		//check if king is captured by 4 pieces when king is not at an edge/against a wall
		
		return false;
	}

	public static void main(String[] args) {
		ClientGame game = new ClientGame(1, 0, "other");
		game.displayGame();
	}
}