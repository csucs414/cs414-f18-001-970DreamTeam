package client;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

class ClientGameTest {
	@Test
	public void testExample() {
		assertTrue(true);
	}
	//TESTS FOR BOARD BUILD
	@Test 
	public void testBoardBuildCorners() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS CORNERS
		assertEquals(board[0][0], 'e');
		assertEquals(board[0][10], 'e');
		assertEquals(board[10][0], 'e');
		assertEquals(board[10][10], 'e');
	}
	@Test 
	public void testBoardBuildKing() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS KING
		assertEquals(board[5][5], 'k');
		
	}
	

}
