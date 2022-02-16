package com.danny.fish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.danny.fish.model.Game;
import com.danny.fish.util.CardActions;

public class GameMapper implements RowMapper<Game> {

	@Override
	public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
		Game game = new Game();
		game.setDeckCards(CardActions.stringToListDeck(rs.getString("deckCards")));
		game.setPlayerCards(CardActions.stringToList(rs.getString("playerCards")));
		game.setNumPlayers(rs.getInt("numPlayers"));
		game.setPlayerNames(rs.getString("playerNames"));
		game.setPlayerTurn(rs.getInt("playerTurn"));
		game.setRoomId(rs.getString("roomId"));
		game.setLobbyFull(rs.getBoolean("lobbyFull"));
		game.setCurrentPlayers(rs.getInt("currentPlayers"));
		game.setSetsDeclared(CardActions.stringToListSets(rs.getString("setsDeclared")));
		game.setNumSetsDeclared(rs.getInt("numSetsDeclared"));
		game.setLog(rs.getString("log"));
		return game;
	}

}
