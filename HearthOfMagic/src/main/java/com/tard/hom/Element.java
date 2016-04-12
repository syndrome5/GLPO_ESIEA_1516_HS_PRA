package com.tard.hom;

public enum Element 
{
	FIRE("Feu", "Brûlés"),
	WATER("Eau", "Noyés"),
	EARTH("Terre", "Écrasés"),
	AIR("Air", "Ouragan");
	
	private final String label;
	private final String slogan;

	Element(String label, String slogan) {
		this.label = label;
		this.slogan = slogan;
	}

	public String getLabel() 
	{
		return label;
	}
	
	public String getSlogan()
	{
		return slogan;
	}
}
