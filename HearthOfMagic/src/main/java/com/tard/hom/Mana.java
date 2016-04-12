package com.tard.hom;

public class Mana 
{
	private int mana;
	private int manaMax;
	
	public Mana()
	{
		this.mana = 1;
		this.manaMax = 1;
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public int getManaMax()
	{
		return manaMax;
	}
	
	public void reset()
	{
		this.mana = 1;
		this.manaMax = 1;
	}
	
	public void resetAddTour()
	{
		this.manaMax++;
		this.mana = this.manaMax;
	}
}
