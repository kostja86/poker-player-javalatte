package org.leanpoker.player;

public class JsonCard {

	private PlayingCard playingCard;
	private CardSuit cardSuit;
	
	public JsonCard(String rank, String suit) {
		setPlayingCard(PlayingCard.convert(rank));
		setCardSuit(CardSuit.convert(suit));
	}

	public PlayingCard getPlayingCard() {
		return playingCard;
	}

	public void setPlayingCard(PlayingCard playingCard) {
		this.playingCard = playingCard;
	}

	public CardSuit getCardSuit() {
		return cardSuit;
	}

	public void setCardSuit(CardSuit cardSuit) {
		this.cardSuit = cardSuit;
	}
	
	@Override
	public boolean equals(Object playingCard) {
		if (this.playingCard.equals(((JsonCard) playingCard).getPlayingCard())) {
			return true;
		}
		return false;
	}
	
}
