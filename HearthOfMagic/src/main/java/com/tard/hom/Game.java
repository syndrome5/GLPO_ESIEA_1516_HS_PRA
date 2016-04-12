package com.tard.hom;

import java.util.List;

public class Game 
{
	private Player P1;
	private Player P2;
	private List<Integer> boules;
	private List<Card> allCards;
	private int tour;
	private int tourBackup;
	private int nbCartes;
	private Boolean sameDeck;
	private Boolean accepterEnDouble;
	
	public Game(String n1, String n2, int tour, Boolean sameDeck, int nbCartes, Boolean accepterEnDouble)
	{
		this.P1 = new Player(n1);
		this.P2 = new Player(n2);
		final CsvEuro Euro = new CsvEuro();
		this.boules = Euro.findBoules();
		final CsvDeck Deck = new CsvDeck();
		this.allCards = Deck.getDeck();
		this.tour = tour;
		this.tourBackup = tour;
		this.sameDeck = sameDeck;
		this.nbCartes = nbCartes;
		this.accepterEnDouble = accepterEnDouble;
	}
	
	public void start()
	{
		init();
		
	}
	
	private void init()
	{
		if (boules.size() == 0 || allCards.size() == 0)
		{
			return;
		}
		reset();
		if (P1.isDeckEmpty() == true)
		{
			P1.addRandomInDeck(allCards, nbCartes, accepterEnDouble);
		}
		if (P2.isDeckEmpty() == true)
		{
			P2.addRandomInDeck(allCards, nbCartes, accepterEnDouble);
		}
	}
	
	private void reset()
	{
		P1.clearBoard();
		P1.clearHand();
		P2.clearBoard();
		P2.clearHand();
		if (sameDeck==false)
		{
			P1.clearDeck();
			P2.clearDeck();
		}
		P1.getMana().reset();
		P2.getMana().reset();
		this.tour = this.tourBackup;
	}
	
	private void switchTour()
	{
		if (this.tour == 1) this.tour=2;
		else this.tour=1;
	}
}
