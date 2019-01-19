package com.poker.webapp;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	private final String[] RANKS = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", 
            "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

	private final String[] SUITS = {"Spades", "Clubs", "Hearts", "Diamonds"};
	
	private ArrayList<Card> deck;
	
	public Deck() {
		deck = new ArrayList<Card>();
	}
	
	public void newDeck() {
		deck.clear();
		for (String rank: RANKS) {
			for (String suit: SUITS) {
				deck.add(new Card(rank + suit, false));
			}
		}
		shuffle(); 
	}
	
	public void shuffle() {
	    int n = deck.size();
	    Random random = new Random();
	    random.nextInt();
	    
	    for (int i = 0; i < n; i++) {
	    	int change = i + random.nextInt(n - i);
	    	Card helper = deck.get(i);
	    	deck.set(i, deck.get(change));
	    	deck.set(change, helper);
	    }
	}
	
	public void rigDraw(Card c) {
		deck.add(0, c);
	}
	
	public Card draw() {
		return deck.remove(0);
	}
	
	public boolean isEmpty() { return deck.size() == 0; }
}
