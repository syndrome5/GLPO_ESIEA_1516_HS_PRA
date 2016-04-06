/*
Player
 - String name
 - List<Card> deck
 - List<Card> inHand
 - Integer mana
 - Integer manaMax

Card
 - String name
 - Rare rare
 - Integer life
 - Integer attack
 - Integer cost
 - Boolean provocation

Enum Rare {NORMAL, GOOD, RARE, INSANE}
 - String label
 - String description

main
 - List<Player> players
 - List<Card> allCards
 */

package com.tard.hom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player 
{
	private String name;
	private List<Card> deck;
	private List<Card> inHand;
	private int mana;
	private int manaMax;
	
	public Player(String name)
	{
		this.name = name;
		this.deck = new ArrayList<Card>();
		this.inHand = new ArrayList<Card>();
		this.mana = 0;
		this.manaMax = 0;
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
	
	public Card addCardInHand(Card card)
	{
		if (this.inHand != null)
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
	
	public Card pickCardFromHandByName(String name)
	{
		if (this.inHand != null)
		{
			for (Card c:this.inHand)
			{
				if (c.getName().equals(name))
				{
					this.inHand.remove(c);
					return c;
				}
			}
		}
		return null;
	}
	
	public Card addCardInDeck(Card card)
	{
		if (this.deck != null)
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
	
	public String getName()
	{
		return name;
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public int getManaMax()
	{
		return manaMax;
	}
	
	public void setMana(int mana)
	{
		this.mana = mana;
	}
	
	public void setManaMax(int manaMax)
	{
		this.manaMax = manaMax;
	}
}

