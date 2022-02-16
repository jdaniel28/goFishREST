package com.danny.fish.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.danny.fish.mapper.GameMapper;
import com.danny.fish.model.Game;
import com.danny.fish.util.CardActions;

@Repository
public class GameDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String ADD_GAME = "insert into games (roomId, numPlayers,playerNames,currentPlayers,lobbyFull,numSetsDeclared,deckCards,playerCards,playerTurn,setsDeclared) values (?,?, ?,?,?,?,?,?,?,?);";
	private final String GET_GAMES = "select * from games where roomId = ?;";
	private final String UPDATE_GAME = "update games set playerNames = ?, playerCards = ?,deckCards =?,playerTurn = ?,currentPlayers=?,lobbyFull=?,numSetsDeclared=?,setsDeclared=?,log=? where roomId = ?;";
	private final String DELETE_GAME = "delete from games where roomId = ?;";

	public void createGame(Game game) {
		this.jdbcTemplate.update(ADD_GAME, game.getRoomId(), game.getNumPlayers(), game.getPlayerNames(), 1, false, 0,
				CardActions.listToString(game.getDeckCards()), CardActions.listToString(game.getPlayerCards()),
				game.getPlayerTurn(),CardActions.listToStringSets(game.getSetsDeclared()));
	}

	public void updateGame(Game game) {
		this.jdbcTemplate.update(UPDATE_GAME, game.getPlayerNames(),
				CardActions.updatedListToString(game.getPlayerCards()), CardActions.listToString(game.getDeckCards()),
				game.getPlayerTurn(), game.getCurrentPlayers(), game.isLobbyFull(), game.getNumSetsDeclared(),
				CardActions.listToStringSets(game.getSetsDeclared()), game.getLog(), game.getRoomId());
	}

	public Game getGame(String roomId) {
		return this.jdbcTemplate.queryForObject(GET_GAMES, new GameMapper(), new Object[] { roomId });
	}

	public void deleteGame(String roomId) {
		this.jdbcTemplate.update(DELETE_GAME, roomId);
	}
}
