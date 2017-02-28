package org.leanpoker.player;

import java.util.HashMap;
import java.util.List;

import org.leanpoker.player.JsonPlayer;
import org.leanpoker.player.PlayingCard;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

	static final String VERSION = "Default Java folding player";
	static HashMap<String, HashMap<String, JsonPlayer>> mapGameToPlayers;
	static List<PlayingCard> holeCards;
	
	static int maxBet = 0;
	static int stack = 1000;
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
		setMaxBetAndActualStack(players);
		
		
//		System.err.println("betRequest: " + request.toString());
//		System.out.println("betRequest: " + request.toString());
		
//		if (maxBet >= 50) {
//			return 50;
//		}
//		maxBet += 2;
		if (maxBet > 25) {
			return 25;
		}
		return maxBet;
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
