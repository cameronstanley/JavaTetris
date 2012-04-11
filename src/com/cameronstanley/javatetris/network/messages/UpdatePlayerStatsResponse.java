package com.cameronstanley.javatetris.network.messages;

public class UpdatePlayerStatsResponse {

	private int level;
	private int totalLinesCleared;
	private long score;
	
	public UpdatePlayerStatsResponse() {
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTotalLinesCleared() {
		return totalLinesCleared;
	}

	public void setTotalLinesCleared(int totalLinesCleared) {
		this.totalLinesCleared = totalLinesCleared;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
}
