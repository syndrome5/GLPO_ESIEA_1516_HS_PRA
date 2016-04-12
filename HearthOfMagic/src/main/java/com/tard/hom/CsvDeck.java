package com.tard.hom;

import static com.tard.hom.CsvFileHelper.readCsvFile;

import java.util.ArrayList;
import java.util.List;

public class CsvDeck implements Deck 
{
	private final static char SEPARATOR = ';';
	private final static String RESOURCES_PATH = "src/main/resources/";
	private final static String EUROMILLIONS = "cartes.csv";

	public List<Card> getDeck() 
	{
		final List<String[]> data = readCsvFile(RESOURCES_PATH + EUROMILLIONS, SEPARATOR);
		final List<Card> cards = getData(data);
		return cards;
	}

	private List<Card> getData(List<String[]> data) 
	{
		final List<Card> cards = new ArrayList<Card>();
		Rare rare = null;
		Element ele = null;
		Boolean provoc = false;
		
		for (String[] oneData : data) 
		{
			if (oneData[0].equals("nom")) {}
			else
			{
				if (oneData[1].equals("normal")) rare = Rare.NORMAL;
				else if (oneData[1].equals("good")) rare = Rare.GOOD;
				else if (oneData[1].equals("rare")) rare = Rare.RARE;
				else if (oneData[1].equals("insane")) rare = Rare.INSANE;
				if (oneData[5].equals("oui")) provoc = true;
				else if (oneData[5].equals("non")) provoc = false;
				if (oneData[6].equals("feu")) ele = Element.FIRE;
				else if (oneData[6].equals("eau")) ele = Element.WATER;
				else if (oneData[6].equals("terre")) ele = Element.EARTH;
				else if (oneData[6].equals("air")) ele = Element.AIR;
				cards.add(new Card(oneData[0], rare, Integer.parseInt(oneData[2]), Integer.parseInt(oneData[3]), Integer.parseInt(oneData[4]), provoc,ele));
			}
		}

		return cards;
	}

}
