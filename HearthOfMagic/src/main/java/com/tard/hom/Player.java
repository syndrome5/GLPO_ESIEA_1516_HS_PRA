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
	
	public Player(String name)
	{
		this.name = name;
		this.deck = new ArrayList<Card>();
		this.inHand = new ArrayList<Card>();
		this.inBoard = new ArrayList<Card>();
		this.mana = new Mana();
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
	
	public Card addCardInHand(Card card, Boolean autoriserEnDouble)
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
	
	public Card pickCardFromTopDeck()
	{
		if (this.deck != null)
		{
			Card c = this.deck.get(0);
			this.deck.remove(0);
			return c;
		}
		return null;
	}
	
	public Card addCardInBoard(Card card, Boolean autoriserEnDouble)
	{
		if (this.inBoard != null && autoriserEnDouble == true)
		{
			for (Card c:this.inBoard)
			{
				if (c.getName().equals(card.getName()))
				{
					return null;
				}
			}
		}
		this.inBoard.add(card);
		return card;
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
	
	public void addRandomInDeck(List<Card> allCards, int nb, Boolean autoriserEnDouble)
	{
		Random randomGenerator = new Random();
		Integer index, secure;
		index = randomGenerator.nextInt(allCards.size());
		for(int i=0;i<nb;i++)
		{
			secure = 0;
			do 
			{
				secure++;
				if (secure==50) break;
			} while (addCardInDeck(allCards.get(index),autoriserEnDouble)==null);
		}
	}
	
	public void addRandomInHand(int nb, Boolean autoriserEnDouble)
	{
		Random randomGenerator = new Random();
		Integer index, secure;
		index = randomGenerator.nextInt(this.deck.size());
		for(int i=0;i<nb;i++)
		{
			secure = 0;
			do 
			{
				secure++;
				if (secure==50) break;
			} while (addCardInHand(this.deck.get(index),autoriserEnDouble)==null);
		}
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
	
	public String getName()
	{
		return name;
	}
	
	public Mana getMana()
	{
		return mana;
	}
}

