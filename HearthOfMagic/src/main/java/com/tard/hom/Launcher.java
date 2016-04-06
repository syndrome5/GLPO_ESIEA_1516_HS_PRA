package com.tard.hom;

import java.util.List;
import java.util.Random;

public class Launcher 
{
	public static void main(String[] args) 
	{
		final CsvEuro Euro = new CsvEuro();
		final List<Integer> boules = Euro.findBoules();
		
		final Player P1 = new Player("Joueur 1");
		final Player P2 = new Player("Joueur 2");
		
		final CsvDeck Deck = new CsvDeck();
		final List<Card> allCards = Deck.getDeck();
		
		/*
		 * 
		 * ICI SERA LE JEU
		 * RESTE A FAIRE LA GUI
		 */
	}
	
	public static void addRandomInDeck(Player P, List<Card> allCards)
	{
		Integer index;
		do 
		{
			Random randomGenerator = new Random();
			index = randomGenerator.nextInt(allCards.size());
		} while (P.addCardInDeck(allCards.get(index))==null);
	}
	
	public static void addRandomInHand(Player P, List<Card> allCards)
	{
		Integer index;
		do 
		{
			Random randomGenerator = new Random();
			index = randomGenerator.nextInt(allCards.size());
		} while (P.addCardInHand(allCards.get(index))==null);
	}
}
