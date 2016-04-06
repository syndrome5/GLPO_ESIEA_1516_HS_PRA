package com.tard.hom;

public enum Rare 
{
	NORMAL("Normal", "Carte basique"),
	GOOD("Good", "Carte avantageuse"),
	RARE("Rare", "Carte rare"),
	INSANE("Insane", "La meilleure de sa catégorie");
	
	private final String label;
	private final String description;

	Rare(String label, String description) {
		this.label = label;
		this.description = description;
	}

	public String getLabel() 
	{
		return label;
	}
	
	public String getDescription()
	{
		return description;
	}
}
