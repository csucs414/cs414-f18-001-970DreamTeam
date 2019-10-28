package client;

import java.lang.String;

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
        //players[0] = self;
        players[1] = opponent;
        this.gameBoard = buildBoard();
    }

    private void update_game() {

        // Switch player
        if(this.turn == 0){
            this.turn = 1;
        }else
            this.turn = 0;

        // TODO: check move and update board.

    }

    private void display_game() {

    }

    private char[][] buildBoard() {
        char[][] board = {
                {'c','e','e','b','b','b','b','b','e','e','c'},
                {'e','e','e','e','e','b','e','e','e','e','e'},
                {'e','e','e','e','e','e','e','e','e','e','e'},
                {'b','e','e','e','e','w','e','e','e','e','b'},
                {'b','e','e','e','w','w','w','e','e','e','b'},
                {'b','b','e','w','w','k','w','w','e','b','b'},
                {'b','e','e','e','w','w','w','e','e','e','b'},
                {'b','e','e','e','e','w','e','e','e','e','b'},
                {'e','e','e','e','e','e','e','e','e','e','e'},
                {'e','e','e','e','e','b','e','e','e','e','e'},
                {'c','e','e','b','b','b','b','b','e','e','c'}
        };

        return board;
    }

}