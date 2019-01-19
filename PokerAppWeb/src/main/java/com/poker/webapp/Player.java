package com.poker.webapp;

public class Player implements Comparable<Player>{
	public static int currentID = 1;
	private int id; 
	private Hand hand;
	private PokerStrategy strategy;
	public int rank;
	
	public boolean isAI;
	public boolean active;
	public boolean hasWon;
	public boolean hasLost;
	public boolean potentialBlackjack;
	
	public Player() {
		id = currentID;
		currentID++; 
		
		if (id != 1)
			isAI = true;
		else
			isAI = false;
		
		hand = new Hand(); 
		
		rank = 0;
		active = true;
		hasWon = false;
		hasLost = false;
		potentialBlackjack = false;
	}
	
	public void addCard(Card card) {
		hand.addCard(card);
		if (hand.size() == 2 && (card.getRankValue() == 10 || card.getRank() == "Ace"))
			potentialBlackjack = true;
		if (hand.size() > 2)
			potentialBlackjack = false;
	}
	
	public Card discardCard(int id) {
		Card discard = hand.getCard(id);
		hand.removeCard(discard);
		return discard;
	}
	
	public Card discardCard(Card card) {
		Card discard = hand.removeCard(card);
		return discard;
	}
	
	public void revealHand() {
		hand.reveal(); 
	}
	
	public void hideHand() {
		hand.hide();
	}
		
	public int getID() {
		return id;
	}

	public Hand getHand() {
		return hand;
	}
	
	public void setPokerStrategy(PokerStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void performTurn(Game game) {
		strategy.move(hand, game);
	}

	@Override
	public int compareTo(Player p) {
		return p.getHand().getHandValue() - hand.getHandValue();
	}
}
