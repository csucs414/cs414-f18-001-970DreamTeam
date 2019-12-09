package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class ClientGUI {
	
	ClientGame game;
	public JFrame gameWindow;
	public JButton[][] buttonGrid;
	public JLabel playerDisplay;
	
	public ClientGUI(ClientGame game) {
		this.game = game;
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

	private JButton[] setWhiteRow() {
		JButton[] rowSquares = new JButton[11];
		Insets margin = new Insets(0, 0, 0, 0);
		for (int i = 0; i < rowSquares.length; i++) {
			rowSquares[i] = new JButton();
			rowSquares[i].setMargin(margin);
			rowSquares[i].setBackground(Color.WHITE);
			rowSquares[i].addActionListener(new MoveListener(game));
		}
		return rowSquares;
	}
	
	private JButton[][] buildBoardBackground() {
		JButton[][] boardSquares = new JButton[11][11];
		for (int i = 0; i < boardSquares.length; i++) {
			boardSquares[i] = setWhiteRow();
		}
		colorBackground(boardSquares);
		return boardSquares;
	}
	
	public void displayGame() throws IOException {
		
		// get host screen size to setup starting window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width * 2 / 3;
		int height = screenSize.height * 2 / 3;

		gameWindow = new JFrame("Hnefatafl");
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
	
	public void setPieceLocations() throws IOException {
		BufferedImage king = ImageIO.read(ClassLoader.getSystemResource("Chess_klt60.png"));
		BufferedImage whitePawn = ImageIO.read(ClassLoader.getSystemResource("Chess_plt60.png"));
		BufferedImage blackPawn = ImageIO.read(ClassLoader.getSystemResource("Chess_pdt60.png"));
		char[][] gameBoard = game.getBoard();
    	for (int i=0; i<gameBoard.length; i++) {
    		for (int j=0; j<gameBoard[i].length; j++) {
    			char piece = gameBoard[i][j];
    			Icon image = new ImageIcon();
    			if (piece == 'b') {
    				image = new ImageIcon(blackPawn);
    			} else if (piece == 'w') {
    				image = new ImageIcon(whitePawn);
    			} else if (piece == 'k') {
    				image = new ImageIcon(king);
    			}
    			
    			buttonGrid[i][j].setIcon(image);
    			buttonGrid[i][j].setFont(new Font("utf-8", Font.PLAIN, 50));
    		}
    	}
    }
	
	private JPanel initializeToolBarPanel() {
		JPanel toolBarPanel = new JPanel(new BorderLayout(3, 3));
		toolBarPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolBarPanel.add(getToolBar(), BorderLayout.PAGE_START);
		return toolBarPanel;
	}
	
	private JPanel initializeBoardPanel() throws IOException {
		JPanel boardPanel = new JPanel(new GridLayout(0, 11));
		buttonGrid = buildBoardBackground();
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		setPieceLocations();
		fillGUIBoard(boardPanel);
		return boardPanel;
	}

	private JToolBar getToolBar() {
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);

		JButton quitGameButton = new JButton("Quit");
		quitGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}});
		tools.add(quitGameButton); // TODO add functionality
		tools.addSeparator();
		JLabel turnDisplay = new JLabel("Turn: Player "+ game.players[game.getTurn()]);
		playerDisplay = turnDisplay;
		tools.add(turnDisplay);
		return tools;
	}
	
	private void fillGUIBoard(JPanel board) {
		
		for (int i = 0; i < buttonGrid.length; i++) {
			for (int j = 0; j < buttonGrid[i].length; j++) {
				board.add(buttonGrid[i][j]);
			}
		}
	}
	
	
}