package com.tard.hom;

import static com.tard.hom.CsvFileHelper.readCsvFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvEuro implements Euro 
{
	private final static char SEPARATOR = ';';
	private final static String RESOURCES_PATH = "src/main/resources/";
	private final static String EUROMILLIONS = "euromillions_3.csv";

	public List<Integer> findBoules() 
	{
		final List<String[]> data = readCsvFile(RESOURCES_PATH + EUROMILLIONS, SEPARATOR);
		final List<Integer> boules = getDataBoules(data);
		Collections.shuffle(boules);
		return boules;
	}

	private List<Integer> getDataBoules(List<String[]> data) 
	{
		final List<Integer> boules = new ArrayList<Integer>();

		for (String[] oneData : data) 
		{
			if (oneData[4].equals("boule_1")) {}
			else
			{
				for (int i=4;i<=8;i++)
				{
					boules.add(Integer.parseInt(oneData[i]));
				}
			}
		}

		return boules;
	}

}
