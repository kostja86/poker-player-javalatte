package org.leanpoker.player;

public enum CardSuit {
	HEART("heart"), DIAMONDS("diamonds"), SPADES("spades"), CLUBS("clubs");
	
	private String jsonKey;
	
	private CardSuit(String jsonKey) {
		this.jsonKey = jsonKey;
	}
	
	public static CardSuit convert(String jsonKey) {
		CardSuit card = CardSuit.HEART;
		
		for(CardSuit cardSuit : CardSuit.values()) {
			if (cardSuit.jsonKey.equals(jsonKey)) {
				card = cardSuit;
				break;
			}
		}
		
		return card;
	}
}
