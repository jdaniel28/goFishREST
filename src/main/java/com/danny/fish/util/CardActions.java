package com.danny.fish.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.danny.fish.model.Game;

public class CardActions {

	public static String defaultDeck = "2D3D4D5D6D7D8D9DTDJDQDKDAD2H3H4H5H6H7H8H9HTHJHQHKHAH2C3C4C5C6C7C8C9CTCJCQCKCAC2S3S4S5S6S7S8S9STSJSQSKSAS";

	public static List<String> stringToList(String cards) {
		List<String> cardsList = new ArrayList<String>();
		if (cards != null) {
			int start = 0;
			for (int i = 0; i < cards.length(); i++) {
				if (cards.charAt(i) == ';') {
					if (start == i) {
						cardsList.add("WE");
						start = i + 1;
					} else {
						cardsList.add(cards.substring(start, i));
						start = i + 1;
					}
				}
			}
		}
		return cardsList;
	}

	public static String createSetsString(int n) {
		String str = "";
		for (int i = 0; i < n; i++) {
			str += ';';
		}
		System.out.println(str);
		return str;
	}

	public static String listToStringSets(List<String> listSets) {
		String sets = "";
		for (int i = 0; i < listSets.size(); i++) {
			if (listSets.get(i).equals("W")) {
				sets += ";";
			} else {
				sets += listSets.get(i);
				sets += ";";
			}
		}
		return sets;
	}

	public static int getNextTurn(int numPlayers, int currentTurn) {
		if (currentTurn == numPlayers) {
			return 1;
		} else {
			return currentTurn + 1;
		}
	}

	public static List<String> stringToListSets(String sets) {
		List<String> stringList = new ArrayList<String>();
		int start = 0;
		for (int i = 0; i < sets.length(); i++) {
			if (sets.charAt(i) == ';') {
				if (start == i) {
					stringList.add("W");
				} else {
					stringList.add(sets.substring(start, i));
				}
				start = i + 1;
			}
		}
		System.out.println(stringList);
		return stringList;
	}

	public static String updatedListToString(List<String> cards) {
		String cardsString = "";
		for (int i = 0; i < cards.size(); i++) {
			cardsString += cards.get(i) + ";";
		}
		return cardsString;
	}

	public static String getSetName(String abbStr) {
		String string = "";
		if (abbStr.equals("2")) {
			string = "Twos";
		} else if (abbStr.equals("3")) {
			string = "Threes";
		} else if (abbStr.equals("4")) {
			string = "Fours";
		} else if (abbStr.equals("5")) {
			string = "Fives";
		} else if (abbStr.equals("6")) {
			string = "Sixes";
		} else if (abbStr.equals("7")) {
			string = "Sevens";
		} else if (abbStr.equals("8")) {
			string = "Eights";
		} else if (abbStr.equals("9")) {
			string = "Nines";
		} else if (abbStr.equals("T")) {
			string = "Tens";
		} else if (abbStr.equals("J")) {
			string = "Jacks";
		} else if (abbStr.equals("Q")) {
			string = "Queens";
		} else if (abbStr.equals("K")) {
			string = "Kings";
		} else if (abbStr.equals("A")) {
			string = "Aces";
		}
		return string;
	}

	/*
	 * public static String[] getSet(String card) { String str[] = {}; String
	 * setName = ""; if (card.equals("2")) { setName += "Two"; } else if
	 * (card.equals("3")) { setName += "Three"; } else if (card.equals("4")) {
	 * setName += "Four"; } else if (card.equals('5')) { setName += "Five"; } else
	 * if (card.equals("6")) { setName += "Six"; } else if (card.equals("7")) {
	 * setName += "Seven"; } else if (card.equals("8")) { setName += "Eight"; } else
	 * if (card.equals("9")) { setName += "Nine"; } else if (card.equals("T")) {
	 * setName += "Ten"; } else if (card.equals("J")) { setName += "Jack"; } else if
	 * (card.equals("Q")) { setName += "Queen"; } else if (card.equals("K")) {
	 * setName += "King"; } else if (card.equals("A")) { setName += "Ace"; } return
	 * str; }
	 */

	public static List<String> getSameSetCards(String card) {
		String[] strings = { card + "C", card + "S", card + "H", card + "D" };
		List<String> listStr = Arrays.asList(strings);
		return listStr;
	}

	public static String listToString(List<String> cardsList) {
		String cards = "";
		for (Iterator<String> iterator = cardsList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			cards += string;
		}
		return cards;
	}

	public static List<String> stringToListDeck(String deckCards) {
		List<String> listCards = new ArrayList<String>();
		if (deckCards != null && deckCards.length() > 0) {
			for (int i = 0; i < deckCards.length(); i += 2) {
				listCards.add(deckCards.substring(i, i + 2));
			}
		}
		return listCards;
	}

	public static String getAlphaNumericString(int n) {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	public static Map<Integer, String[]> getSetsMap(int currentPlayers, String setsDeclared) {
		Map<Integer, String[]> setsMap = new HashMap<Integer, String[]>();
		System.out.println(setsDeclared);
		if (setsDeclared != null && setsDeclared.length() != 0) {
			for (int i = 0; i < currentPlayers; i++) {
				int start = 0;
				List<String> sets = new ArrayList<String>();
				String subString = "";
				for (int j = 0; j < setsDeclared.length(); j++) {
					if (setsDeclared.charAt(j) == ';' && j > 1) {
						subString = setsDeclared.substring(0, j);
						start = j + 1;
						break;
					}
				}
				if (subString.length() > 1) {
					setsDeclared = setsDeclared.substring(start, setsDeclared.length());
					for (int j = 0; j < subString.length(); j += 2) {
						sets.add(subString.substring(j, j + 2));
					}
					String str[] = sets.toArray(new String[sets.size()]);
					setsMap.put(i + 1, str);
				} else {
					setsMap.put(i + 1, new String[0]);
				}
			}
		}
		return setsMap;
	}

	public static String getSetsString(Map<Integer, String[]> setsMap) {
		String setsString = "";
		if (setsMap != null) {
			for (Map.Entry<Integer, String[]> entry : setsMap.entrySet()) {
				for (int i = 0; i < entry.getValue().length; i++) {
					setsString += entry.getValue()[i];
				}
				setsString += ";";
			}
		}
		return setsString;
	}

	public static Game distributeCards(List<String> cardsList, int playerNum) {
		Game game = new Game();
		List<String> playerCards = new ArrayList<String>();
		int cardMultiplier = 0;

		if (playerNum == 2 || playerNum == 3) {
			cardMultiplier = 7;
		} else {
			cardMultiplier = 5;
		}

		for (int i = 0; i < playerNum; i++) {
			String playerString = "";
			for (int j = i * cardMultiplier; j < ((i + 1) * cardMultiplier); j++) {
				playerString += cardsList.get(j);
			}
			playerString += ";";
			playerCards.add(playerString);
		}

		for (int i = 0; i < (cardMultiplier * playerNum); i++) {
			cardsList.remove(0);
		}
		game.setDeckCards(cardsList);
		game.setPlayerCards(playerCards);
		return game;
	}

}
