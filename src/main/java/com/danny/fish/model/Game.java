package com.danny.fish.model;

import java.util.List;

public class Game {
	private String roomId;
	private int numPlayers;
	private String playerNames;
	private List<String> playerCards;
	private List<String> deckCards;
	private int playerTurn;
	private int currentPlayers;
	private boolean lobbyFull;
	private int numSetsDeclared;
	private List<String> setsDeclared;
	private String log;

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public List<String> getSetsDeclared() {
		return setsDeclared;
	}

	public void setSetsDeclared(List<String> setsDeclared) {
		this.setsDeclared = setsDeclared;
	}

	public int getNumSetsDeclared() {
		return numSetsDeclared;
	}

	public void setNumSetsDeclared(int numSetsDeclared) {
		this.numSetsDeclared = numSetsDeclared;
	}

	public int getCurrentPlayers() {
		return currentPlayers;
	}

	public void setCurrentPlayers(int currentPlayers) {
		this.currentPlayers = currentPlayers;
	}

	public boolean isLobbyFull() {
		return lobbyFull;
	}

	public void setLobbyFull(boolean lobbyFull) {
		this.lobbyFull = lobbyFull;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public String getPlayerNames() {
		return playerNames;
	}

	public void setPlayerNames(String playerNames) {
		this.playerNames = playerNames;
	}

	public List<String> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(List<String> playerCards) {
		this.playerCards = playerCards;
	}

	public List<String> getDeckCards() {
		return deckCards;
	}

	public void setDeckCards(List<String> deckCards) {
		this.deckCards = deckCards;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	@Override
	public String toString() {
		return "Game [roomId=" + roomId + ", numPlayers=" + numPlayers + ", playerNames=" + playerNames
				+ ", playerCards=" + playerCards + ", deckCards=" + deckCards + ", playerTurn=" + playerTurn
				+ ", currentPlayers=" + currentPlayers + ", lobbyFull=" + lobbyFull + ", numSetsDeclared="
				+ numSetsDeclared + ", setsDeclared=" + setsDeclared + "]";
	}

}
