package com.poker.webapp;

import java.util.Arrays;

public class Card implements Comparable {

		public static final String[] RANKS = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", 
		                       "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
		
		public static final String[] SUITS = {"Spades", "Clubs", "Hearts", "Diamonds"};
		
		private String rank;
		private String suit; 
		private String cardString;
		private int rankValue;
		private int suitValue;
		public boolean hidden;
		public boolean revealed;
		
		public Card(String cardString, boolean hidden) {
			this.cardString = cardString;
			this.hidden = hidden;
			this.revealed = false;
			
			String[] splitString = cardString.split("(?<=.)(?=\\p{Lu})");

			if (splitString.length != 2) 
				throw new IncorrectInputException();
			
			String r = splitString[0];
			String s = splitString[1];
			
			if (!Arrays.asList(RANKS).contains(r)) 
				throw new IncorrectInputException();
			else if (!Arrays.asList(SUITS).contains(s)) 
				throw new IncorrectInputException();
			else {
				rank = r;
				suit = s;
				
				if (rank.equals("Ace"))
					rankValue = 14;
				else if (rank.equals("King"))
					rankValue = 13;
				else if (rank.equals("Queen"))
					rankValue = 12;
				else if (rank.equals("Jack"))
					rankValue = 11;
				else {
					for (int i = 0; i < RANKS.length; ++i) {						
						if (RANKS[i].equals(rank)) {
							rankValue = i+2; 
							break;
						}
					}
				}
				
				if (suit.equals("Spades"))
					suitValue = 4;
				else if (suit.equals("Clubs"))
					suitValue = 2;
				else if (suit.equals("Hearts"))
					suitValue = 3;
				else if (suit.equals("Diamonds"))
					suitValue = 1;
			}
		}
		
		public void setCardValue(String cardString) {
			String[] splitString = cardString.split("(?<=.)(?=\\p{Lu})");

			if (splitString.length != 2) 
				throw new IncorrectInputException();
			
			String r = splitString[0];
			String s = splitString[1];
			
			if (!Arrays.asList(RANKS).contains(r)) 
				throw new IncorrectInputException();
			else if (!Arrays.asList(SUITS).contains(s)) 
				throw new IncorrectInputException();
			else {
				rank = r;
				suit = s;
				
				if (rank.equals("Ace"))
					rankValue = 14;
				else if (rank.equals("King"))
					rankValue = 13;
				else if (rank.equals("Queen"))
					rankValue = 12;
				else if (rank.equals("Jack"))
					rankValue = 11;
				else {
					for (int i = 0; i < RANKS.length; ++i) {						
						if (RANKS[i].equals(rank)) {
							rankValue = i+2; 
							break;
						}
					}
				}
				
				if (suit.equals("Spades"))
					suitValue = 4;
				else if (suit.equals("Clubs"))
					suitValue = 2;
				else if (suit.equals("Hearts"))
					suitValue = 3;
				else if (suit.equals("Diamonds"))
					suitValue = 1;
			}
		}

		public String getRank() {
			return rank;
		}
		
		public String getSuit() {
			return suit;
		}

		public int getRankValue() {
			return rankValue;
		}
		
		public int getSuitValue() {
			return suitValue;
		}
		
		public String getCardString() {
			return cardString;
		}
		
		public String toString() {
			if (!hidden || revealed)
				return rank + " of " + suit; 
			return "Hidden"; 
		}

		@Override
		public int compareTo(Object o) {
			int otherRankValue = ((Card) o).getRankValue();
			return this.rankValue - otherRankValue;
		}
		
		
}
