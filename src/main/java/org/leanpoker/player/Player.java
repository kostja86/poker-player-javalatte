package org.leanpoker.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.leanpoker.player.JsonPlayer;
import org.leanpoker.player.PlayingCard;
import org.leanpoker.player.JsonCard;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

	static final String VERSION = "Default Java folding player";

	static List<JsonCard> holeCards;
	static List<JsonCard> communityCards;
	
	static int maxBet = 0;
	static int stack = 1000;
	static  int numberOfMatches = 0;
	static int numberOfEqualSuits = 0;
	static final String teamName = "JavaLatte";

	/**
	 * betRequest:
	 * {"tournament_id":"58b538ab8835920004000021","game_id":"58b54b8d80f878000400003e","round":41,
	 * "players":
	 * [{"name":"PokerFarce","stack":782,"status":"folded","bet":0,"time_used":687524,"version":"Default
	 * TypeScript foldingplayer","id":0},
	 * {"name":"JavaLatte","stack":1405,"status":"active","bet":15,"hole_cards":[{"rank":"8","suit":"hearts"},{"rank":"4","suit":"hearts"}],"time_used":546141,"version":"Default
	 * Java folding player","id":1},{"name":"Java Master Race
	 * Maybe","stack":0,"status":"out","bet":0,"time_used":20057,"version":"Default
	 * Java folding
	 * player","id":2},{"name":"STG","stack":768,"status":"active","bet":30,"time_used":673885,"version":"Default
	 * C# folding player","id":3},{"name":"still mono
	 * noobs","stack":0,"status":"out","bet":0,"time_used":18933,"version":"Default
	 * C# folding
	 * player","id":4}],"small_blind":15,"big_blind":30,"orbits":8,"dealer":0,"community_cards":[],"current_buy_in":30,"pot":45,"in_action":1,"minimum_raise":15,"bet_index":5}
	 * 
	 * @param request
	 * @return
	 */
	public static int betRequest(JsonElement request) {
		JsonObject jObj = request.getAsJsonObject();
		JsonArray players = jObj.get("players").getAsJsonArray();
		maxBet = 0;
		stack = 0;
		holeCards = new ArrayList<>();
		setMaxBetAndActualStack(players);
		setHoleCards(players);
		communityCards = new ArrayList<>();
		
		setCommunityCards(jObj.get("community_cards").getAsJsonArray());
		
		int numberOfCards = holeCards.size() + communityCards.size();
		boolean flopPlayed = numberOfCards > 2;
		boolean turnPlayed = numberOfCards > 5;
		boolean riverPlayed = numberOfCards > 6;
		
		
		int bet = 0;
		
		boolean hasMatch = false;
		
		if (hasPair() || hasHighCard() || (hasAtLeastOnePair() && hasPair()) || (numberOfEqualSuits > 5)) {
			hasMatch = true;
			bet = maxBet;
			if (numberOfMatches > 2) {
				bet += getMaxAvailableRaise();
			}
			if (numberOfEqualSuits > 5) {
				bet += getMaxAvailableRaise();
			}
		}
		
		if (!hasMatch && !flopPlayed) {
			bet = getCallBet(0.1);
		}
		
		return bet;
	}
	
	private static int getMaxAvailableRaise() {
		int availableRaise = stack - maxBet;
		if (availableRaise < 0) {
			return 0;
		}
		return availableRaise;
	}

	private static int getCallBet(double factor) {
		int bet = (int) (stack * 0.1);
		if (stack * factor >= maxBet) {
			bet = maxBet;
		}
		return bet;
	}

	private static boolean hasPair() {
		return holeCards.get(0).equals(holeCards.get(1));
	}
	
	private static boolean hasAtLeastOnePair() {
		numberOfMatches = 0;
		numberOfEqualSuits = 0;
		List<JsonCard> allCards = new ArrayList<>();
		allCards.addAll(holeCards);
		allCards.addAll(communityCards);
		
		for (int i = 0; i < holeCards.size(); i++) {
			for (int j = 0; j < communityCards.size(); j++) {
				if (holeCards.get(i).equals(communityCards.get(j))) {
					numberOfMatches++;
				}

				if (holeCards.get(i).getCardSuit().equals(communityCards.get(j).getCardSuit())) {
					numberOfEqualSuits++;
				}
			}
		}
		
		return numberOfMatches >= 2;
	}
	
	private static boolean hasHighCard() {
		for (JsonCard holeCard : holeCards) {
			if (holeCard.getPlayingCard().equals(PlayingCard.ASS)) {
				return true;
			}
			if (holeCard.getPlayingCard().equals(PlayingCard.KING)) {
				return true;
			}
			if (holeCard.getPlayingCard().equals(PlayingCard.QUEEN)) {
				return true;
			}
			if (holeCard.getPlayingCard().equals(PlayingCard.JACK)) {
				return true;
			}
		}
		return false;
	}

	
	private static void setMaxBetAndActualStack(JsonArray players) {
		for(int i = 0; i < players.size(); i++) {
			JsonElement plEl = players.get(i);
			int actualBet = plEl.getAsJsonObject().get("bet").getAsInt();
			if (teamName.equals(plEl.getAsJsonObject().get("name").getAsString())) {
				stack = plEl.getAsJsonObject().get("stack").getAsInt();
			}
			if (actualBet > maxBet) {
				maxBet = actualBet;
			}
		}
	}

	private static void setHoleCards(JsonArray players) {
		for(int i = 0; i < players.size(); i++) {
			JsonElement plEl = players.get(i);
			if (teamName.equals(plEl.getAsJsonObject().get("name").getAsString())) {
				JsonArray jsonHoleCards = plEl.getAsJsonObject().get("hole_cards").getAsJsonArray();
				// "hole_cards":[{"rank":"8","suit":"hearts"},{"rank":"4","suit":"hearts"}]
				for (int j = 0; j < jsonHoleCards.size(); j++) {
					JsonElement hlEl = jsonHoleCards.get(j);
					String rank = hlEl.getAsJsonObject().get("rank").getAsString();
					String suit = hlEl.getAsJsonObject().get("suit").getAsString();
					System.out.println("hlEl " + i + ": " + hlEl);
					holeCards.add(new JsonCard(rank, suit));
				}
				
			}
		}
	}
	
	// "community_cards":[{"rank":"6","suit":"clubs"},{"rank":"3","suit":"spades"},{"rank":"K","suit":"clubs"},{"rank":"9","suit":"clubs"},{"rank":"9","suit":"hearts"}]
	private static void setCommunityCards(JsonArray jsonCommunityCards) {
		System.out.println("jsonCommunityCards.size(): " + jsonCommunityCards.size());
		for(int i = 0; i < jsonCommunityCards.size(); i++) {
			JsonElement cCardEl = jsonCommunityCards.get(i);
			String rank = cCardEl.getAsJsonObject().get("rank").getAsString();
			String suit = cCardEl.getAsJsonObject().get("suit").getAsString();
			System.out.println("cCardEl " + i + ": " + cCardEl);
			communityCards.add(new JsonCard(rank, suit));
		}
	}

	/**
	 * showdown:
	 * {"tournament_id":"58b538ab8835920004000021","game_id":"58b54bb580f878000400003f","round":21,
	 * "players":[{"name":"PokerFarce","stack":936,"status":"folded","bet":0,"time_used":349441,"version":"Default
	 * TypeScript folding
	 * player","id":0},{"name":"JavaLatte","stack":0,"status":"out","bet":0,"hole_cards":[],"time_used":87233,"version":"Default
	 * Java folding player","id":1},{"name":"Java Master Race
	 * Maybe","stack":2116,"status":"active","bet":0,"time_used":1334783,"version":"Default
	 * Java folding
	 * player","amount_won":15,"id":2},{"name":"STG","stack":948,"status":"folded","bet":0,"time_used":533350,"version":"Default
	 * C# folding player","id":3},{"name":"still mono
	 * noobs","stack":0,"status":"out","bet":0,"time_used":31780,"version":"Default
	 * C# folding
	 * player","id":4}],
	 * "small_blind":5,"big_blind":10,"orbits":4,"dealer":3,"community_cards":[{"rank":"6","suit":"clubs"},{"rank":"3","suit":"spades"},{"rank":"K","suit":"clubs"},{"rank":"9","suit":"clubs"},{"rank":"9","suit":"hearts"}],"current_buy_in":0,"pot":0}
	 * 
	 * @param game
	 */
	public static void showdown(JsonElement game) {

		System.err.println("showdown: " + game.toString());
		System.out.println("showdown: " + game.toString());
	}
}
