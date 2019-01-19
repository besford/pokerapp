package com.poker.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
	public ArrayList<Player> players;

	public Deck deck = new Deck();
	public final int handSize = 5;
	
	public int numberOfPlayers = 0;
	public int numberOfAIPlayers = 0;
	public int numberOfHumanPlayers = 0;
	
	public Game(int numPlayers) {
		reset(numPlayers);
	}
	
	public void reset(int numPlayers) {
		players = new ArrayList<Player>();
		
		numberOfPlayers = numPlayers;
		numberOfAIPlayers = 0;
		numberOfHumanPlayers = 0;
		Player.currentID = 1;
		
		for (int i = 0; i < numberOfPlayers; ++i)
			players.add(new Player());
		
		deck.newDeck(); 
		
		for (Player p: players) {
			for (int i = 0; i < handSize; i++) {
				Card card = deck.draw();
				card.hidden = true;
				p.addCard(card);
			}
		}
	}
	
	public void playerStand() {
		procTurn();
	}
	
	public void playerDiscardsCards(int[] cards) {
		Player player = getActivePlayer();
		
		if (cards != null) {
			int removed = 0;
			for (int i = 0; i < cards.length; i++) {
				player.discardCard(cards[i] - removed);
				removed += 1;
			}
			for (int i = 0; i < cards.length; i++) {
				Card card = deck.draw();
				card.hidden = false;
				player.addCard(card);	
			}
		}
		
		procTurn();
	}
	
	public void playerDiscardsCards(List<Card> cards) {
		Player player = getActivePlayer();
		
		if (cards != null) {
			if (!cards.isEmpty()) {
				for (Card c : cards) 
					player.discardCard(c);
				
				for (Card c : cards) {
					Card card = deck.draw();
					card.hidden = false;
					player.addCard(card);
				}
			}
		}
		
		procTurn();
	}
	
	public void playerEndTurn(int playerID) {
		players.get(playerID-1).hideHand();
		players.get(playerID-1).active = false;
	}
	
	public void revealHands() {
		for (Player p: players)
			p.revealHand();
	}
	
	public void procTurn() {
		playerEndTurn(getActivePlayerId());
	}
	
	public void resolveAiTurn() {
		Player p = getActivePlayer();
		if (p.isAI) {
			p.performTurn(this);
			p.active = true;
		}
	}
	
	public Player getActivePlayer() {
		Player activeP = null;
		for (Player p : players) {
			if (p.active) {
				activeP = p;
				break;
			}
		}
		
		return activeP;
	}
	
	public int getActivePlayerId() {
		int id = 0;
		for (Player p : players) {
			if (p.active) {
				id = p.getID();
				break;
			}
		}
		
		return id;
	}
	
	public String resolve() {
		StringBuilder result = new StringBuilder(); 
		
		int maxScore = 0;
		int winnerId = 0;
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			p.revealHand();
			int playerHandValue = p.getHand().getHandValue();
			if (playerHandValue > maxScore) {
				maxScore = playerHandValue;
				winnerId = players.get(i).getID();
			}
		}
		
		result.append("Player " + winnerId + " has won with hand: " + players.get(winnerId-1).getHand().toString() + "<br/>");
		
		return result.toString();
	}
	
	public String lobby() {
		StringBuilder lobby = new StringBuilder();
		
		for (Player p : players) {
			lobby.append("Player " + p.getID() +": ");
			lobby.append("<input type=\"radio\" name=\""+p.getID()+"\" value=\"Human\" checked>Human");
			lobby.append("<input type=\"radio\" name=\""+p.getID()+"\" value=\"AI\">AI");
			lobby.append("<br/>");
		}
		
		lobby.append("<h3></h3>");
		
		return lobby.toString();
	}
	
	public String aIStrategies() {
		StringBuilder lobby = new StringBuilder();
		
		for (Player p : players) {
			if (p.isAI) {
				lobby.append("AI Player " + p.getID() +": ");
				lobby.append("<input type=\"radio\" name=\""+p.getID()+"\" value=\"1\" checked>Strategy 1");
				lobby.append("<input type=\"radio\" name=\""+p.getID()+"\" value=\"2\">Strategy 2");
				lobby.append("<br/>");
			}
		}
		
		return lobby.toString();
	}
	
	public boolean playerIsActive(int playerID) {
		return players.get(playerID).active;
	}
	
	public boolean playerIsActive(Player p) {
		return p.active;
	}
	
	public void updatePlayerType(int playerID, String type) {
		Player p = players.get(playerID-1);
		if (type.equals("AI")) {
			p.isAI = true;
			numberOfAIPlayers += 1;
		} 
		else {
			p.isAI = false;
			numberOfHumanPlayers += 1;
		}
	}
	
	public String returnGameState() {
		StringBuilder state = new StringBuilder(); 
		
		resolveAiTurn();
		
		for (Player p: players) {
			if (getActivePlayerId() == p.getID()) {
				if (!p.isAI) {
					p.revealHand();
					state.append("<b>Your hand: " + p.getHand().toString() + "</b><br/>");
				}
				else {
					state.append("<b>Player " + p.getID() + "'s hand (AI): " + p.getHand().toString() + "</b><br/>");
				}
			}
			else {
				if (!p.isAI)
					state.append("Player " + p.getID() + "'s hand: " + p.getHand().toString() + "<br/>");
				else
					state.append("Player " + p.getID() + "'s hand (AI): " + p.getHand().toString() + "<br/>");
			}
		}
		
		state.append("<h1></h1>");
		
		return state.toString();
	}
	
	public String returnRevealedGameState() {
		StringBuilder state = new StringBuilder(); 
		
		for (Player p: players) {
			p.revealHand();
			if (!p.isAI)
				state.append("Player " + p.getID() + "'s hand: " + p.getHand().toString() + "<br/>");
			else
				state.append("Player " + p.getID() + "'s hand (AI): " + p.getHand().toString() + "<br/>");
		}
		
		return state.toString();
	}
	
	public String getMoveOptions() {
		StringBuilder moves = new StringBuilder();
		Player player = getActivePlayer();
		
		moves.append("<form action=\"move\" method=\"POST\">");
		
		if (!player.isAI) {
			moves.append("<h3>Moves:</h3>");
			moves.append("<input value=\"Stand\" name=\"move\" type=\"submit\">");
    	    moves.append("<input value=\"Discard\" name=\"move\" type=\"submit\">");
		} 
		else {
			moves.append("<input value=\"Confirm AI Move\" name=\"move\" type=\"submit\">");
		}
		
		moves.append("<input value=\"Rig Hands\" name=\"move\" type=\"submit\">");
		moves.append("<input value=\"Rig Next Draw\" name=\"move\" type=\"submit\">");
		moves.append("</form>");
		
		return moves.toString();
	}
	
	public String getPlayerTurnHeader() {
		StringBuilder header = new StringBuilder();
		Player player = getActivePlayer();
		
		if (!player.isAI) {
			header.append("<h1>Human Player "+player.getID()+"'s turn</h1>");
		}
		else {
			header.append("<h1>AI Player "+player.getID()+" has completed their turn</h1>");
		}
		
		return header.toString();
	}
	
	public String returnNextDrawForm() {
		StringBuilder cards = new StringBuilder();
		
		cards.append("<select name=\"Rank\">");
		for (String rank : Card.RANKS) 
			cards.append("<option value=\""+rank+"\">"+rank+"</option>");
		cards.append("</select>");
		
		cards.append("<select name=\"Suit\">");
		for (String suit : Card.SUITS) 
			cards.append("<option value=\""+suit+"\">"+suit+"</option>");
		cards.append("</select>");
		
		cards.append("<br>");
		
		cards.append("<h3></h3>");
		
		return cards.toString();
	}
	
	public String returnCardsForm() {
		StringBuilder cards = new StringBuilder();
		
		for (Player p : players) {
			cards.append("<h4>Player "+p.getID()+"</h4>");
			Hand hand = p.getHand();
			for (int i = 0; i < handSize; i++) {
				if (i % 3 == 0) 
					cards.append("<br>");
				cards.append("Card " + (i + 1) + " ");
				cards.append("<select name=\"Rank"+p.getID()+""+(i+1)+"\">");
				for (String rank : Card.RANKS) {
					if (!hand.getCard(i).getRank().equals(rank))
						cards.append("<option value=\""+rank+"\">"+rank+"</option>");
					else
						cards.append("<option value=\""+rank+"\" selected=\"selected\">"+rank+"</option>");
				}
				cards.append("</select>");
				
				cards.append("<select name=\"Suit"+p.getID()+""+(i+1)+"\">");
				for (String suit : Card.SUITS) {
					if (!hand.getCard(i).getSuit().equals(suit))
						cards.append("<option value=\""+suit+"\">"+suit+"</option>");
					else
						cards.append("<option value=\""+suit+"\" selected=\"selected\">"+suit+"</option>");
				}
				cards.append("</select>");
			}
			
			cards.append("<br>");
		}
		
		cards.append("<h3></h3>");
		
		return cards.toString();
	}
	
	public String getLeaderBoard() {
		StringBuilder result = new StringBuilder(); 
		
		ArrayList<Player> leaderBoard = (ArrayList<Player>) players.clone();
		
		Collections.sort(leaderBoard);
		
		int count = 1;
		for (Player p : leaderBoard) {
			p.revealHand();
			if (!p.isAI)
				result.append(count+" - Player " + p.getID() + "'s hand: " + p.getHand().toString() + "<br/>");
			else
				result.append(count + " - Player " + p.getID() + "'s hand (AI): " + p.getHand().toString() + "<br/>");
			
			count += 1;
		}
		
		return result.toString();
	}
	
	public void rigPlayerHands(Map<String, String> cards) {
		ArrayList<String> values = new ArrayList<String>(cards.values());
		
		
		int currentPlayer = -1;
		int currentCardInHand = 0;
		for (int i = 0; i < values.size(); i+=2) {
			if (i % 10 == 0) {
				currentPlayer += 1;
				currentCardInHand = 0;
			}
			String rank = values.get(i);
			String suit = values.get(i + 1);
			
			
			Card card = players.get(currentPlayer).getHand().getCard(currentCardInHand);
			
			card.setCardValue(rank+suit);
			
			currentCardInHand += 1;
		}
		
		for (Player p : players)
			p.hideHand();
	}
	
	public void rigPlayerDraw(Map<String, String> card) {
		ArrayList<String> values = new ArrayList<String>(card.values());
		
		String rank = values.get(0);
		String suit = values.get(1);
		
		deck.rigDraw(new Card(rank+suit, false));
	}
	
	public String returnPlayersHand() {
		StringBuilder state = new StringBuilder();
		state.append("Your hand: " + players.get(getActivePlayerId()-1).getHand().toString());
		return state.toString();
	}

	public void updateAIStrategies(int playerId, String value) {
		System.out.println(playerId + "" + value);
		Player player = players.get(playerId-1);
		if (player.isAI) {
			if (value.toLowerCase().equals("1"))
				player.setPokerStrategy(new Strategy1());
			else if(value.toLowerCase().equals("2"))
				player.setPokerStrategy(new Strategy1());
		}
	}
}
