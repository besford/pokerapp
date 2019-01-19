package com.poker.webapp;

public class Strategy1 implements PokerStrategy {
	/**
	 * If AI player has straight or better, do not exchange cards
	 * 
	 * Else attempt to get full house by exchanging everything that is not 
	 * a pair or three of a kind 
	 */
	@Override
	public void move(Hand hand, Game game) {
		if (hand.isStraight() || hand.isFlush() 
				|| hand.isFullHouse() || hand.isFourOfKind()
				|| hand.isSFlush() || hand.isRFlush()) {
			game.playerStand();
		}
		else {
			Card c1 = hand.getCard(0);
			Card c2 = hand.getCard(1);
			Card c3 = hand.getCard(2);
			Card c4 = hand.getCard(3);
			Card c5 = hand.getCard(4);
			
			if (areThreeOfKind(c1, c2, c3) && !areTwoOfKind(c4, c5)) {
				int[] discard = {3, 4};
				game.playerDiscardsCards(discard);
			}
			else if (areThreeOfKind(c2, c3, c4)) {
				int[] discard = {0, 3};
				game.playerDiscardsCards(discard);
			}
			else if (areThreeOfKind(c3, c4, c5) && !areTwoOfKind(c1, c2)) {
				int[] discard = {0, 1};
				game.playerDiscardsCards(discard);
			}
			else if (areTwoOfKind(c1, c2) && !areThreeOfKind(c3, c4, c5)) {
				int[] discard = {2, 3, 4};
				game.playerDiscardsCards(discard);
			}
			else if (areTwoOfKind(c4, c5) && !areThreeOfKind(c1, c2, c3)) {
				int [] discard = {0, 1, 2};
				game.playerDiscardsCards(discard);
			}
			else if (areTwoOfKind(c2, c3)) {
				int[] discard = {0, 3, 4};
				game.playerDiscardsCards(discard);
			} else if (areTwoOfKind(c3, c4)) {
				int[] discard = {0, 1, 4};
				game.playerDiscardsCards(discard);
			} else {
				int[] discard = {0, 1, 2, 3, 4};
				game.playerDiscardsCards(discard);
			}
		}
	}

	private boolean areThreeOfKind(Card c1, Card c2, Card c3) {
		return (c1.getRankValue() == c2.getRankValue()
				&& c2.getRankValue() == c3.getRankValue());
	}
	
	private boolean areTwoOfKind(Card c1, Card c2) {
		return (c1.getRankValue() == c2.getRankValue());
	}
}
