package com.danny.fish.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danny.fish.dao.GameDao;
import com.danny.fish.model.Game;
import com.danny.fish.model.PlayerTurn;
import com.danny.fish.util.CardActions;

@Service
public class GameService {

	@Autowired
	private GameDao gameDao;

	public Game declareSet(PlayerTurn playerTurn) {
		Game game = this.gameDao.getGame(playerTurn.getRoomId());
		if (game.getPlayerTurn() == playerTurn.getPlayerAsked()) {
			String askedSet = playerTurn.getSet();
			List<String> allCardsAskedSet = CardActions.getSameSetCards(askedSet);
			List<String> playerCards = game.getPlayerCards();

			String currentPlayerCards = playerCards.get(game.getPlayerTurn() - 1);
			List<String> listCurrentPlayerCards = CardActions.stringToListDeck(currentPlayerCards);
			boolean hasSet = true;
			for (String card : allCardsAskedSet) {
				if (!listCurrentPlayerCards.contains(card)) {
					hasSet = false;
					break;
				}
			}
			if (hasSet) {
				for (String card : allCardsAskedSet) {
					listCurrentPlayerCards.remove(card);
				}
				currentPlayerCards = CardActions.listToString(listCurrentPlayerCards);
				playerCards.set(game.getPlayerTurn() - 1, currentPlayerCards);
				game.setNumSetsDeclared(game.getNumSetsDeclared() + 1);

				List<String> setsList = game.getSetsDeclared();

				String playerSetsString = setsList.get(game.getPlayerTurn() - 1);
				if (playerSetsString.equals("W")) {
					playerSetsString = playerTurn.getSet();
				} else {
					playerSetsString += playerTurn.getSet();
				}
				setsList.set(game.getPlayerTurn() - 1, playerSetsString);
				game.setSetsDeclared(setsList);
				game.setPlayerCards(playerCards);
				if (listCurrentPlayerCards.size() == 0 || listCurrentPlayerCards == null) {
					boolean emptyCards = true;
					int nextPlayerNum = game.getPlayerTurn();
					while (emptyCards) {
						nextPlayerNum = CardActions.getNextTurn(game.getNumPlayers(), nextPlayerNum);
						String nextPlayerCards = playerCards.get(nextPlayerNum - 1);
						List<String> listNextPlayerCards = CardActions.stringToListDeck(nextPlayerCards);
						if (listNextPlayerCards.size() == 1 && listNextPlayerCards.get(0).equals("WE")) {
							continue;
						} else {
							emptyCards = false;
						}
					}
					game.setPlayerTurn(nextPlayerNum);
				}

				this.gameDao.updateGame(game);
			}

		}
		return game;

	}

	public Game askCard(PlayerTurn playerTurn) {
		boolean hasSet = false;
		Game game = this.gameDao.getGame(playerTurn.getRoomId());

		String askedSet = playerTurn.getSet();

		List<String> allCardsAskedSet = CardActions.getSameSetCards(askedSet);
		List<String> playerCards = game.getPlayerCards();
		String askedPlayerCards = playerCards.get(playerTurn.getPlayerAsked() - 1);
		List<String> listAskedPlayerCards = CardActions.stringToListDeck(askedPlayerCards);
		for (String card : listAskedPlayerCards) {
			if (allCardsAskedSet.contains(card)) {
				hasSet = true;
			}
		}

		String playerNames = game.getPlayerNames();
		List<String> listPlayerNames = CardActions.stringToList(playerNames);
		String playerName = listPlayerNames.get(game.getPlayerTurn() - 1);
		String askedPlayerName = listPlayerNames.get(playerTurn.getPlayerAsked() - 1);

		if (hasSet) {
			List<String> containingCards = new ArrayList<String>();
			for (String card : allCardsAskedSet) {
				if (listAskedPlayerCards.contains(card)) {
					containingCards.add(card);
				}
			}
			String currentPlayerCards = playerCards.get(game.getPlayerTurn() - 1);
			List<String> listCurrentPlayerCards = CardActions.stringToListDeck(currentPlayerCards);
			for (String card : containingCards) {
				listAskedPlayerCards.remove(card);
				listCurrentPlayerCards.add(card);
			}
			String logString = playerName + " took ";
			for (int k = 0; k < containingCards.size(); k++) {
				logString += CardActions.getCardName(containingCards.get(k));
				if (k == containingCards.size() - 1) {
					logString += " ";
				} else {
					logString += ", ";
				}
			}
			logString += "from " + askedPlayerName;
			// + containingCards.toString() + " from " + askedPlayerName;
			game.setLog(logString);
			currentPlayerCards = CardActions.listToString(listCurrentPlayerCards);
			askedPlayerCards = CardActions.listToString(listAskedPlayerCards);
			playerCards.set(playerTurn.getPlayerAsked() - 1, askedPlayerCards);
			playerCards.set(game.getPlayerTurn() - 1, currentPlayerCards);
			game.setPlayerCards(playerCards);
			this.gameDao.updateGame(game);

		} else {
			List<String> listDeckCards = game.getDeckCards();
			if (listDeckCards != null && listDeckCards.size() > 0) {
				String logString = playerName + " asked " + askedPlayerName + " for " + CardActions.getSetName(askedSet)
						+ " but went fishing instead 🐟";
				game.setLog(logString);
				String cardDrawn = listDeckCards.get(0);
				listDeckCards.remove(0);
				String currentPlayerCards = playerCards.get(game.getPlayerTurn() - 1);
				List<String> listCurrentPlayerCards = CardActions.stringToListDeck(currentPlayerCards);
				listCurrentPlayerCards.add(cardDrawn);
				currentPlayerCards = CardActions.listToString(listCurrentPlayerCards);
				playerCards.set(game.getPlayerTurn() - 1, currentPlayerCards);
				game.setPlayerCards(playerCards);

				int nextPlayerNum = game.getPlayerTurn();
				int askedPlayerNum = playerTurn.getPlayerAsked();
				nextPlayerNum = askedPlayerNum;

				game.setPlayerTurn(nextPlayerNum);
				this.gameDao.updateGame(game);
			}
		}

		return game;
	}

	public Game createGame(Game game) {
		String roomId = CardActions.getAlphaNumericString(10);
		game.setRoomId(roomId);
		String startingDeck = CardActions.defaultDeck;
		List<String> startingList = CardActions.stringToListDeck(startingDeck);
		Collections.shuffle(startingList);
		Game modGame = new Game();
		modGame = CardActions.distributeCards(startingList, game.getNumPlayers());
		game.setDeckCards(modGame.getDeckCards());
		game.setPlayerCards(modGame.getPlayerCards());
		game.setPlayerTurn(1);
		game.setLobbyFull(true);
		String setsString = CardActions.createSetsString(game.getNumPlayers());
		List<String> setsList = CardActions.stringToListSets(setsString);
		game.setSetsDeclared(setsList);
		this.gameDao.createGame(game);
		return this.gameDao.getGame(game.getRoomId());
	}

	public Game addPlayer(String roomId, String playerName) throws Exception {
		Game game = this.gameDao.getGame(roomId);
		if (game.getCurrentPlayers() < game.getNumPlayers()) {
			String playerNames = game.getPlayerNames();
			playerNames += playerName;
			playerNames += ";";
			int currentPlayers = game.getCurrentPlayers();
			++currentPlayers;
			if (currentPlayers == game.getNumPlayers()) {
				game.setLobbyFull(true);
			}
			game.setCurrentPlayers(currentPlayers);
			game.setPlayerNames(playerNames);
			this.gameDao.updateGame(game);
		} else {
			throw new Exception();
		}
		return game;
	}

	public void startGame(String roomId) throws Exception {
		Game game = this.gameDao.getGame(roomId);
		if (game.getCurrentPlayers() == game.getNumPlayers() && game.getPlayerCards().size() == 0) {
			String startingDeck = CardActions.defaultDeck;
			List<String> startingList = CardActions.stringToListDeck(startingDeck);
			Collections.shuffle(startingList);
			Game modGame = new Game();
			modGame = CardActions.distributeCards(startingList, game.getNumPlayers());
			game.setDeckCards(modGame.getDeckCards());
			game.setPlayerCards(modGame.getPlayerCards());
			game.setPlayerTurn(1);
			game.setLobbyFull(true);
			this.gameDao.updateGame(game);
		} else {
			throw new Exception();
		}
	}

	public Game getGame(String roomId) {
		return this.gameDao.getGame(roomId);
	}

}
