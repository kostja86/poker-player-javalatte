package org.leanpoker.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerTest {

    @Test
    public void testBetRequest() throws Exception {

//        JsonElement jsonElement = new JsonParser().parse("{\"key1\": \"value1\", \"key2\": \"value2\"}");
        JsonElement jsonElement = new JsonParser().parse("{\"tournament_id\":\"58b538ab8835920004000021\",\"game_id\":\"58b5548480f8780004000081\",\"round\":0,\"players\":[{\"name\":\"PokerFarce\",\"stack\":1000,\"status\":\"folded\",\"bet\":0,\"time_used\":8985,\"version\":\"Default TypeScript folding player\",\"id\":0},{\"name\":\"JavaLatte\",\"stack\":998,\"status\":\"active\",\"bet\":2,\"hole_cards\":[{\"rank\":\"8\",\"suit\":\"spades\"},{\"rank\":\"7\",\"suit\":\"clubs\"}],\"time_used\":0,\"version\":\"Default Java folding player\",\"id\":1},{\"name\":\"Java Master Race Maybe\",\"stack\":996,\"status\":\"active\",\"bet\":4,\"time_used\":0,\"version\":\"Default Java folding player\",\"id\":2},{\"name\":\"STG\",\"stack\":1000,\"status\":\"folded\",\"bet\":0,\"time_used\":6937,\"version\":\"Default C# folding player\",\"id\":3},{\"name\":\"still mono noobs\",\"stack\":0,\"status\":\"out\",\"bet\":0,\"time_used\":17117,\"version\":\"Default C# folding player\",\"id\":4}],\"small_blind\":2,\"big_blind\":4,\"orbits\":0,\"dealer\":0,\"community_cards\":[],\"current_buy_in\":4,\"pot\":6,\"in_action\":1,\"minimum_raise\":2,\"bet_index\":5}");

		System.out.println("System.out");
		System.err.println("System.err");
//		JsonArray jsonArray;
//		JsonObject obj = request.getAsJsonObject();
//		JsonArray results = obj.getAsJsonArray("players");
//		for (JsonObject result : results.) {
//			
//		}
		System.out.println(jsonElement.isJsonObject());
		JsonObject jObj = jsonElement.getAsJsonObject();
		System.out.println("players: " + jObj.get("players"));
		System.out.println("players is Array?: " + jObj.get("players").isJsonArray());
		JsonArray players = jObj.get("players").getAsJsonArray();
		for(int i = 0; i < players.size(); i++) {
			System.out.println(i + ": " + players.get(i));
			JsonElement plEl = players.get(i);
			System.out.println(plEl.isJsonObject());
			System.out.println(plEl.getAsJsonObject().get("bet"));
			int actualBet = plEl.getAsJsonObject().get("bet").getAsInt();
		}
//		if (jsonElement.isJsonArray()) {
//			System.err.println("isJsonArray");
//			jsonArray = jsonElement.getAsJsonArray();
//			for (int i = 0; i < jsonArray.size(); i++) {
//				System.out.println("betRequest: " + i + ": " + jsonArray.get(i).toString());
//				System.err.println("betRequest: " + i + ": " + jsonArray.get(i).toString());
//				JsonElement el = jsonArray.get(i);
//			}
//		}
        
		assertEquals(200, 1);
//        assertEquals(200, Player.betRequest(jsonElement));

    }
}
