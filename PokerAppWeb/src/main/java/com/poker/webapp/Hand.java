package com.poker.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Hand {
	
	private List<Card> cards;
	private int value; 
	public int numberOfAces;
	
	public Hand() {
		cards = new ArrayList<Card>();
		value = 0;
	}
	
	public void addCard(Card card) {
		if (size() == 0)
			card.hidden = true;
		cards.add(card);
		Collections.sort(cards);
	}
	
	public Card removeCard(Card card) {
		if (cards.remove(card))
			return card;
		else
			return null;
	}
	
	public Card removeCard(int id) {
		return cards.remove(id);
	}
	
	public void reveal() {
		for (Card c: cards)
			c.revealed = true;
	}
	
	public void hide() {
		for (Card c: cards)
			c.revealed = false;
	}
	
	public boolean isRFlush() {
		return isSFlush() && getHighCard().getRankValue() == 14;
	}
	
	public boolean isSFlush() {
		return isStraight() && isFlush();
	}
	
	public boolean isFourOfKind() {
		return ((cards.get(1).getRankValue() == cards.get(2).getRankValue() && cards.get(2).getRankValue() == cards.get(3).getRankValue())
				&& (cards.get(0).getRankValue() == cards.get(1).getRankValue() || cards.get(4).getRankValue() == cards.get(1).getRankValue()));
	}
	
	public boolean isFullHouse() {
		return (((cards.get(0).getRankValue() == cards.get(1).getRankValue()
					&& cards.get(1).getRankValue() == cards.get(2).getRankValue())
					&& cards.get(2).getRankValue() != cards.get(3).getRankValue()
					&& cards.get(3).getRankValue() == cards.get(4).getRankValue())||
				(cards.get(0).getRankValue() == cards.get(1).getRankValue()
					&& cards.get(1).getRankValue() != cards.get(2).getRankValue()
					&& cards.get(2).getRankValue() == cards.get(3).getRankValue()
					&& cards.get(3).getRankValue() == cards.get(4).getRankValue()));
	}
	
	public boolean isFlush() {
		return (cards.get(0).getSuitValue() == cards.get(1).getSuitValue() 
				&& cards.get(1).getSuitValue() == cards.get(2).getSuitValue()
				&& cards.get(2).getSuitValue() == cards.get(3).getSuitValue()
				&& cards.get(3).getSuitValue() == cards.get(4).getSuitValue());
	}
	
	public boolean isStraight() {
		return ((cards.get(0).getRankValue() == cards.get(1).getRankValue()-1) 
					&& cards.get(0).getRankValue() == cards.get(2).getRankValue()-2
					&& cards.get(0).getRankValue() == cards.get(3).getRankValue()-3
					&& ((cards.get(0).getRankValue() == cards.get(4).getRankValue()-4) || 
						(cards.get(4).getRank().equals("Ace") && cards.get(0).getRankValue() == 2)));
	}
	
	public boolean isThreeOfKind() {
		return ((cards.get(0).getRankValue() == cards.get(2).getRankValue() 
					&& cards.get(0).getRankValue() != cards.get(3).getRankValue()
					&& cards.get(3).getRankValue() != cards.get(4).getRankValue()) ||
				(cards.get(1).getRankValue() == cards.get(3).getRankValue()
					&& cards.get(0).getRankValue() != cards.get(1).getRankValue()
					&& cards.get(0).getRankValue() != cards.get(4).getRankValue()) ||
				(cards.get(2).getRankValue() == cards.get(4).getRankValue()
					&& cards.get(1).getRankValue() != cards.get(2).getRankValue()
					&& cards.get(1).getRankValue() != cards.get(0).getRankValue()));
					
 	}
	
	public boolean isTwoPair() {
		return ((cards.get(0).getRankValue() == cards.get(1).getRankValue() 
					&& cards.get(2).getRankValue() == cards.get(3).getRankValue()
					&& cards.get(0).getRankValue() != cards.get(2).getRankValue()) ||
				(cards.get(0).getRankValue() == cards.get(2).getRankValue() 
					&& cards.get(3).getRankValue() == cards.get(4).getRankValue()
					&& cards.get(1).getRankValue() != cards.get(3).getRankValue()));
	}
	
	public boolean isOnePair() {
		for (int i = 0; i < 4; i++) {
			if (cards.get(i).getRankValue() == cards.get(i+1).getRankValue())
				return true;
		}
		
		return false;
	}
	
	public Card getHighCard() {
		int high = 0;
		Card highCard = null;
		
		for (Card c : cards) {
			if (c.getRankValue() > high) {
				high = c.getRankValue();
				highCard = c;
			}
		}
		
		return highCard;
	}
	
	public List<Card> getVisibleCards() {
		List<Card> visible = new ArrayList<Card>();
		
		for (Card c : cards) 
			if (!c.hidden) 
				visible.add(c);
		
		return visible;
	}
	
	public int getHandValue() {
		sort();
		
		int result = 0;
		
		if (this.isRFlush())
			result += 9000;
		else if(this.isSFlush())
			result += 8000;
		else if(this.isFourOfKind())
			result += 7000;
		else if(this.isFullHouse())
			result += 6000;
		else if(this.isFlush())
			result += 5000;
		else if(this.isStraight())
			result += 4000;
		else if(this.isThreeOfKind())
			result += 3000;
		else if(this.isTwoPair())
			result += 2000;
		else if(this.isOnePair())
			result += 1000;
		
		sortForRanking();
		
		result += (cards.get(0).getRankValue() + (cards.get(0).getSuitValue() * 100));
		
		System.out.println(result);
		
		return result;
	}
	
	public int getVisibleHandValue() {
		return (getHandValue() - cards.get(0).getRankValue());
	}
	
	public int size() { return cards.size(); }
	public Card getCard(int n) { return cards.get(n); }
	
	public String toString() {
		String s = "";
		for (Card c: cards)
			s += c.toString() + ", ";
		return s;
	}
	
	private void sort() {
		Collections.sort(cards);
	}
	
	private void sortReverse() {
		Collections.sort(cards, Collections.reverseOrder());
	}
	
	private void sortForRanking() {
		sortReverse();
		if (isFourOfKind())
			sortFourOfKind();
		else if (isFullHouse())
			sortFullHouse();
		else if (isThreeOfKind())
			sortThreeOfKind();
		else if (isTwoPair())
			sortTwoPair();
		else if (isOnePair())
			sortOnePair();
	}

	private void sortOnePair() {
		if (cards.get(size()-4).getRankValue() == cards.get(size()-3).getRankValue()) 
			swapCards(0, size()-3);
		else if (cards.get(size()-3).getRankValue() == cards.get(size()-2).getRankValue()) {
			swapCards(0, size()-3);
			swapCards(size()-4, size()-2);
		}
		else if (cards.get(size()-2).getRankValue() == cards.get(size()-1).getRankValue()) {
			sort();
			swapCards(size()-3, size()-1);
		}
	}

	private void sortTwoPair() {
		if (cards.get(0).getRankValue() != cards.get(size()-4).getRankValue()) {
			for (int i = 0; i < size()-1; i++)
				swapCards(i, i + 1);
		} 
		else if (cards.get(0).getRankValue() == cards.get(size()-4).getRankValue()
			&& cards.get(size()-2).getRankValue() == cards.get(size()-1).getRankValue()) {
			swapCards(size()-3, size()-1);
		}
	}

	private void sortThreeOfKind() {
		if (cards.get(0).getRankValue() != cards.get(size()-3).getRankValue()
				&& cards.get(size()-1).getRankValue() != cards.get(size()-3).getRankValue()) {
			swapCards(0, size()-2);
		}
		else if (cards.get(0).getRankValue() != cards.get(size()-3).getRankValue()
				&& cards.get(size()-4).getRankValue() != cards.get(size()-3).getRankValue()) {
			sort();
			swapCards(size()-1, size()-2);
		}
	}

	private void sortFullHouse() {
		if (cards.get(0).getRankValue() != cards.get(size()-3).getRankValue()) {
			swapCards(0, size()-2);
			swapCards(size()-4, size()-1);
		}
	}

	private void sortFourOfKind() {
		if (cards.get(0).getRankValue() != cards.get(size()-4).getRankValue())
			swapCards(0, size()-1);
	}

	private void swapCards(int i, int j) {
		Collections.swap(cards, i, j);
	}
}
