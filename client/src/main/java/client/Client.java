package client;

import java.io.IOException;

public class Client {
	
	
	
	public static void main(String[] args) throws IOException {
		ClientGame game = new ClientGame(1, 0, "other");
		game.gameGUI.displayGame();
	}

}
