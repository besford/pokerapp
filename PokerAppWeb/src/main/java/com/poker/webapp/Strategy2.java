package com.poker.webapp;

import java.util.ArrayList;
import java.util.List;

public class Strategy2 implements PokerStrategy {

	/**
	 * If first player to go, use strategy 1.
	 * 
	 * Elif any player before you has 3 visible cards of same kind keep
	 * pairs and discard all other cards
	 * 
	 * Else use strategy 1 
	 */
	@Override
	public void move(Hand hand, Game game) {
		Player ai = game.getActivePlayer();
		
		if (ai.getID() == 0)
			tryToGetStraightOrBetter(hand, game);
		else if (otherHasVisibleThreeOfKind(game)) {
			List<Card> discarded = new ArrayList<Card>();
			for (int i = 0; i < hand.size()-2; i++) {
				if (!areTwoOfKind(hand.getCard(i), hand.getCard(2)))
					discarded.add(hand.getCard(i));
			}
			
			game.playerDiscardsCards(discarded);
		} 
		else
			game.playerStand();
	}
	
	private boolean otherHasVisibleThreeOfKind(Game game) {
		Player self = game.getActivePlayer();
		for (Player p : game.players) {
			if (p.getID() != self.getID() && p.getID() < self.getID()) {
				List<Card> visible = p.getHand().getVisibleCards();
				if (visible.size() >= 3) {
					for (int i = 0; i < visible.size()-3; i++)
						if (areThreeOfKind(visible.get(i), visible.get(i+1), visible.get(i+2)))
							return true;
				}
			}
		}
		
		return false;
	}
	
	private void tryToGetStraightOrBetter(Hand hand, Game game) {	
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
