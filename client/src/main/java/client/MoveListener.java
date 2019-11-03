package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class MoveListener implements ActionListener {
	
	private static JButton currentlySelected;
	ClientGame game;
	
	public MoveListener(ClientGame game) {
		this.game = game;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (currentlySelected == null) {
			currentlySelected = (JButton) arg0.getSource();
			
		} else {
			JButton destination = (JButton) arg0.getSource();
			int sourceCol=-1;
			int sourceRow=-1;
			int destinationCol=-1;
			int destinationRow=-1;
			
			
			for (int i=0; i < game.buttonGrid.length; i++) {
				for (int j=0; j < game.buttonGrid[i].length; j++) {
					if (currentlySelected == game.buttonGrid[i][j]) {
						sourceRow = i;
						sourceCol = j;
					}
					if (destination == game.buttonGrid[i][j]) {
						destinationRow = i;
						destinationCol = j;
					}
				}
			}
			
			int[] sourceCoordinates = {sourceRow, sourceCol};
			int[] destCoordinates = {destinationRow, destinationCol};
			
			game.updateGameState(sourceCoordinates, destCoordinates);
			currentlySelected = null;
		}
		
		
	}

}
