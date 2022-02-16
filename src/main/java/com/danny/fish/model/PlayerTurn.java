package com.danny.fish.model;

public class PlayerTurn {
	private String set;
	private int playerAsked;
	private String roomId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public int getPlayerAsked() {
		return playerAsked;
	}

	public void setPlayerAsked(int playerAsked) {
		this.playerAsked = playerAsked;
	}

}
