package com.danny.fish.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.danny.fish.model.Game;
import com.danny.fish.model.Player;
import com.danny.fish.model.PlayerTurn;
import com.danny.fish.service.GameService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200"})
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping("/get/{roomId}")
	public ResponseEntity<Object> getGames(@PathVariable String roomId) {
		ResponseEntity<Object> response = new ResponseEntity<Object>(this.gameService.getGame(roomId),
				HttpStatus.CREATED);
		return response;
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createGame(@RequestBody Game game) {
		ResponseEntity<Object> response = new ResponseEntity<Object>(this.gameService.createGame(game),
				HttpStatus.CREATED);
		return response;
	}

	@PostMapping("/start")
	public ResponseEntity<Object> startGame(@RequestBody Game game) {
		try {
			this.gameService.startGame(game.getRoomId());
			ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.OK);
			return response;
		} catch (Exception e) {
			Map<String, String> message = new HashMap<String, String>();
			message.put("eror", "All players haven't joined in yet");
			ResponseEntity<Object> response = new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@PostMapping("/addPlayer")
	public ResponseEntity<Object> addPlayer(@RequestBody Player player) {
		try {
			ResponseEntity<Object> response = new ResponseEntity<Object>(
					this.gameService.addPlayer(player.getRoomId(), player.getPlayerName()), HttpStatus.CREATED);
			return response;
		} catch (Exception e) {
			Map<String, String> message = new HashMap<String, String>();
			message.put("playerNames", "All players have already joined or the game has already started");
			ResponseEntity<Object> response = new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@PutMapping("/askCard")
	public ResponseEntity<Object> askCard(@RequestBody PlayerTurn playerTurn) {
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Ask successful");
		ResponseEntity<Object> response = new ResponseEntity<Object>(this.gameService.askCard(playerTurn),
				HttpStatus.OK);
		return response;
	}

	@PutMapping("/declareCard")
	public ResponseEntity<Object> declareSet(@RequestBody PlayerTurn playerTurn) {

		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Declare successful");
		ResponseEntity<Object> response = new ResponseEntity<Object>(this.gameService.declareSet(playerTurn),
				HttpStatus.OK);
		return response;
	}

}
