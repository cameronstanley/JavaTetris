package com.cameronstanley.javatetris.client.model;

public class OnlineMultiplayerGame {

	private Player player;
	private Player opponent;
	private boolean isWaiting;
	private boolean isPlayerAlive;
	private long timeElapsed;
	
	private boolean playerActiveTetrominoUpdated;
	private boolean playerBoardUpdated;
	private boolean playerStatisticsUpdated;
	
	public static final int BOARDWIDTH = 10;
	public static final int BOARDHEIGHT = 20;
	public static final int TETROMINOQUEUESIZE = 5;
	public static final int TETROMINOSTARTROTATION = 0;
	public static final int TETROMINOSTARTX = (BOARDWIDTH / 2) - 2;
	public static final int TETROMINOSTARTY = -1;
	
	public OnlineMultiplayerGame() {
		player = new Player();
		opponent = new Player();
		
		player.setBoard(new Board(BOARDHEIGHT, BOARDWIDTH));		
		player.setTetrominoQueue(new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY));
		
		opponent.setBoard(new Board(BOARDHEIGHT, BOARDWIDTH));
		opponent.setTetrominoQueue(new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY));
		
		isWaiting = true;
		isPlayerAlive = true;
		timeElapsed = 0;
		
		newGame();
	}
	
	public void newGame() {
		player.getBoard().clear();
		player.getTetrominoQueue().clear();
		player.getTetrominoQueue().fill();
		player.setActiveTetromino(player.getTetrominoQueue().nextTetromino());
		player.setScore(0);
		player.setTotalLinesCleared(0);
		player.setLevel(1);
		
		opponent.getBoard().clear();
		opponent.getTetrominoQueue().clear();
		opponent.getTetrominoQueue().fill();
		opponent.setActiveTetromino(opponent.getTetrominoQueue().nextTetromino());
		
		isPlayerAlive = true;
		timeElapsed = 0;
	}
	
	public void updateState(long delta) {
		if (isWaiting) {
			return;
		}
				
		// The level speed determines how many milliseconds must elapse before
		// the game's state is updated. The time varies from 500 to 100 milliseconds,
		// getting smaller as the level increases. The speed changes by 20 
		// millisecond intervals until level 20, where it remains at 100 for
		// any further level increases.
		int levelSpeed;		
		if(player.getLevel() > 20) {
			levelSpeed = 100;
		} else {
			levelSpeed = 500 - ((player.getLevel() - 1) * 20); 
		}
		
		// Only update the game's state if the time elapsed is greater than the
		// level speed
		timeElapsed += delta;
		if(timeElapsed < levelSpeed) {
			return;
		} else {
			timeElapsed = 0;
		}
		
		// Try to move active tetromino down; place on board if can't drop further
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(), 
				player.getActiveTetromino().getRotation(), 
				player.getActiveTetromino().getXPosition(), 
				player.getActiveTetromino().getYPosition() + 1);
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			
			// Move the piece down
			player.setActiveTetromino(movedTetromino);
			
		} else {
			
			// Check that the game is not over; a piece being placed on the
			// top row means the game is over
			if(player.getActiveTetromino().getYPosition() <= 0) {
				isPlayerAlive = false;
			} else {
				
				// Place the tetromino on the board and set the active
				// tetromino to the next tetromino in the queue
				player.getBoard().placeTetromino(player.getActiveTetromino());
				player.setActiveTetromino(player.getTetrominoQueue().nextTetromino());
				
				// Clear any full rows on the board
				int linesCleared = player.getBoard().clearFullLines();
				player.setTotalLinesCleared(player.getTotalLinesCleared() + linesCleared);
				
				// Update player score depending on how many lines were cleared
				switch(linesCleared) {
				case 1:
					player.setScore(player.getScore() + (40 * player.getLevel()));
					break;
				case 2:
					player.setScore(player.getScore() + (100 * player.getLevel()));
					break;
				case 3:
					player.setScore(player.getScore() + (300 * player.getLevel()));
					break;
				case 4:
					player.setScore(player.getScore() + (1200 * player.getLevel()));
					break;
				default:
					break;
				}
				
				// Update level; a maximum level of 99 is enforced
				if (player.getLevel() > 99) {
					player.setLevel(99);
				} else {
					player.setLevel((player.getTotalLinesCleared() / 10) + 1);
				}
				
			}
			
		}		
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getOpponent() {
		return opponent;
	}
	
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public boolean isWaiting() {
		return isWaiting;
	}

	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}
	
	public boolean isPlayerAlive() {
		return isPlayerAlive;
	}

	public void setPlayerAlive(boolean isPlayerAlive) {
		this.isPlayerAlive = isPlayerAlive;
	}

	public boolean isPlayerActiveTetrominoUpdated() {
		return playerActiveTetrominoUpdated;
	}

	public void setPlayerActiveTetrominoUpdated(boolean playerActiveTetrominoUpdated) {
		this.playerActiveTetrominoUpdated = playerActiveTetrominoUpdated;
	}

	public boolean isPlayerBoardUpdated() {
		return playerBoardUpdated;
	}

	public void setPlayerBoardUpdated(boolean playerBoardUpdated) {
		this.playerBoardUpdated = playerBoardUpdated;
	}

	public boolean isPlayerStatisticsUpdated() {
		return playerStatisticsUpdated;
	}

	public void setPlayerStatisticsUpdated(boolean playerStatisticsUpdated) {
		this.playerStatisticsUpdated = playerStatisticsUpdated;
	}
	
}