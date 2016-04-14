/*
Player
 - String name
 - List<Card> deck
 - List<Card> inHand
 - List<Card> inBoard
 - Mana mana

Card
 - String name
 - Rare rare
 - Integer life
 - Integer attack
 - Integer cost
 - Boolean provocation
 - Element element

Enum Rare {NORMAL, GOOD, RARE, INSANE}
 - String label
 - String description
 
Enum Element {FIRE, WATER, EARTH, AIR}
 - String label
 - String slogan

main
 - List<Player> players
 - List<Card> allCards
 */

package com.tard.hom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player 
{
	private String name;
	private List<Card> deck;
	private List<Card> inHand;
	private List<Card> inBoard;
	private Mana mana;
	private int pLife;
	
	public Player(String name)
	{
		this.name = name;
		this.deck = new ArrayList<Card>();
		this.inHand = new ArrayList<Card>();
		this.inBoard = new ArrayList<Card>();
		this.mana = new Mana();
		this.pLife = 30;
	}
	
	public void afficherDeck()
	{
		System.out.println("Debut Deck Joueur "+name);
		if (this.deck != null)
		{
			for (Card c:this.deck)
			{
				System.out.println(c.getName());
			}
		}
		System.out.println("Fin Deck Joueur "+name);
	}
	
	public void afficherHand()
	{
		System.out.println("Debut Main Joueur "+name);
		if (this.inHand != null)
		{
			for (Card c:this.inHand)
			{
				System.out.println(c.getName());
			}
		}
		System.out.println("Fin Main Joueur "+name);
	}
	
	public void afficherBoard()
	{
		System.out.println("Debut Board Joueur "+name);
		if (this.inBoard != null)
		{
			for (Card c:this.inBoard)
			{
				System.out.println(c.getName());
			}
		}
		System.out.println("Fin Board Joueur "+name);
	}
	
	public Card addCardInHand(Card card, Boolean autoriserEnDouble, Rare rare)
	{
		if (this.inHand != null && autoriserEnDouble == false)
		{
			for (Card c:this.inHand)
			{
				if (c.getName().equals(card.getName()))
				{
					return null;
				}
			}
		}
		if (rare != null)
		{
			if (rare != card.getRare()) return null;
		}
		this.inHand.add(card);
		return card;
	}
	
	public Card getCardFromHandByName(String name, Boolean take)
	{
		if (this.inHand != null)
		{
			for (Card c:this.inHand)
			{
				if (c.getName().equals(name))
				{
					if (take == true) this.inHand.remove(c);
					return c;
				}
			}
		}
		return null;
	}
	
	public Card addCardInDeck(Card card, Boolean autoriserEnDouble)
	{
		if (this.deck != null && autoriserEnDouble == false)
		{
			for (Card c:this.deck)
			{
				if (c.getName().equals(card.getName()))
				{
					return null;
				}
			}
		}
		this.deck.add(card);
		return card;
	}
	
	public Card pickCardFromDeck(int id)
	{
		if (this.deck != null)
		{
			Card c = this.deck.get(id);
			this.deck.remove(id);
			return c;
		}
		return null;
	}
	
	public void addCardInBoard(Card card)
	{
		this.inBoard.add(card);
	}
	
	public Card getCardFromBoardByName(String name, Boolean take)
	{
		if (this.inBoard != null)
		{
			for (Card c:this.inBoard)
			{
				if (c.getName().equals(name))
				{
					if (take == true) this.inBoard.remove(c);
					return c;
				}
			}
		}
		return null;
	}
	
	public Card getCardFromHandById(int id, Boolean take)
	{
		if (this.inHand != null)
		{
			Card c = this.inHand.get(id);
			if (take == true) this.inHand.remove(id);
			return c;
		}
		return null;
	}
	
	public Card getCardFromBoardById(int id, Boolean take)
	{
		if (this.inBoard != null)
		{
			Card c = this.inBoard.get(id);
			if (take == true) this.inBoard.remove(id);
			return c;
		}
		return null;
	}
	
	public Card getCardFromDeckById(int id, Boolean take)
	{
		if (this.deck != null)
		{
			Card c = this.deck.get(id);
			if (take == true) this.deck.remove(id);
			return c;
		}
		return null;
	}
	
	public void addRandomInDeck(int nbCartes, List<Integer> boules, List<Card> allCards)
	{
		int id;
		Card c,c2;
		for (int i=0;i<nbCartes;i++)
		{
			do {
				id = getRandomIdDeck(boules,allCards);
				c = allCards.get(id);
				switch (c.getRare())
				{
					case NORMAL:
						c.setCost(getOneBoule(boules)%4 +1); // 1 2 3 4
						c.setAttack(getOneBoule(boules)%4); // 0 < attack < 3
						c.setLife(getOneBoule(boules)%4 +1); // 1 < life < 4
					break;
					case GOOD:
						c.setCost(getOneBoule(boules)%4 +3); // 3 4 5 6
						c.setAttack(getOneBoule(boules)%4 +2); // 2 < attack < 5
						c.setLife(getOneBoule(boules)%5 +2); // 2 < life < 6
					break;
					case RARE:
						c.setCost(getOneBoule(boules)%4 +6); // 6 7 8 9
						c.setAttack(getOneBoule(boules)%4 +4); // 4 < attack < 7
						c.setLife(getOneBoule(boules)%4 +4); // 4 < life < 7
					break;
					case INSANE:
						c.setCost(getOneBoule(boules)%2 +9); // 9 10
						c.setAttack(getOneBoule(boules)%3 +8); // 8 < attack < 10
						c.setLife(getOneBoule(boules)%5 +8); // 8 < life < 12
					break;
				}
				c2 = addCardInDeck(c, false);
			} while (c2 == null);
		}
	}
	
	public void addRandomInHand(int nbCartes, List<Integer> boules, Rare rare)
	{
		for (int i=0;i<nbCartes;i++)
		{
			if (this.isDeckEmpty()==false) do {} while (addCardInHand(getCardFromDeckById(getRandomIdDeck(boules,deck), true), false, rare) == null);
		}
	}
	
	private int getOneBoule(List<Integer> boules)
	{
		Random randomGenerator = new Random();
		return boules.get(randomGenerator.nextInt(boules.size()));
	}
	
	private int getRandomIdDeck(List<Integer> boules, List<Card> deck)
	{
		int tD = deck.size()/50;
		int rD = deck.size()%50;
		int id = 0;
		for (int i=0;i<tD;i++)
		{
			id += getOneBoule(boules);
		}
		if (rD != 0) id+=getOneBoule(boules)%rD;
		return id;
	}
	
	public boolean gotProvocationInBoard(Card attacked)
	{
		for (Card c:this.inBoard)
		{
			if (c.getProvocation() == true && attacked != c)
			{
				return true;
			}
		}
		return false;
	}
	
	public Boolean isDeckEmpty()
	{
		if (deck.size()==0) return true;
		else return false;
	}
	
	public int getSizeDeck()
	{
		return this.deck.size();
	}
	
	public int getSizeHand()
	{
		return this.inHand.size();
	}
	
	public int getSizeBoard()
	{
		return this.inBoard.size();
	}
	
	public void mixDeck()
	{
		Collections.shuffle(deck);
	}
	
	public void clearHand()
	{
		this.inHand.clear();
	}
	
	public void clearDeck()
	{
		this.deck.clear();
	}
	
	public void clearBoard()
	{
		this.inBoard.clear();
	}
	
	public void resetLife()
	{
		this.pLife = 30;
	}
	
	public int getLife()
	{
		return pLife;
	}
	
	public void setLife(int life)
	{
		this.pLife = life;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Mana getMana()
	{
		return mana;
	}
	
	public void resetCanAttack()
	{
		for (Card c:this.inBoard)
		{
			c.setCanAttack(true);
		}
	}
}

