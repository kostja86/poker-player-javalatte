package org.leanpoker.player;

public enum PlayingCard {
	TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8",
			8), NINE("9", 9), TEN("10", 10), JACK("J", 11), QUEEN("Q", 12), KING("K", 13), ASS("A", 15);

	private String jsonKey;
	private int cardRankValue;

	private PlayingCard(String jsonKey, int cardRankValue) {
		this.jsonKey = jsonKey;
		this.cardRankValue = cardRankValue;
	}

	public String getJsonKey() {
		return jsonKey;
	}

	public int getCardRankValue() {
		return cardRankValue;
	}
	
	public static PlayingCard convert(String jsonKey) {
		PlayingCard card = PlayingCard.ASS;
		for (PlayingCard playingCard : PlayingCard.values()) {
			if(playingCard.getJsonKey().equals(jsonKey)) {
				card = playingCard;
				break;
			}
		}
		return card;
	}
}
