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
			int sourceCol = (currentlySelected.getX()-2)/128;
			int sourceRow = (currentlySelected.getY()-5)/78;
			int[] sourceCoordinates = {sourceRow, sourceCol};
			
			int destinationCol = (destination.getX()-2)/128;
			int destinationRow = (destination.getY()-5)/78;
			int[] destCoordinates = {destinationRow, destinationCol};
			
			game.updateGameState(sourceCoordinates, destCoordinates);
			currentlySelected = null;
		}
		
		
	}

}
