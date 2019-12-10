# Hnefatafl [![Build Status](https://travis-ci.com/csucs414/cs414-f18-001-970DreamTeam.svg?branch=master)](https://travis-ci.com/csucs414/cs414-f18-001-970DreamTeam)
## 970 Dream Team
![image1](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/hnefatafl_board.jpg)

# Rules

## Setup:
* Two players. Attackers (black pieces) and Defenders (white pieces).
* 11x11 board. Twice as many attackers as defenders.
* Attacker moves first.
* Defenders have King piece.

## Objectives:
* Attackers: Capture the King.
* Defenders: King escapes.
* King is captured by getting surrounded by Attack on 4 sides or 3 sides and a restricted space.
* The Kind escapes by reaching 1 of the 4 corner squares. 
* Regular pieces captured by getting surrounded on opposite sides by 2 pieces or 1 and a restricted space. (sandwiched)
* The King can participate in the capture if its is the moving piece.
* A player may only capture an enemy piece if they surround it with a move on their turn.

## Moving:
* Each piece (including King) can move vertical or horizontal. Similar to a Rook in chess.
* 1 piece may move per turn.
* Only king may stop on the Throne (center square).
* A piece can only move through empty spaces.
* A piece may pass inbetween enemy pieces and not get captured.
* The throne and 4 corners are considered restricted spaces.
* The throne is always hostile to attackers and can be used to capture their pieces.
* The throne is only hostile to defenders if it is vacated by the King.
* The corners are hostile to both players.
* If a player cannot move they lose.
* If neither player has enough pieces to capture the enemy, the game is a draw.

---

![b1](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/b1.jpeg) ![b2](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/b2.jpeg) ![b3](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/b3.jpeg)

^King is captured.

---

![b4](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/b4.jpeg) ![b5](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/b5.jpeg)

^King is not captured.

---


# How To Run
Step 1. Clone Respository.

Step 2. Compile and package files to generate .jar files.

    In project directory type: mvn clean install
    Or, in Eclipse, Rightclick on project folder and Select "Run As" then select "Maven Build".
    
    This will generate files in the server/target and client/target folders.
    
Step 3. Execute jar files.

    Navigate to server/target directory and run server with command: java -jar server-0.0.1-SNAPSHOT.jar
    Server will run on port 20001. Code must be changed in Server.java to change port number.
    
    After the server is running, navigate to the client/target folder and run the client with command: java -jar client-0.0.1-SNAPSHOT.jar
    If the server port was changed the client port in Client.java must be changed to match.
